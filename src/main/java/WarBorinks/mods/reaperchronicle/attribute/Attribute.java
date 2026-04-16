package warborinks.mods.reaperchronicle.attribute;

import java.util.function.Function;

import warborinks.mods.reaperchronicle.tool.FeatureArgs;
import warborinks.mods.reaperchronicle.tool.FeatureGroup;
import warborinks.mods.reaperchronicle.tool.FeatureResult;

public class Attribute {
    protected FeatureGroup features;

    public Attribute() {
        this.features = new FeatureGroup();
    }

    public void addAttribute(String name, Function<FeatureArgs, FeatureResult> attribute) {
        this.features.addFeature(name, attribute);
    }

    public <T> T useAttribute(String name, Class<T> resType, Object... objects) {
        return this.features.apply(name, resType, objects);
    }

    public boolean findAttribute(String name) {
        return this.features.findFeature(name);
    }
}
