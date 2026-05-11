package warborinks.mods.reaperchronicle.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SoulAltar extends BlockEntity {
    public SoulAltar(BlockPos pos, BlockState state) {
        super(RCBlockEntityTypes.SOUL_ALTAR.get(), pos, state);
    }
}
