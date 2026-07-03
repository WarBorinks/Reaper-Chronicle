package warborinks.mods.reaperchronicle.world.item.crafting;

import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public class RCIngredientTypes {
    private static final DeferredRegister<IngredientType<?>> REGISTER = RCDeferredRegisters.INGREDIENT_TYPE;

    public static final DeferredHolder<IngredientType<?>, IngredientType<ReaperRecipeIngredient>> REAPER = REGISTER.register(
        RCRegistryNames.IngredientTypes.REAPER,
        () -> new IngredientType<>(ReaperRecipeIngredient.CODEC)
    );

    public static void load() {}
}
