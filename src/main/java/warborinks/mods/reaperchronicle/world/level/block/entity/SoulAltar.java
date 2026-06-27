package warborinks.mods.reaperchronicle.world.level.block.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import warborinks.mods.reaperchronicle.RCUtil.ItemStackList;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.inventory.SoulAltarMenu;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.item.crafting.RCRecipeTypes;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipe;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipeIngredient;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipeInput;

public class SoulAltar extends BlockEntity implements MenuProvider {
    private static final Component TITLE = Component.translatable("container." + RCRegistryNames.MenuTypes.SOUL_ALTAR);
    
    public static final int ITEMS_PER_PAGE = 4;
    public static final int DISPLAY_SLOTS = 4;

    private final ItemStackList crystals = new ItemStackList();
    private final ItemStackList reapers = new ItemStackList();
    private final ItemStackList others = new ItemStackList();
    
    private int crystalPage = 0;
    private int reaperPage  = 0;
    private int otherPage   = 0;
    
    private boolean changed = false;

    private ItemStack result = ItemStack.EMPTY;
    private boolean recipeValid;

    @Nullable private RecipeHolder<ReaperRecipe> cache;

    public SoulAltar(BlockPos pos, BlockState state) {
        super(RCBlockEntityTypes.SOUL_ALTAR.get(), pos, state);
    }

    public static Component getTitle() {
        return TITLE.copy();
    }

    public ItemStackList getCrystals() {
        return this.crystals;
    }
    public ItemStackList getReapers() {
        return this.reapers;
    }
    public ItemStackList getOthers() {
        return this.others;
    }
    public ItemStack getResult() {
        return this.recipeValid ? this.result : ItemStack.EMPTY;
    }

    public int getCrystalPage() {
        return this.crystalPage;
    }
    public int getReaperPage() {
        return this.reaperPage;
    }
    public int getOtherPage() {
        return this.otherPage;
    }

    public boolean hasChanged() {
        boolean result = this.changed;
        this.changed = false;
        return result;
    }

    private void onContentsChanged() {
        this.updateRecipe();
        this.markChanged();
    }

    @SuppressWarnings("null")
    private void markChanged() {
        this.changed = true;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public ItemStackList getListForType(ListType type) {
        return switch (type) {
            case CRYSTAL -> this.crystals;
            case REAPER  -> this.reapers;
            case OTHER   -> this.others;
        };
    }

    public int getPageForType(ListType type) {
        return switch (type) {
            case CRYSTAL -> this.crystalPage;
            case REAPER  -> this.reaperPage;
            case OTHER   -> this.otherPage;
        };
    }

    public List<ItemStack> getPageItems(ListType type) {
        ItemStackList list = this.getListForType(type);
        int page = this.getPageForType(type);
        int from = page * ITEMS_PER_PAGE;
        int to = Math.min(from + ITEMS_PER_PAGE, list.size());

        if (from >= list.size()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list.subList(from, to));
    }

    public void shiftPage(ListType type, boolean right) {
        int maxPage = this.getMaxPageForType(type);
        int current = this.getPageForType(type);

        int newPage;
        if (right) {
            newPage = (current >= maxPage) ? 0 : current + 1;
        } else {
            newPage = (current <= 0) ? maxPage : current - 1;
        }

        this.setPage(type, newPage);
    }

    public void sort() {
        this.sortListForType(ListType.CRYSTAL);
        this.sortListForType(ListType.REAPER);
        this.sortListForType(ListType.OTHER);
    }

    private void sortListForType(ListType type) {
        ItemStackList list = this.getListForType(type);
        list.sort();
        this.setPage(type, 0);
    }

    public int getMaxPageForType(ListType type) {
        return this.getPageForIndex(this.getListForType(type).size() - 1);
    }

    private void setPage(ListType type, int page) {
        switch (type) {
            case CRYSTAL -> this.crystalPage = page;
            case REAPER  -> this.reaperPage  = page;
            case OTHER   -> this.otherPage   = page;
        }
        this.markChanged();
    }

    private int getPageForIndex(int index) {
        if (index < 0) {
            return 0;
        } else {
            return Math.max(0, index / ITEMS_PER_PAGE);
        }
    }
    private int getIndexForPage(int page, int indexInPage) {
        return page * ITEMS_PER_PAGE + indexInPage;
    }

    @SuppressWarnings("null")
    private void updateRecipe() {
        if (level == null) {
            this.recipeValid = false;
            this.result = ItemStack.EMPTY;
            return;
        }

        RecipeManager manager = level.getRecipeManager();
        ReaperRecipeInput input = new ReaperRecipeInput(
                Collections.unmodifiableList(crystals),
                Collections.unmodifiableList(reapers),
                Collections.unmodifiableList(others));
        manager.getRecipeFor(RCRecipeTypes.REAPER_RECIPE.get(), input, level)
                .ifPresentOrElse(holder -> {
                    this.cache = holder;
                    this.result = holder.value().assemble(input, level.registryAccess());
                    this.recipeValid = true;
                }, () -> {
                    this.cache = null;
                    this.result = ItemStack.EMPTY;
                    this.recipeValid = false;
                });
    }

    @SuppressWarnings("null")
    public void insertItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        ListType type = this.getTypeForItem(stack.getItem());
        ItemStackList list = this.getListForType(type);
        for (int i = 0; i < list.size(); i++) {
            ItemStack target = list.get(i);
            if (ItemStack.isSameItemSameComponents(stack, target)) {
                target.grow(stack.getCount());
                stack.setCount(0);

                this.setPage(type, this.getPageForIndex(i));

                this.onContentsChanged();
                return;
            }
        }

        list.add(stack.copy());
        stack.setCount(0);

        this.setPage(type, this.getMaxPageForType(type));

        this.onContentsChanged();
    }

