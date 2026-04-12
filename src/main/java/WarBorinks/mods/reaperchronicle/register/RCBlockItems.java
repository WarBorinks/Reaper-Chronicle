package warborinks.mods.reaperchronicle.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCBlockItems {
    private final DeferredRegister<Item> BLOCK_ITEM;

    public final DeferredHolder<Item, BlockItem> NETHER_SAND;
    
    public final DeferredHolder<Item, BlockItem> NETHER_SOIL;

    RCBlockItems(DeferredRegister<Item> blockItem) {
        this.BLOCK_ITEM = blockItem;
        
        this.NETHER_SAND = BLOCK_ITEM.register(
            "nether_sand", 
            () -> new BlockItem(
                RCDeferredRegisters.BLOCKS.NETHER_SAND.get(), 
                new Item.Properties()
            )
        );

        this.NETHER_SOIL = BLOCK_ITEM.register(
            "nether_soil",
            () -> new BlockItem(
                RCDeferredRegisters.BLOCKS.NETHER_SOIL.get(), 
                new Item.Properties()
            )
        );
    }
}
