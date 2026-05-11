package warborinks.mods.reaperchronicle.world.level.block;

import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public class RCBlocks {
    private static final DeferredRegister<Block> REGISTRAR = RCDeferredRegisters.BLOCK;

    public static final DeferredHolder<Block, NetherSand> NETHER_SAND = REGISTRAR.register(
        RCRegistryNames.Blocks.NETHER_SAND,
        () -> new NetherSand(new ColorRGBA(0x2e2e2e00),
            BlockBehaviour.Properties.of()
            .strength(0.5f, 10.0f)
            .sound(RCSoundTypes.NETHER_SAND)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static final DeferredHolder<Block, NetherSoil> NETHER_SOIL = REGISTRAR.register(
        RCRegistryNames.Blocks.NETHER_SOIL,
        () -> new NetherSoil(BlockBehaviour.Properties.of()
            .strength(0.5f, 10.0f)
            .sound(RCSoundTypes.NETHER_SOIL)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static final DeferredHolder<Block, SoulAltarBlock> SOUL_ALTAR = REGISTRAR.register(
        RCRegistryNames.BlockEntityTypes.SOUL_ALTAR,
        () -> new SoulAltarBlock(BlockBehaviour.Properties.of()
            .strength(4.0f, 20.0f)
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static void load() {}
}
