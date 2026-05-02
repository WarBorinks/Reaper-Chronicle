package warborinks.mods.reaperchronicle.world.reaper.crystal;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.reaper_attribute.ReaperAttributes;

public class Crystals {
    private static final DeferredRegister<Crystal> REGISTER = RCDeferredRegisters.CRYSTAL;

    public static final DeferredHolder<Crystal, Crystal> EMPTY_CRYSTAL = REGISTER.register(
        RCRegistryNames.Crystals.EMPTY_CRYSTAL,
        () -> new Crystal(() -> ReaperAttributes.EMPTY_ATTRIBUTE.get())
    );

    public static final DeferredHolder<Crystal, Crystal> WATER_CRYSTAL = REGISTER.register(
        RCRegistryNames.Crystals.WATER_CRYSTAL,
        () -> new Crystal(() -> ReaperAttributes.WATER_ATTRIBUTE.get())
    );

    public static void load() {}
}
