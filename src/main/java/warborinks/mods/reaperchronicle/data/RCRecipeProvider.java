package warborinks.mods.reaperchronicle.data;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.data.recipes.ReaperRecipeBuilder;
import warborinks.mods.reaperchronicle.world.item.CrystalItems;
import warborinks.mods.reaperchronicle.world.item.RCBlockItems;
import warborinks.mods.reaperchronicle.world.item.RCItems;

public final class RCRecipeProvider extends RecipeProvider {
    public RCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    @SuppressWarnings("null")
    protected void buildRecipes(@Nonnull RecipeOutput recipeOutput) {
        ReaperRecipeBuilder.crystal(CrystalItems.EMPTY_CRYSTAL.get())
            .addOther(RCItems.NETHER_DEBRIS.get(), 9)
            .unlockedBy("has_" + RCRegistryNames.Items.NETEHR_DEBRIS, has(RCItems.NETHER_DEBRIS.get()))
            .save(recipeOutput);
        ReaperRecipeBuilder.crystal(CrystalItems.WATER_CRYSTAL.get())
            .addCrystal(CrystalItems.EMPTY_CRYSTAL.get(), 1)
            .addOther(Items.WATER_BUCKET, 1)
            .unlockedBy("has_" + RCRegistryNames.Items.EMPTY_CRYSTAL, has(CrystalItems.EMPTY_CRYSTAL.get()))
            .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RCBlockItems.NETHER_SOIL.get())
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .define('#', RCBlockItems.NETHER_SAND.get())
            .unlockedBy("has_" + RCRegistryNames.Blocks.NETHER_SAND, has(RCBlockItems.NETHER_SAND.get()))
            .save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(RCBlockItems.NETHER_SOIL.get()),
            RecipeCategory.MISC,
            RCItems.NETHER_DEBRIS.get(),
            10, 300
        );
    }
}
