package warborinks.mods.reaperchronicle.register;

import warborinks.mods.reaperchronicle.block.NetherSand;
import warborinks.mods.reaperchronicle.block.NetherSoil;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCBlocks {
    private final DeferredRegister<Block> BLOCK;

    public final DeferredHolder<Block, NetherSand> NETHER_SAND;
    public final DeferredHolder<Block, NetherSoil> NETHER_SOIL;

    RCBlocks(DeferredRegister<Block> block) {
        this.BLOCK = block;

        this.NETHER_SAND = BLOCK.register(
            "nether_sand", 
            () -> new NetherSand(new ColorRGBA(0x2e2e2e00), 
                BlockBehaviour.Properties.of()
                .strength(0.5f, 10.0f)
                .sound(RCDeferredRegisters.SOUND_EVENTS.SOUND_TYPES.NETHER_SAND)
                .requiresCorrectToolForDrops()
                .mapColor(MapColor.COLOR_GRAY)
            )
        );

        this.NETHER_SOIL = BLOCK.register(
            "nether_soil", 
            () -> new NetherSoil(BlockBehaviour.Properties.of()
                .strength(0.5f, 10.0f)
                .sound(RCDeferredRegisters.SOUND_EVENTS.SOUND_TYPES.NETHER_SOIL)
                .requiresCorrectToolForDrops()
                .mapColor(MapColor.COLOR_GRAY)
            )
        );
    }
}
