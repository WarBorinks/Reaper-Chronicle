package warborinks.mods.reaperchronicle.world.reaper.reaper_attribute;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.reaper_attribute.features.WaterAttributeFeatures;

public class ReaperAttributes {
    private static final DeferredRegister<ReaperAttribute> REGISTER = RCDeferredRegisters.ATTRIBUTE;

    public static final DeferredHolder<ReaperAttribute, ReaperAttribute> EMPTY_ATTRIBUTE = REGISTER.register(
        RCRegistryNames.ReaperAttributes.EMPTY_ATTRIBUTE,
        () -> new ReaperAttribute(0xffffff)
    );

    public static final DeferredHolder<ReaperAttribute, ReaperAttribute> WATER_ATTRIBUTE = REGISTER.register(
        RCRegistryNames.ReaperAttributes.WATER_ATTRIBUTE,
        () -> new ReaperAttribute(0x0000ff)
            .addFeatures(WaterAttributeFeatures.class)
    );

    public static void load() {}
}
