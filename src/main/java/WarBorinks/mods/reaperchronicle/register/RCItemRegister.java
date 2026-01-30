package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCItemRegister {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(
        BuiltInRegistries.ITEM, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<Item, BlockItem> NETHER_SOIL = ITEM.register(
        "nether_soil",
        () -> new BlockItem(
            RCBlockRegister.NETHER_SOIL.get(), 
            new Item.Properties()
        )
    );

    public static void register(IEventBus bus) {
        ITEM.register(bus);
    }
}
