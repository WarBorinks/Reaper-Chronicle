package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCItems {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(
        BuiltInRegistries.ITEM, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<Item, Item> NETHER_DEBRIS = ITEM.register(
        "nether_debris",
        () -> new Item(
            new Item.Properties()
                .stacksTo(64)
        )
    );

    public static final DeferredHolder<Item, BlockItem> NETHER_SOIL = ITEM.register(
        "nether_soil",
        () -> new BlockItem(
            RCBlocks.NETHER_SOIL.get(), 
            new Item.Properties()
        )
    );
    
    public static final DeferredHolder<Item, BlockItem> NETHER_SAND = ITEM.register(
        "nether_sand", 
        () -> new BlockItem(
            RCBlocks.NETHER_SAND.get(), 
            new Item.Properties()
        )
    );

    public static void register(IEventBus bus) {
        ITEM.register(bus);
    }
}
