package warborinks.mods.reaperchronicle.world.reaper.attribute.features;

import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Args;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Result;

@FunctionalInterface
public interface FeatureInterface {
    public Result invoke(Args args) throws Throwable;
}
