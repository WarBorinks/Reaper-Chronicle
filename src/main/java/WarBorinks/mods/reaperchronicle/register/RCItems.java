package warborinks.mods.reaperchronicle.register;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCItems {
    private final DeferredRegister<Item> ITEM;

    public final DeferredHolder<Item, Item> NETHER_DEBRIS;

    RCItems(DeferredRegister<Item> item) {
        this.ITEM = item;

        this.NETHER_DEBRIS = this.ITEM.register(
            "nether_debris",
            () -> new Item(
                new Item.Properties()
                    .stacksTo(64)
            )
        );
    }
}
