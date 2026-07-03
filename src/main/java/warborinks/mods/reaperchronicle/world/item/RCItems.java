package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCItems {
    private static final DeferredRegister<Item> REGISTER = RCDeferredRegisters.ITEM;

    public static final DeferredHolder<Item, Item> NETHER_DEBRIS = REGISTER.register(
        RCRegistryNames.Items.NETEHR_DEBRIS,
        () -> new Item(
            new Item.Properties()
                .stacksTo(64)
        )
    );

    public static void load() {
        CrystalItems.load();
        ReaperItems.load();
        
        RCBlockItems.load();
    }
}
