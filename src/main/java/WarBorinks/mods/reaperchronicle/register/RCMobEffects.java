package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import WarBorinks.mods.reaperchronicle.mob_effect.NetherPoison;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(
        BuiltInRegistries.MOB_EFFECT, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<MobEffect, NetherPoison> NETHER_POISON = MOB_EFFECT.register(
        "nether_poison", 
        () -> new NetherPoison(MobEffectCategory.HARMFUL, 0x03020c)
    );

    public static void register(IEventBus bus) {
        MOB_EFFECT.register(bus);
    }
}
