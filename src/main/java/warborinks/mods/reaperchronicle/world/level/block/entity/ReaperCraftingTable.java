package warborinks.mods.reaperchronicle.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReaperCraftingTable extends BlockEntity {
    public ReaperCraftingTable(BlockPos pos, BlockState state) {
        super(RCBlockEntityTypes.REAPER_CRAFTING_TABLE.get(), pos, state);
    }
}
