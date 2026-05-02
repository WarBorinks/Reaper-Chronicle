package warborinks.mods.reaperchronicle.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import warborinks.mods.reaperchronicle.world.item.RCBlockItems;
import warborinks.mods.reaperchronicle.world.item.CrystalItems;
import warborinks.mods.reaperchronicle.world.item.RCItems;

@SuppressWarnings("null")
public class RCRecipeProvider extends RecipeProvider {
    public RCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,
            CrystalItems.EMPTY_CRYSTAL.get())
            .pattern(" # ")
            .pattern("###")
            .pattern(" # ")
            .define('#', RCItems.NETHER_DEBRIS.get())
            .unlockedBy("has_nether_debris", has(RCItems.NETHER_DEBRIS.get()))
            .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,
            RCBlockItems.NETHER_SOIL.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .define('#', RCBlockItems.NETHER_SAND.get())
            .unlockedBy("has_nether_debris", has(RCBlockItems.NETHER_SAND.get()))
            .save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(RCBlockItems.NETHER_SOIL.get()),
            RecipeCategory.MISC,
            RCItems.NETHER_DEBRIS.get(),
            10, 300
        );
    }
}
