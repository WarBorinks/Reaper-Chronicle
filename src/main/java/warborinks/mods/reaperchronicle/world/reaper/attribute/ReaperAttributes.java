package warborinks.mods.reaperchronicle.world.reaper.attribute;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

public final class ReaperAttributes {
    private static final DeferredRegister<ReaperAttribute> REGISTER = RCDeferredRegisters.REAPER_ATTRIBUTE;

    public static final DeferredHolder<ReaperAttribute, ReaperAttribute> EMPTY_ATTRIBUTE = REGISTER.register(
        RCRegistryNames.ReaperAttributes.EMPTY_ATTRIBUTE,
        () -> new ReaperAttribute(0xffffff, new ReaperAttributeBehaviour.Properties())
    );

    public static final DeferredHolder<ReaperAttribute, ReaperAttribute> WATER_ATTRIBUTE = REGISTER.register(
        RCRegistryNames.ReaperAttributes.WATER_ATTRIBUTE,
        () -> new ReaperAttribute(0x0000ff, new ReaperAttributeBehaviour.Properties())
    );

    public static void load() {}
}
