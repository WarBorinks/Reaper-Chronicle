package warborinks.mods.reaperchronicle.world.level.block.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.item.crafting.RCRecipeTypes;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipe;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipeIngredient;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipeInput;

public class SoulAltar extends BlockEntity {
    private final List<ItemStack> crystals = new ArrayList<>();
    private final List<ItemStack> reapers = new ArrayList<>();
    private final List<ItemStack> others = new ArrayList<>();

    private ItemStack result = ItemStack.EMPTY;

    private RecipeHolder<ReaperRecipe> cache;
    private boolean recipeValid;
    private boolean contentsChanged = false;

    public SoulAltar(BlockPos pos, BlockState state) {
        super(RCBlockEntityTypes.SOUL_ALTAR.get(), pos, state);
    }

    private void markContentsChanged() {
        this.contentsChanged = true;
        setChanged();
    }
    public boolean consumeChanged() {
        boolean changed = this.contentsChanged;
        this.contentsChanged = false;
        return changed;
    }

    @SuppressWarnings("null")
    private void updateRecipe() {
        if (level == null) {
            this.recipeValid = false;
            this.result = ItemStack.EMPTY;
        }

        RecipeManager recipeManager = level.getRecipeManager();
        ReaperRecipeInput input = new ReaperRecipeInput(
            Collections.unmodifiableList(this.crystals), 
            Collections.unmodifiableList(this.reapers),
            Collections.unmodifiableList(this.others)
        );

        recipeManager.getRecipeFor(
            RCRecipeTypes.REAPER_RECIPE.get(),
            input, level
        ).ifPresentOrElse(
            (recipe) -> {
                this.cache = recipe;
                this.result = this.cache.value().assemble(input, level.registryAccess());
                this.recipeValid = true;
            },
            () -> {
                this.result = ItemStack.EMPTY;
                this.recipeValid = false;
            }
        );
    }

    private void onContentsChanged() {
        this.updateRecipe();
        this.markContentsChanged();
    }

    public void insertItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        List<ItemStack> list = this.getList(stack.getItem());
        this.addTo(list, stack.copy());

        this.onContentsChanged();
    }

    private List<ItemStack> getList(Item item) {
        if (item instanceof CrystalItem) {
            return this.crystals;
        } else if (item instanceof ReaperItem) {
            return this.reapers;
        } else {
            return this.others;
        }
    }

    @SuppressWarnings("null")
    private void addTo(List<ItemStack> list, ItemStack stack) {
        for (ItemStack target : list) {
            if (ItemStack.isSameItemSameComponents(stack, target)) {
                target.grow(stack.getCount());
                return;
            }
        }
        list.add(stack);
    }

    public ItemStack extractItem(List<ItemStack> list, int index, int amount, boolean simulate) {
        if (index < 0 || index >= list.size()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = list.get(index);
        int take = Math.min(stack.getCount(), Math.min(stack.getMaxStackSize(), amount));
        if (take == 0) {
            return ItemStack.EMPTY;
        }

        if (simulate) {
            ItemStack result = stack.copy();
            result.setCount(take);
            return result;
        } else {
            ItemStack result = stack.split(take);
            if (stack.isEmpty()) {
                list.remove(index);
            }
            
            this.onContentsChanged();
            return result;
        }
    }

    @SuppressWarnings("null")
    public ItemStack craftOne() {
        if (!this.recipeValid || level == null) {
            return ItemStack.EMPTY;
        }

        ReaperRecipeInput input = new ReaperRecipeInput(this.crystals, this.reapers, this.others);
        if (!this.cache.value().matches(input, level)) {
            this.updateRecipe();
            if (!this.recipeValid) {
                return ItemStack.EMPTY;
            }
        }

        ReaperRecipe recipe = this.cache.value();
        if (!this.consumeIngredients(this.crystals, recipe.getCrystals(), true, false)
            || !this.consumeIngredients(this.reapers, recipe.getReapers(), false, true)
            || !this.consumeIngredients(this.others, recipe.getOthers(), false, false)) {
            return ItemStack.EMPTY;
        }

        this.onContentsChanged();

        return this.result.copy();
    }

    private boolean consumeIngredients(List<ItemStack> available, List<ReaperRecipeIngredient> required, 
        boolean crystals, boolean reapers) {
        List<ItemStack> mutable = available.stream()
            .map(ItemStack::copy)
            .toList();
        
        Map<Ingredient, Integer> neededMap = required.stream()
            .collect(Collectors.toMap(
                ReaperRecipeIngredient::ingredient,
                ReaperRecipeIngredient::count
            ));
        
        for (Iterator<ItemStack> it = mutable.iterator(); it.hasNext();) {
            ItemStack stack = it.next();
            boolean trueType = this.checkType(stack.getItem(), crystals, reapers);
            if (!trueType) {
                continue;
            }

            for (Map.Entry<Ingredient, Integer> entry : neededMap.entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }

                Ingredient ingredient = entry.getKey();
                if (ingredient.test(stack)) {
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

        if (neededMap.values().stream().anyMatch(v -> v > 0)) {
            return false;
        }

        available.clear();
        available.addAll(mutable);
        available.removeIf(ItemStack::isEmpty);

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
    public void saveAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("crystals", this.writeStackList(this.crystals, registries));
        tag.put("reapers", this.writeStackList(this.reapers, registries));
        tag.put("others", this.writeStackList(this.others, registries));
    }

    private @Nonnull ListTag writeStackList(List<ItemStack> stacks, @Nonnull HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                CompoundTag itemTag = (CompoundTag) stack.save(registries);
                list.add(itemTag);
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

        if (level != null) {
            this.updateRecipe();
        }
    }

    @SuppressWarnings("null")
    private void readStackList(ListTag list, List<ItemStack> target, @Nonnull HolderLookup.Provider regsitries) {
        target.clear();
        for (Tag tag : list) {
            ItemStack.parse(regsitries, tag).ifPresent(target::add);
        }
    }
}
