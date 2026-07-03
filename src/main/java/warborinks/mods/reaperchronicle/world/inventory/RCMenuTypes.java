package warborinks.mods.reaperchronicle.world.inventory;

import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

public final class RCMenuTypes {
    private static final DeferredRegister<MenuType<?>> REGISTER = RCDeferredRegisters.MENU_TYPE;

    public static final DeferredHolder<MenuType<?>, MenuType<SoulAltarMenu>> SOUL_ALTAR = REGISTER.register(
        RCRegistryNames.MenuTypes.SOUL_ALTAR,
        () -> IMenuTypeExtension.create(SoulAltarMenu::new)
    );

    public static void load() {}
}
