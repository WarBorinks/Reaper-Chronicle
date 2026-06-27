package warborinks.mods.reaperchronicle.data.recipes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipe;
import warborinks.mods.reaperchronicle.world.item.crafting.ReaperRecipeIngredient;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

public final class ReaperRecipeBuilder implements RecipeBuilder {
    private final List<ReaperRecipeIngredient> crystals = new ArrayList<>();
    private final List<ReaperRecipeIngredient> reapers = new ArrayList<>();
    private final List<ReaperRecipeIngredient> others = new ArrayList<>();
    private final ItemStack result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group = "";

    public ReaperRecipeBuilder(ItemStack result) {
        this.result = result;
    }

    public static ReaperRecipeBuilder crystal(@Nonnull CrystalItem crystal) {
        return crystal(crystal, 1);
    }
    public static ReaperRecipeBuilder crystal(@Nonnull CrystalItem crystal, int count) {
        return new ReaperRecipeBuilder(new ItemStack(crystal, count));
    }
    
    public static ReaperRecipeBuilder crystal(@Nonnull Crystal crystal) {
        return crystal(crystal, 1);
    }
    public static ReaperRecipeBuilder crystal(@Nonnull Crystal crystal, int count) {
        return new ReaperRecipeBuilder(new ItemStack(crystal, count));
    }
    
    public static ReaperRecipeBuilder reaper(@Nonnull ReaperItem reaper) {
        return reaper(reaper, 1);
    }
    public static ReaperRecipeBuilder reaper(@Nonnull ReaperItem reaper, int count) {
        return new ReaperRecipeBuilder(new ItemStack(reaper, count));
    }
    
    public static ReaperRecipeBuilder reaper(@Nonnull Reaper reaper) {
        return reaper(reaper, 1);
    }
    public static ReaperRecipeBuilder reaper(@Nonnull Reaper reaper, int count) {
        return new ReaperRecipeBuilder(new ItemStack(reaper, count));
    }

    public ReaperRecipeBuilder addCrystal(ItemLike item, int count) {
        if (item instanceof CrystalItem crystalItem) {
            this.crystals.add(new ReaperRecipeIngredient(Ingredient.of(crystalItem), count));
        } else if (item instanceof Crystal crystal) {
            this.crystals.add(new ReaperRecipeIngredient(Ingredient.of(crystal), count));
        }
        return this;
    }
    public ReaperRecipeBuilder addCrystal(@Nonnull TagKey<Item> crystal, int count) {
        this.crystals.add(new ReaperRecipeIngredient(Ingredient.of(crystal), count));
        return this;
    }

    public ReaperRecipeBuilder addReaper(ItemLike item, int count) {
        if (item instanceof ReaperItem reaperItem) {
            this.reapers.add(new ReaperRecipeIngredient(Ingredient.of(reaperItem), count));
        } else if (item instanceof Reaper reaper) {
            this.reapers.add(new ReaperRecipeIngredient(Ingredient.of(reaper), count));
        }
        return this;
    }
    public ReaperRecipeBuilder addReaper(@Nonnull TagKey<Item> reaper, int count) {
        this.reapers.add(new ReaperRecipeIngredient(Ingredient.of(reaper), count));
        return this;
    }

    public ReaperRecipeBuilder addOther(ItemLike item, int count) {
        this.others.add(new ReaperRecipeIngredient(Ingredient.of(item), count));
        return this;
    }
    public ReaperRecipeBuilder addOther(@Nonnull TagKey<Item> item, int count) {
        this.others.add(new ReaperRecipeIngredient(Ingredient.of(item), count));
        return this;
    }
    
    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public ReaperRecipeBuilder unlockedBy(@Nonnull String name, @Nonnull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public ReaperRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    @SuppressWarnings("null")
    public void save(@Nonnull RecipeOutput output, @Nonnull ResourceLocation id) {
        Advancement.Builder adv = output.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(adv::addCriterion);
        ReaperRecipe recipe = new ReaperRecipe(this.group, this.crystals, this.reapers, this.others, this.result);
        output.accept(id, recipe, adv.build(id.withPrefix(
            "recipes/" + ReaperChronicle.MODID + "/" + RCRegistryNames.RecipeTypes.REAPER + "/"
        )));
    }
}
