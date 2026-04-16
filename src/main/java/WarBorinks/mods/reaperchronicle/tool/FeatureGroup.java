package warborinks.mods.reaperchronicle.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FeatureGroup {
    private Map<String, Function<FeatureArgs, FeatureResult>> features;

    public FeatureGroup() {
        this.features = new HashMap<>();
    }

    public void addFeature(String name, Function<FeatureArgs, FeatureResult> feature) {
        if (feature != null) {
            this.features.put(name, feature);
        }
    }

    public <T> T apply(String name, Class<T> resType, Object... objects) {
        if (this.findFeature(name)) {
            return this.features.get(name).apply(new FeatureArgs(objects)).get(resType);
        } else {
            return null;
        }
    }

    public boolean findFeature(String name) {
        return this.features.containsKey(name);
    }
}
