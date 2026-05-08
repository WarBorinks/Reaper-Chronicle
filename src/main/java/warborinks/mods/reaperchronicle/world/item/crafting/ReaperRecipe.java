package warborinks.mods.reaperchronicle.world.item.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;

public class ReaperRecipe implements Recipe<ReaperRecipeInput> {
    private final String group;
    private final List<ReaperRecipeIngredient> crystals;
    private final List<ReaperRecipeIngredient> reapers;
    private final List<ReaperRecipeIngredient> others;
    private final ItemStack result;

    public ReaperRecipe(String group, List<ReaperRecipeIngredient> crystals, List<ReaperRecipeIngredient> reapers,
        List<ReaperRecipeIngredient> others, ItemStack result) {
        this.group = group;
        this.crystals = crystals;
        this.reapers = reapers;
        this.others = others;
        this.result = result;
    }

    @Override
    public boolean matches(@Nonnull ReaperRecipeInput input, @Nonnull Level level) {
        return checkType(this.crystals, input.getCrystals(), true, false) &&
               checkType(this.reapers, input.getReapers(), false, true) &&
               checkType(this.others, input.getOthers(), false, false);
    }

    private boolean checkType(List<ReaperRecipeIngredient> required, List<ItemStack> available,
        boolean mustBeCrystal, boolean mustBeReaper) {
        List<ItemStack> mutableAvailable = new ArrayList<>();
        for (ItemStack s : available) {
            mutableAvailable.add(s.copy());
        }
        for (ReaperRecipeIngredient req : required) {
            int needed = req.count();
            for (Iterator<ItemStack> it = mutableAvailable.iterator(); it.hasNext() && needed > 0;) {
                ItemStack stack = it.next();
                if (mustBeCrystal && !(stack.getItem() instanceof CrystalItem)) {
                    continue;
                } else if (mustBeReaper && !(stack.getItem() instanceof ReaperItem)) {
                    continue;
                } else if (!mustBeCrystal && !mustBeReaper) {
                    if (stack.getItem() instanceof CrystalItem || stack.getItem() instanceof ReaperItem) {
                        continue;
                    }
                }
                
                if (req.ingredient().test(stack)) {
                    int take = Math.min(stack.getCount(), needed);
                    needed -= take;
                    stack.shrink(take);
                    if (stack.isEmpty()) it.remove();
                }
            }
            if (needed > 0) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(@Nonnull ReaperRecipeInput input, @Nonnull HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem(@Nonnull HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        this.crystals.forEach(i -> list.add(i.ingredient()));
        this.reapers.forEach(i -> list.add(i.ingredient()));
        this.others.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    @Override
    public RecipeType<?> getType() {
        return RCRecipeTypes.REAPER_RECIPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RCRecipeSerializers.REAPER_RECIPE.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    public List<ReaperRecipeIngredient> getCrystals() {
        return this.crystals;
    }
    public List<ReaperRecipeIngredient> getReapers() {
        return this.reapers;
    }
    public List<ReaperRecipeIngredient> getOthers() {
        return this.others;
    }
    public ItemStack getResult() {
        return this.result;
    }
}
