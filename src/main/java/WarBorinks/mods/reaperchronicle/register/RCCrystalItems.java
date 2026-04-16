package warborinks.mods.reaperchronicle.register;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.item.CrystalItem;

public class RCCrystalItems {
    private final DeferredRegister<Item> CRYSTAL_ITEM;

    public final DeferredHolder<Item, CrystalItem> EMPTY_CRYSTAL;

    RCCrystalItems(DeferredRegister<Item> crystal_item) {
        this.CRYSTAL_ITEM = crystal_item;

        this.EMPTY_CRYSTAL = this.CRYSTAL_ITEM.register(
            "empty_crystal",
            () -> new CrystalItem(
                () -> RCDeferredRegisters.CRYSTALS.EMPTY_CRYSTAL.get(),
                new Item.Properties()
            )
        );
    }
}
