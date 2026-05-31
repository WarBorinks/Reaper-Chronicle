package warborinks.mods.reaperchronicle.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;

public class NetherSoil extends Block {
    public NetherSoil(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("null")
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(
                RCMobEffects.NETHER_POISON,
                128
            ));
        }

        super.stepOn(level, pos, state, entity);
    }
}
