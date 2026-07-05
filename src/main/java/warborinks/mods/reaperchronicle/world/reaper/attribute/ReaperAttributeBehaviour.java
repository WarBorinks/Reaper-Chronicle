package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.IFeatureClass;

public class ReaperAttributeBehaviour {
    protected final Properties properties;
    private final Map<String, Map<List<Class<?>>, FeatureInterface>> featureMap = new ConcurrentHashMap<>();

    protected ReaperAttributeBehaviour(Properties properties) {
        this.properties = properties;
    }

    void complete() {
        this.featureMap.putAll(this.properties.featureMap);
    }

    public boolean findFeature(@Nonnull String name) {
        return this.featureMap.containsKey(name);
    }

    @SuppressWarnings("null")
    public boolean findFeature(@Nonnull String name, @Nonnull List<Class<?>> classes) {
        if (!this.findFeature(name)) {
            return false;
        }

        classes = classes.stream()
            .filter(cls -> cls != null && RCUtil.boxed(cls) != Void.class)
            .toList();

        return this.featureMap.get(name).containsKey(classes);
    }
    @SuppressWarnings("null")
    public boolean findFeature(@Nonnull String name, @Nonnull Class<?>... classes) {
        return this.findFeature(name, List.of(classes));
    }

    public Map.Entry<List<Class<?>>, FeatureInterface> find(String name, Object... args)
        throws NoSuchFeatureException {
        Map<List<Class<?>>, FeatureInterface> features = this.featureMap.get(name);
        if (features == null) {
            throw new NoSuchFeatureException("Feature not found: " + name);
        }

        List<Class<?>> argTypes = Arrays.stream(args)
            .<Class<?>>map(obj -> obj == null ? null : obj.getClass())
            .toList();
        
        List<Map.Entry<List<Class<?>>, FeatureInterface>> candidates = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, FeatureInterface> feature : features.entrySet()) {
            if (feature.getKey().size() == args.length) {
                candidates.add(feature);
            }
        }

        if (candidates.isEmpty()) {
            throw new NoSuchFeatureException(
                "No overload of '" + name + "' takes " + args.length + " argument(s)"
            );
        }

        List<Map.Entry<List<Class<?>>, FeatureInterface>> compatible = candidates.stream()
            .filter(e -> this.isCompatible(e.getKey(), argTypes))
            .toList();

        if (compatible.isEmpty()) {
            throw new IllegalArgumentException(
                "Argument types " + argTypes + " do not match any overload of '" + name + "'"
            );
        }

        return this.findMostSpecific(compatible, name);
    }

    private boolean isCompatible(List<Class<?>> paramTypes, List<Class<?>> argTypes) {
        for (int i = 0; i < paramTypes.size(); i++) {
            Class<?> paramType = paramTypes.get(i);
            Class<?> argType = argTypes.get(i);
            if (argType == null) {
                if (paramType.isPrimitive()) {
                    return false;
                }
            } else {
                if (!isAssignable(paramType, argType)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAssignable(Class<?> paramType, Class<?> argType) {
        paramType = RCUtil.boxed(paramType);
        argType = RCUtil.boxed(argType);
        return paramType.isAssignableFrom(argType);
    }

    private Map.Entry<List<Class<?>>, FeatureInterface> findMostSpecific(
        List<Map.Entry<List<Class<?>>, FeatureInterface>> candidates, String name) {
        if (candidates.size() == 1) {
            return candidates.getFirst();
        }

        List<Map.Entry<List<Class<?>>, FeatureInterface>> mostSpecific = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, FeatureInterface> candidate : candidates) {
            boolean isMostSpecific = true;
            for (Map.Entry<List<Class<?>>, FeatureInterface> other : candidates) {
                if (candidate == other) {
                    continue;
                } else if (this.isMoreSpecific(other.getKey(), candidate.getKey())) {
                    isMostSpecific = false;
                    break;
                }
            }

            if (isMostSpecific) {
                mostSpecific.add(candidate);
            }
        }

        if (mostSpecific.size() == 1) {
            return mostSpecific.getFirst();
        } else {
            throw new AmbiguousFeatureException(
                "Ambiguous feature overload for '" + name + "'. " + "Candidates: " + mostSpecific
            );
        }
    }

    private boolean isMoreSpecific(List<Class<?>> types1, List<Class<?>> types2) {
        for (int i = 0; i < types1.size(); i++) {
            if (!types2.get(i).isAssignableFrom(types1.get(i))) {
                return false;
            }
        }

        return true;
    }

    protected static class Properties {
        private final Map<String, Map<List<Class<?>>, FeatureInterface>> featureMap = new ConcurrentHashMap<>();

        public Properties addFeature(@Nonnull String name,
            @Nonnull FeatureInterface feature, @Nonnull List<Class<?>> argTypes) {
            this.featureMap.computeIfAbsent(name, key -> new ConcurrentHashMap<>())
                .put(
                    argTypes.stream()
                        .filter(cls -> cls != null && RCUtil.boxed(cls) != Void.class)
                        .toList(),
                    feature
                );

            return this;
        }
        @SuppressWarnings("null")
        public Properties addFeature(@Nonnull String name,
            @Nonnull FeatureInterface feature, @Nonnull Class<?>... argTypes) {
            return this.addFeature(name, feature, List.of(argTypes));
        }

        public Properties addFeatures(@Nonnull Map<String, Map<List<Class<?>>, FeatureInterface>> map) {
            map.forEach((name, features) -> {
                features.forEach((argTypes, feature) -> {
                    if (name != null && argTypes != null && feature != null) {
                        this.addFeature(name, feature, argTypes);
                    }
                });
            });
            return this;
        }

        public Properties addFeatures(@Nonnull IFeatureClass featureClass) {
            return this.addFeatures(FeatureGetter.getFeatures(featureClass));
        }
    }

    public static final class AmbiguousFeatureException extends RuntimeException {
        public AmbiguousFeatureException(String message) {
            super(message);
        }
    }

    public static final class NoSuchFeatureException extends NoSuchMethodException {
        public NoSuchFeatureException(String message) {
            super(message);
        }
    }
}
