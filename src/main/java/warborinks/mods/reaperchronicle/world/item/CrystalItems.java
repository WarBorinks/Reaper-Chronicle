package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystals;

public class CrystalItems {
    private static final DeferredRegister<Item> REGISTRAR = RCDeferredRegisters.ITEM;

    public static final DeferredHolder<Item, CrystalItem> EMPTY_CRYSTAL = REGISTRAR.register(
        RCRegistryNames.Crystals.EMPTY_CRYSTAL,
        () -> new CrystalItem(
            Crystals.EMPTY_CRYSTAL,
            new Item.Properties()
        )
    );

    public static final DeferredHolder<Item, CrystalItem> WATER_CRYSTAL = REGISTRAR.register(
        RCRegistryNames.Crystals.WATER_CRYSTAL,
        () -> new CrystalItem(
            Crystals.WATER_CRYSTAL,
            new Item.Properties()
        )
    );

    public static void load() {}
}
