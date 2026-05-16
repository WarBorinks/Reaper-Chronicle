package warborinks.mods.reaperchronicle.world.reaper.crystal;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributes;

public class Crystals {
    private static final DeferredRegister<Crystal> REGISTRAR = RCDeferredRegisters.CRYSTAL;

    public static final DeferredHolder<Crystal, Crystal> EMPTY_CRYSTAL = REGISTRAR.register(
        RCRegistryNames.Crystals.EMPTY_CRYSTAL,
        () -> new Crystal()
    );

    public static final DeferredHolder<Crystal, Crystal> WATER_CRYSTAL = REGISTRAR.register(
        RCRegistryNames.Crystals.WATER_CRYSTAL,
        () -> new Crystal(ReaperAttributes.WATER_ATTRIBUTE)
    );

    public static void load() {}
}
