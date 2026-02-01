package WarBorinks.mods.reaperchronicle.block;

import WarBorinks.mods.reaperchronicle.register.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("null")
public class NetherSand extends ColoredFallingBlock {
    public NetherSand(ColorRGBA dustColor, BlockBehaviour.Properties properties) {
        super(dustColor, properties);
    }
    
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(
                RCMobEffects.NETHER_POISON, 256
            ));
        }

        super.stepOn(level, pos, state, entity);
    }
}
