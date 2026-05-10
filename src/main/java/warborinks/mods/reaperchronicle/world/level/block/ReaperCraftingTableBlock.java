package warborinks.mods.reaperchronicle.world.level.block;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import warborinks.mods.reaperchronicle.world.level.block.entity.ReaperCraftingTable;

public class ReaperCraftingTableBlock extends Block implements EntityBlock {
    public ReaperCraftingTableBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ReaperCraftingTable(pos, state);
    }
}
