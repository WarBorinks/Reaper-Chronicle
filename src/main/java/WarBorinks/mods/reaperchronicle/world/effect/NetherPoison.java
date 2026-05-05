package warborinks.mods.reaperchronicle.world.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@SuppressWarnings("null")
public class NetherPoison extends MobEffect {
    public NetherPoison(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            int damage = 1 << amplifier;

            if (entity.getType().getCategory().isFriendly() || entity instanceof Player) {
                damage <<= 1;
            } else {
                TagKey<EntityType<?>> undeadTag = TagKey.create(
                    Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "undead")
                );

                if (entity.getType().is(undeadTag)) {
                    damage >>= 1;
                }
            }

            if (damage < 1) {
                damage = 1;
            }

            entity.hurt(entity.damageSources().magic(), damage);
        }

        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        int k = 16 >> amplifier;
        if (k > 0) {
            return tick % k == 0;
        } else {
            return true;
        }
    }
}