    private ListType getTypeForItem(Item item) {
        if (item instanceof CrystalItem) {
            return ListType.CRYSTAL;
        } else if (item instanceof ReaperItem) {
            return ListType.REAPER;
        } else {
            return ListType.OTHER;
        }
    }

    public ItemStack extractItem(ListType type, int index, int amount, boolean simulate) {
        ItemStackList list = this.getListForType(type);
        if (index < 0 || index >= list.size()) {
            return ItemStack.EMPTY;
        }

        ItemStack target = list.get(index);
        int take = Math.min(amount, target.getCount());
        if (take <= 0) {
            return ItemStack.EMPTY;
        }

        if (simulate) {
            ItemStack result = target.copy();
            result.setCount(take);
            return result;
        }

        ItemStack result = target.split(take);
        if (target.isEmpty()) {
            list.remove(index);
            this.clampPage(type);
        }

        this.onContentsChanged();

        return result;
    }

    private void clampPage(ListType type) {
        int max = this.getMaxPageForType(type);
        switch (type) {
            case CRYSTAL -> this.crystalPage = Math.min(this.crystalPage, max);
            case REAPER  -> this.reaperPage = Math.min(this.reaperPage, max);
            case OTHER   -> this.otherPage = Math.min(this.otherPage, max);
        }
    }

    public ItemStack extractFromDisplaySlot(ListType type, int slotIndex, int amount, boolean simulate) {
        int index = this.getIndexForPage(this.getPageForType(type), slotIndex);
        return extractItem(type, index, amount, simulate);
    }

    @SuppressWarnings("null")
    public ItemStack craftOne() {
        if (!this.recipeValid || this.cache == null || level == null) {
            return null;
        }

        ReaperRecipe recipe = this.cache.value();
        if (!recipe.matches(new ReaperRecipeInput(this.crystals, this.reapers, this.others), level)) {
            this.updateRecipe();

            if (!this.recipeValid) {
                return null;
            }

            recipe = this.cache.value();
        }

        if (!this.consumeIngredients(this.crystals, recipe.getCrystals(), true, false, true) ||
            !this.consumeIngredients(this.reapers, recipe.getReapers(), false, true, true) ||
            !this.consumeIngredients(this.others, recipe.getOthers(), false, false, true)) {
            return null;
        }

        this.consumeIngredients(this.crystals, recipe.getCrystals(), true, false, false);
        this.consumeIngredients(this.reapers, recipe.getReapers(), false, true, false);
        this.consumeIngredients(this.others, recipe.getOthers(), false, false, false);

        ItemStack result = this.result.copy();

        this.onContentsChanged();

        return result;
    }

