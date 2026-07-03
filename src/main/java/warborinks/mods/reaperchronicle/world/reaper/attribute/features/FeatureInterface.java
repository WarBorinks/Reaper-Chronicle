package warborinks.mods.reaperchronicle.world.reaper.attribute.features;

import warborinks.mods.reaperchronicle.util.Args;
import warborinks.mods.reaperchronicle.util.Result;

@FunctionalInterface
public interface FeatureInterface {
    public Result invoke(Args args) throws Throwable;
}
