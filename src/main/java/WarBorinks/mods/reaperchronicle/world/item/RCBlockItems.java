package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCBlockItems {
    private static final DeferredRegister<Item> REGISTER = RCDeferredRegisters.ITEM;

    public static final DeferredHolder<Item, BlockItem> NETHER_SAND = REGISTER.register(
        RCRegistryNames.Blocks.NETHER_SAND,
        () -> new BlockItem(
            RCBlocks.NETHER_SAND.get(),
            new Item.Properties()
        )
    );
    
    public static final DeferredHolder<Item, BlockItem> NETHER_SOIL = REGISTER.register(
        RCRegistryNames.Blocks.NETHER_SOIL,
        () -> new BlockItem(
            RCBlocks.NETHER_SOIL.get(),
            new Item.Properties()
        )
    );

    public static void load() {}
}