    @SuppressWarnings("null")
    public void craftMax(Player player) {
        if (!this.recipeValid || this.cache == null || level == null) {
            return;
        }

        ReaperRecipe recipe = this.cache.value();
        while (true) {
            if (!recipe.matches(new ReaperRecipeInput(crystals, reapers, others), level)) {
                this.updateRecipe();

                if (!this.recipeValid) {
                    break;
                }

                recipe = this.cache.value();
            }

            if (!this.consumeIngredients(this.crystals, recipe.getCrystals(), true, false, true) ||
                !this.consumeIngredients(this.reapers, recipe.getReapers(), false, true, true) ||
                !this.consumeIngredients(this.others, recipe.getOthers(), false, false, true)) {
                break;
            }

            ItemStack product = result.copy();
            if (!player.getInventory().add(product)) {
                break;
            }
            
            this.consumeIngredients(this.crystals, recipe.getCrystals(), true, false, false);
            this.consumeIngredients(this.reapers, recipe.getReapers(), false, true, false);
            this.consumeIngredients(this.others, recipe.getOthers(), false, false, false);
        }

        this.onContentsChanged();
    }

    @SuppressWarnings("null")
    private boolean consumeIngredients(ItemStackList available, List<ReaperRecipeIngredient> required,
        boolean crystal, boolean reaper, boolean simulate) {
        List<ItemStack> mutable = available.stream()
            .map(ItemStack::copy)
            .toList();

        Map<Ingredient, Integer> needed = required.stream()
            .collect(Collectors.toMap(
                ReaperRecipeIngredient::ingredient,
                ReaperRecipeIngredient::count,
                Integer::sum
            ));

        for (Iterator<ItemStack> it = mutable.iterator(); it.hasNext(); ) {
            ItemStack stack = it.next();
            if (!this.checkType(stack.getItem(), crystal, reaper)) {
                continue;
            }

            for (Map.Entry<Ingredient, Integer> entry : needed.entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }

                if (entry.getKey().test(stack)) {
                    int take = Math.min(stack.getCount(), entry.getValue());
                    entry.setValue(entry.getValue() - take);
                    stack.shrink(take);

                    if (stack.isEmpty()) {
                        it.remove();
                    }

                    break;
                }
            }
        }

        if (needed.values().stream().anyMatch(v -> v > 0)) {
            return false;
        }

        if (!simulate) {
            available.clear();
            available.addAll(mutable);
            available.removeIf(ItemStack::isEmpty);
        }

        return true;
    }

    private boolean checkType(Item item, boolean crystal, boolean reaper) {
        if (crystal) {
            return item instanceof CrystalItem;
        } else if (reaper) {
            return item instanceof ReaperItem;
        } else {
            return !(item instanceof CrystalItem) && !(item instanceof ReaperItem);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    @SuppressWarnings("null")
    public CompoundTag getUpdateTag(@Nonnull HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        loadAdditional(tag, registries);
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("crystals", this.writeStackList(this.crystals, registries));
        tag.put("reapers", this.writeStackList(this.reapers, registries));
        tag.put("others", this.writeStackList(this.others, registries));
        tag.putInt("crystalPage", this.crystalPage);
        tag.putInt("reaperPage", this.reaperPage);
        tag.putInt("otherPage", this.otherPage);
    }

    private @Nonnull ListTag writeStackList(ItemStackList stacks, @Nonnull HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                list.add(stack.save(registries));
            }
        }

        return list;
    }

    @Override
    public void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.readStackList(tag.getList("crystals", Tag.TAG_COMPOUND), this.crystals, registries);
        this.readStackList(tag.getList("reapers", Tag.TAG_COMPOUND), this.reapers, registries);
        this.readStackList(tag.getList("others", Tag.TAG_COMPOUND), this.others, registries);
        this.crystalPage = tag.getInt("crystalPage");
        this.reaperPage = tag.getInt("reaperPage");
        this.otherPage = tag.getInt("otherPage");

        if (level != null) {
            this.updateRecipe();
        }
    }

    @SuppressWarnings("null")
    private void readStackList(ListTag list, ItemStackList target, @Nonnull HolderLookup.Provider regsitries) {
        target.clear();
        for (Tag tag : list) {
            ItemStack.parse(regsitries, tag).ifPresent(target::add);
        }
    }

    @Override
    public Component getDisplayName() {
        return getTitle();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @Nonnull Inventory inventory, @Nonnull Player player) {
        return new SoulAltarMenu(id, inventory, this);
    }

    public enum ListType implements StringRepresentable {
        CRYSTAL("crystal"),
        REAPER("reaper"),
        OTHER("other");

        public static final Codec<ListType> CODEC = StringRepresentable.fromEnum(ListType::values);

        private final String name;

        ListType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
