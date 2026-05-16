package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.Reapers;

public class ReaperItems {
    private static final DeferredRegister<Item> REGISTRAR = RCDeferredRegisters.ITEM;

    public static final DeferredHolder<Item, ReaperItem> COMMON_REAPER = REGISTRAR.register(
        RCRegistryNames.Reapers.COMMON_REAPER,
        () -> new ReaperItem(
            Reapers.COMMON_REAPER,
            new Item.Properties()
        )
    );

    public static void load() {}
}
