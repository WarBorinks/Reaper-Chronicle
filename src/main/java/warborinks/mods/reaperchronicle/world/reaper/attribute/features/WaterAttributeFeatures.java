package warborinks.mods.reaperchronicle.world.reaper.attribute.features;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

@SuppressWarnings("null")
public class WaterAttributeFeatures {
    @ReaperAttribute.Feature
    public static ReaperAttribute.Result useOn(ReaperAttribute.Args args) {
        UseOnContext context = args.get(0, UseOnContext.class);

        Level level = context.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos();
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_NONE);
            level.setBlock(pos, level.getBlockState(pos).setValue(LiquidBlock.LEVEL, 0), Block.UPDATE_ALL);

            Player player = context.getPlayer();
            level.playSound(player, pos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS);
        }
        
        return new ReaperAttribute.Result(InteractionResult.CONSUME);
    }
}
