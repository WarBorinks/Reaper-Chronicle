package warborinks.mods.reaperchronicle.world.level.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import warborinks.mods.reaperchronicle.world.level.block.entity.SoulAltar;

public class SoulAltarBlock extends BaseEntityBlock {
    private static final MapCodec<SoulAltarBlock> CODEC = simpleCodec(SoulAltarBlock::new);

    public SoulAltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new SoulAltar(pos, state);
    }
    
    @Override
    protected InteractionResult useWithoutItem(@Nonnull BlockState state, @Nonnull Level level,
        @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        MenuProvider menuProvider = state.getMenuProvider(level, pos);
        if (menuProvider != null) {
            player.openMenu(menuProvider, pos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    @SuppressWarnings("null")
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos,
        @Nonnull BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SoulAltar altar && !movedByPiston) {
                if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
                    List<ItemStack> drops = new ArrayList<>();
                    drops.addAll(altar.getCrystals());
                    drops.addAll(altar.getReapers());
                    drops.addAll(altar.getOthers());

                    for (ItemStack stack : drops) {
                        Block.popResource(level, pos, stack.copy());
                    }
                }
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
