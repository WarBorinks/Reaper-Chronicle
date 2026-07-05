package warborinks.mods.reaperchronicle.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.IFeatureClass;

public class AddFeaturesEvent extends Event implements IModBusEvent {
    private final ReaperAttribute reaperAttribute;
    private final Map<String, Map<List<Class<?>>, FeatureInterface>> features = new HashMap<>();
    private final Set<IFeatureClass> featureClasses = new HashSet<>();

    public AddFeaturesEvent(ReaperAttribute reaperAttribute) {
        this.reaperAttribute = reaperAttribute;
    }

    public ReaperAttribute getReaperAttribute() {
        return this.reaperAttribute;
    }

    public Map<String, Map<List<Class<?>>, FeatureInterface>> getFeatures() {
        return this.features;
    }

    public Set<IFeatureClass> getFeatureClasses() {
        return this.featureClasses;
    }

    public void addFeature(@Nonnull String name,
        @Nonnull FeatureInterface feature, @Nonnull List<Class<?>> argTypes) {
        this.features.computeIfAbsent(name, key -> new ConcurrentHashMap<>())
            .put(
                argTypes.stream()
                    .filter(cls -> cls != null && RCUtil.boxed(cls) != Void.class)
                    .toList(),
                feature
            );
    }
    @SuppressWarnings("null")
    public void addFeature(@Nonnull String name,
        @Nonnull FeatureInterface feature, @Nonnull Class<?>... argTypes) {
        this.addFeature(name, feature, List.of(argTypes));
    }

    public void addFeatures(@Nonnull Map<String, Map<List<Class<?>>, FeatureInterface>> map) {
        map.forEach((name, features) -> {
            features.forEach((argTypes, feature) -> {
                if (name != null && argTypes != null && feature != null) {
                    this.addFeature(name, feature, argTypes);
                }
            });
        });
    }

    public void addFeatures(@Nonnull IFeatureClass featureClass) {
        this.featureClasses.add(featureClass);
    }
}
