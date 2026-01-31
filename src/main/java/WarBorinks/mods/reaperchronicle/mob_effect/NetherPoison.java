package WarBorinks.mods.reaperchronicle.mob_effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("null")
public class NetherPoison extends MobEffect {
    public NetherPoison(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            int damage = 2 << amplifier;
            if (entity.getType().getCategory().isFriendly()) {
                damage <<= 1;
            }

            entity.hurt(entity.damageSources().magic(), damage);
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        int k = 64 >> amplifier;
        return tick % k == 0;
    }
}
