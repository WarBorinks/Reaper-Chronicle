package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import WarBorinks.mods.reaperchronicle.block.NetherSoil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCBlockRegister {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(
        BuiltInRegistries.BLOCK, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<Block, NetherSoil> NETHER_SOIL = BLOCK.register(
        "nether_soil", 
        () -> new NetherSoil(BlockBehaviour.Properties.of()
            .strength(0.5f)
            .sound(RCSoundEventRegister.SoundTypes.NETHER_SOIL)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.COLOR_GRAY)
        )
    );

    public static void register(IEventBus bus) {
        BLOCK.register(bus);
    }
}
