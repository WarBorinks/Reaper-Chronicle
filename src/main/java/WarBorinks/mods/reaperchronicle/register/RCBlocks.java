package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import WarBorinks.mods.reaperchronicle.block.NetherSand;
import WarBorinks.mods.reaperchronicle.block.NetherSoil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCBlocks {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(
        BuiltInRegistries.BLOCK, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<Block, NetherSoil> NETHER_SOIL = BLOCK.register(
        "nether_soil", 
        () -> new NetherSoil(BlockBehaviour.Properties.of()
            .strength(0.5f, 10.0f)
            .sound(RCSoundEvents.SoundTypes.NETHER_SOIL)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static final DeferredHolder<Block, NetherSand> NETHER_SAND = BLOCK.register(
        "nether_sand", 
        () -> new NetherSand(new ColorRGBA(0x2e2e2e00), 
            BlockBehaviour.Properties.of()
            .strength(0.5f, 10.0f)
            .sound(RCSoundEvents.SoundTypes.NETHER_SAND)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static void register(IEventBus bus) {
        BLOCK.register(bus);
    }
}
