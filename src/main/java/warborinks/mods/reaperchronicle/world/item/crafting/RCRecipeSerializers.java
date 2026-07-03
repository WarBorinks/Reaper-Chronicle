package warborinks.mods.reaperchronicle.world.item.crafting;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

public final class RCRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> REGISTER = RCDeferredRegisters.RECIPE_SERIALIZERS;

    public static final DeferredHolder<RecipeSerializer<?>, ReaperRecipeSerializer> REAPER_RECIPE = REGISTER.register(
        RCRegistryNames.RecipeSerializers.REAPER,
        () -> new ReaperRecipeSerializer()
    );

    public static void load() {}
}
