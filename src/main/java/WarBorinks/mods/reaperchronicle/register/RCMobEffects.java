package warborinks.mods.reaperchronicle.register;

import warborinks.mods.reaperchronicle.mob_effect.NetherPoison;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RCMobEffects {
    private final DeferredRegister<MobEffect> MOB_EFFECT;

    public final DeferredHolder<MobEffect, NetherPoison> NETHER_POISON;

    RCMobEffects(DeferredRegister<MobEffect> mobEffect) {
        this.MOB_EFFECT = mobEffect;

        this.NETHER_POISON = MOB_EFFECT.register(
            "nether_poison", 
            () -> new NetherPoison(MobEffectCategory.HARMFUL, 0x03020c)
        );
    }
}
