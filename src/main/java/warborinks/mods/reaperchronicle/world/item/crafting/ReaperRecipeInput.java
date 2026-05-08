package warborinks.mods.reaperchronicle.world.item.crafting;

import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class ReaperRecipeInput implements RecipeInput {
    private final List<ItemStack> crystals;
    private final List<ItemStack> reapers;
    private final List<ItemStack> others;

    public ReaperRecipeInput(List<ItemStack> crystals, List<ItemStack> reapers, List<ItemStack> others) {
        this.crystals = crystals;
        this.reapers = reapers;
        this.others = others;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
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
