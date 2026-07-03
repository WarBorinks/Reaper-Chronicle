package warborinks.mods.reaperchronicle.world.item.crafting;

import java.util.List;
import java.util.function.Function;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.util.ItemStackSave;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;

public class ReaperRecipeInput implements RecipeInput {
    private final List<ItemStack> crystals;
    private final List<ItemStack> reapers;
    private final List<ItemStack> others;

    public ReaperRecipeInput(List<ItemStack> crystals, List<ItemStack> reapers, List<ItemStack> others) {
        this.crystals = dealInputList(crystals, stack -> {
            return stack.getItem() instanceof CrystalItem;
        });
        this.reapers = dealInputList(reapers, stack -> {
            return stack.getItem() instanceof ReaperItem;
        });
        this.others = dealInputList(others, stack -> {
            return !(stack.getItem() instanceof CrystalItem || stack.getItem() instanceof ReaperItem);
        });
    }

    @SuppressWarnings("null")
    private static List<ItemStack> dealInputList(List<ItemStack> list,
        Function<ItemStack, Boolean> checker) {
        return list.stream()
            .filter(stack -> stack != null && !stack.isEmpty() && checker.apply(stack))
            .collect(RCUtil.getMergedListCollector(
                ItemStackSave::new, ItemStack::getCount,
                Integer::sum, ItemStackSave::getStackFromEntry
            ));
    }

    @Override
    public ItemStack getItem(int index) {
        if (index < this.crystals.size()) {
            return this.crystals.get(index);
        } else if (index < this.crystals.size() + this.reapers.size()) {
            return this.reapers.get(index - this.crystals.size());
        } else if (index < this.crystals.size() + this.reapers.size() + this.others.size()) {
            return this.others.get(index - this.crystals.size() - this.reapers.size());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int size() {
        return this.crystals.size() + this.reapers.size() + this.others.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ReaperRecipeInput input) {
            return input.crystals.equals(this.crystals) &&
                input.others.equals(this.others) &&
                input.reapers.equals(this.reapers);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int i = 0;

        for (ItemStack stack : this.crystals) {
            i = i * 31 + ItemStack.hashItemAndComponents(stack);
        } for (ItemStack stack : this.reapers) {
            i = i * 31 + ItemStack.hashItemAndComponents(stack);
        } for (ItemStack stack : this.others) {
            i = i * 31 + ItemStack.hashItemAndComponents(stack);
        }

        return i;
    }

    public List<ItemStack> getCrystals() {
        return this.crystals;
    }
    public List<ItemStack> getReapers() {
        return this.reapers;
    }
    public List<ItemStack> getOthers() {
        return this.others;
    }
}
