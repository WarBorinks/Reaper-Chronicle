package warborinks.mods.reaperchronicle.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

public class RCMobEffects {
    private static final DeferredRegister<MobEffect> REGISTER = RCDeferredRegisters.MOB_EFFECT;

    public static final DeferredHolder<MobEffect, NetherPoison> NETHER_POISON = REGISTER.register(
        RCRegistryNames.MobEffects.NETHER_POISON,
        () -> new NetherPoison(MobEffectCategory.HARMFUL, 0x03020c)

    );

    public static void load() {}
}
