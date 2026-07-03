package warborinks.mods.reaperchronicle.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCRecipeTypes {
    private static final DeferredRegister<RecipeType<?>> REGISTER = RCDeferredRegisters.RECIPE_TYPE;

    public static final DeferredHolder<RecipeType<?>, RecipeType<ReaperRecipe>> REAPER_RECIPE = REGISTER.register(
        RCRegistryNames.RecipeTypes.REAPER,
        () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.RecipeTypes.REAPER
        ))
    );

    public static void load() {}
}
