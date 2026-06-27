package warborinks.mods.reaperchronicle.world.inventory;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.level.block.entity.SoulAltar;

public class SoulAltarMenu extends AbstractContainerMenu {
    private static final int PLAYER_INVENTORY_START = 3 + SoulAltar.DISPLAY_SLOTS * 3 + 1;

    private final SoulAltar soulAltar;
    private final Player player;

    @SuppressWarnings("null")
    public SoulAltarMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(
            containerId, inventory,
            (SoulAltar) inventory.player.level().getBlockEntity(extraData.readBlockPos())
        );
    }

    public SoulAltarMenu(int containerId, Inventory inventory, @Nonnull SoulAltar soulAltar) {
        super(RCMenuTypes.SOUL_ALTAR.get(), containerId);

        this.soulAltar = soulAltar;
        this.player = inventory.player;

        for (int i = 0; i < 3; i++) {
            addSlot(new InputSlot(soulAltar, SoulAltar.ListType.values()[i], 8, 17 + i * 24));
        }

        for (int i = 0; i < 3; i++) {
            SoulAltar.ListType type = SoulAltar.ListType.values()[i];
            for (int j = 0; j < SoulAltar.DISPLAY_SLOTS; j++) {
                addSlot(new DisplaySlot(soulAltar, type, j, 44 + j * 18, 17 + i * 24));
            }
        }

        addSlot(new OutputSlot(soulAltar, 141, 41));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 96 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 8 + i * 18, 154));
        }
    }

    public SoulAltar getSoulAltar() {
        return this.soulAltar;
    }
    public Player getPlayer() {
        return this.player;
    }

    @SuppressWarnings("null")
    public void handleExtractItem(SoulAltar.ListType type, int slotIndex, int amount) {
        ItemStack stack = this.soulAltar.extractFromDisplaySlot(type, slotIndex, amount, true);
        if (!stack.isEmpty()) {
            ItemStack carried = getCarried();
            if (carried.isEmpty()) {
                setCarried(this.soulAltar.extractFromDisplaySlot(type, slotIndex, amount, false));
            } else if (ItemStack.isSameItemSameComponents(carried, stack)) {
                amount = Math.min(amount, carried.getMaxStackSize() - carried.getCount());
                if (amount > 0) {
                    carried.grow(this.soulAltar.extractFromDisplaySlot(type, slotIndex, amount, false)
                        .getCount());
                }
            }
        }

        broadcastChanges();
    }
    public void handleShiftPage(SoulAltar.ListType type, boolean right) {
        this.soulAltar.shiftPage(type, right);
    }
    public void handleSort() {
        this.soulAltar.sort();
    }

    @Override
    @SuppressWarnings("null")
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        if (player.level().isClientSide()) {
            return ItemStack.EMPTY;
        }

        Slot slot = getSlot(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        if (slot instanceof OutputSlot) {
            this.soulAltar.craftMax(player);
            return ItemStack.EMPTY;
        }

        if (index >= PLAYER_INVENTORY_START) {
            if (stack.getItem() instanceof CrystalItem) {
                if (!moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (stack.getItem() instanceof ReaperItem) {
                if (!moveItemStackTo(stack, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack, 2, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return ItemStack.EMPTY;
    }

    @Override
    @SuppressWarnings("null")
    public boolean stillValid(@Nonnull Player player) {
        return Container.stillValidBlockEntity(this.soulAltar, player);
    }

    public static final class InputSlot extends Slot {
        private final SoulAltar soulAltar;
        private final SoulAltar.ListType type;

        public InputSlot(@Nonnull SoulAltar soulAltar, SoulAltar.ListType type, int x, int y) {
            super(new SimpleContainer(1), 0, x, y);
            this.soulAltar = soulAltar;
            this.type = type;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return switch (this.type) {
                case CRYSTAL -> stack.getItem() instanceof CrystalItem;
                case REAPER  -> stack.getItem() instanceof ReaperItem;
                case OTHER   -> !(stack.getItem() instanceof CrystalItem) &&
                    !(stack.getItem() instanceof ReaperItem);
            };
        }

        @Override
        public void set(@Nonnull ItemStack stack) {
            if (!stack.isEmpty()) {
                this.soulAltar.insertItem(stack);
            }
            this.setChanged();
        }

        @Override
        public ItemStack getItem() {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean hasItem() {
            return false;
        }
    }

    public static final class DisplaySlot extends Slot {
        private final SoulAltar soulAltar;
        private final SoulAltar.ListType type;
        private final int index;

        public DisplaySlot(@Nonnull SoulAltar soulAltar, SoulAltar.ListType type, int index, int x, int y) {
            super(new SimpleContainer(1), 0, x, y);
            this.soulAltar = soulAltar;
            this.type = type;
            this.index = index;
        }

        public SoulAltar.ListType getType() {
            return this.type;
        }
        public int getIndex() {
            return this.index;
        }

        @Override
        public ItemStack getItem() {
            List<ItemStack> pageItems = this.soulAltar.getPageItems(this.type);
            return this.index < pageItems.size() ? pageItems.get(this.index).copy() : ItemStack.EMPTY;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(@Nonnull Player player) {
            return this.index < this.soulAltar.getPageItems(this.type).size();
        }

        @Override
        public void set(@Nonnull ItemStack stack) {
        }
    }

    public static class OutputSlot extends Slot {
        private final SoulAltar soulAltar;

        public OutputSlot(@Nonnull SoulAltar soulAltar, int x, int y) {
            super(new SimpleContainer(1), 0, x, y);
            this.soulAltar = soulAltar;
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack getItem() {
            return this.soulAltar.getResult().copy();
        }

        @Override
        public boolean hasItem() {
            return this.soulAltar.getResult() != null && !this.soulAltar.getResult().isEmpty();
        }

        @Override
        public void onTake(@Nonnull Player player, @Nonnull ItemStack stack) {
            if (!player.level().isClientSide()) {
                ItemStack result = this.soulAltar.craftOne();
                if (result != null) {
                    player.getInventory().placeItemBackInInventory(result);
                }
            }
        }

        @Override
        public void set(@Nonnull ItemStack stack) {
        }
    }
}
