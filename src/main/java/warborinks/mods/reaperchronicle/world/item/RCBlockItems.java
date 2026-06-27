package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public final class RCBlockItems {
    private static final DeferredRegister<Item> REGISTRAR = RCDeferredRegisters.ITEM;

    public static final DeferredHolder<Item, BlockItem> NETHER_SAND = REGISTRAR.register(
        RCRegistryNames.Items.NETHER_SAND,
        () -> new BlockItem(
            RCBlocks.NETHER_SAND.get(),
            new Item.Properties()
        )
    );
    
    public static final DeferredHolder<Item, BlockItem> NETHER_SOIL = REGISTRAR.register(
        RCRegistryNames.Items.NETHER_SOIL,
        () -> new BlockItem(
            RCBlocks.NETHER_SOIL.get(),
            new Item.Properties()
        )
    );

    public static final DeferredHolder<Item, BlockItem> SOUL_ALTAR = REGISTRAR.register(
        RCRegistryNames.Items.SOUL_ALTAR,
        () -> new BlockItem(
            RCBlocks.SOUL_ALTAR.get(),
            new Item.Properties()
        )
    );

    public static void load() {}
}
