package warborinks.mods.reaperchronicle.util;

import java.util.ArrayList;
import java.util.Comparator;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class ItemStackList extends ArrayList<ItemStack> {
    @SuppressWarnings("null")
    public void sort() {
        sort(Comparator.comparing(
                (ItemStack stack) -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString()
            ).thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed())
        );
    }
}
