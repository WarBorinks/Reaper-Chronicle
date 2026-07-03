package warborinks.mods.reaperchronicle.util;

import java.util.Map;

import net.minecraft.world.item.ItemStack;

public record ItemStackSave(ItemStack stack) {
    public static ItemStack getStackFromEntry(Map.Entry<ItemStackSave, Integer> entry) {
        ItemStack result = entry.getKey().stack().copy();
        result.setCount(entry.getValue());
        return result;
    }

    @Override
    @SuppressWarnings("null")
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ItemStackSave save) {
            return ItemStack.isSameItemSameComponents(save.stack, this.stack);
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(this.stack);
    }
}
