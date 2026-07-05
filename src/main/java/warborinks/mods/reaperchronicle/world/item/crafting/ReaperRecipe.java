package warborinks.mods.reaperchronicle.world.item.crafting;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

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
    private static final Logger LOGGER = LogUtils.getLogger();

    private final String group;
    private final List<Ingredient> crystals;
    private final List<Ingredient> reapers;
    private final List<Ingredient> others;
    private final ItemStack result;

    public ReaperRecipe(String group, List<Ingredient> crystals, List<Ingredient> reapers,
        List<Ingredient> others, ItemStack result) {
        this.group = group;
        this.result = result;

        this.crystals = dealInputList(crystals, ing -> {
            for (ItemStack stack : ing.getItems()) {
                if (stack.getItem() instanceof CrystalItem) {
                    return true;
                }
            }
            return false;
        });
        this.reapers = dealInputList(reapers, ing -> {
            for (ItemStack stack : ing.getItems()) {
                if (stack.getItem() instanceof ReaperItem) {
                    return true;
                }
            }
            return false;
        });
        this.others = dealInputList(others, ing -> {
            for (ItemStack stack : ing.getItems()) {
                if (!(stack.getItem() instanceof CrystalItem || stack.getItem() instanceof ReaperItem)) {
                    return true;
                }
            }
            return false;
        });

        if (this.getIngredients().size() == 0) {
            LOGGER.warn("Recipe '{}' has no valid ingredients after filtering", this.result);
        }
    }

    private static List<Ingredient> dealInputList(List<Ingredient> list,
        Function<Ingredient, Boolean> checker) {
        return list
            .stream()
            .filter(ing -> ing != null && ing.getCustomIngredient() instanceof ReaperRecipeIngredient && checker.apply(ing))
            .toList();
    }


    @Override
    public boolean matches(@Nonnull ReaperRecipeInput input, @Nonnull Level level) {
        if (this.getIngredients().size() == 0) {
            return false;
        } else {
            return checkType(this.crystals, input.getCrystals()) &&
                   checkType(this.reapers, input.getReapers()) &&
                   checkType(this.others, input.getOthers());
        }
    }

    @SuppressWarnings("null")
    private boolean checkType(List<Ingredient> needed, List<ItemStack> input) {
        List<ItemStack> stacks = input.stream()
            .map(ItemStack::copy)
            .collect(Collectors.toList());

        for (Ingredient ing : needed) {
            boolean passed = false;
            for (Iterator<ItemStack> it = stacks.iterator(); it.hasNext(); ) {
                ItemStack stack = it.next();
                if (ing.test(stack)) {
                    ItemStack ingStack = Arrays.stream(ing.getItems())
                        .filter(
                            itemStack -> ItemStack.isSameItemSameComponents(stack, itemStack) && 
                                stack.getCount() >= itemStack.getCount() 
                        ).findFirst().orElse(ItemStack.EMPTY);
                    stack.shrink(ingStack.getCount());

                    if (stack.isEmpty()) {
                        it.remove();
                    }
                    
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                return false;
            }
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
        list.addAll(this.crystals);
        list.addAll(this.reapers);
        list.addAll(this.others);
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

    public List<Ingredient> getCrystals() {
        return this.crystals;
    }
    public List<Ingredient> getReapers() {
        return this.reapers;
    }
    public List<Ingredient> getOthers() {
        return this.others;
    }
    public ItemStack getResult() {
        return this.result;
    }
}
