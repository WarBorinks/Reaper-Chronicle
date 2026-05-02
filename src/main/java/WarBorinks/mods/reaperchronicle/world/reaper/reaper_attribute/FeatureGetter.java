package warborinks.mods.reaperchronicle.world.reaper.reaper_attribute;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class FeatureGetter {
    private static final Class<FeatureGroup.Feature> annotation = FeatureGroup.Feature.class;

    static Map<String, Function<FeatureGroup.Args, FeatureGroup.Result>> get(Class<?> cls) {
        Map<String, Function<FeatureGroup.Args, FeatureGroup.Result>> features = new HashMap<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            } if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            } if (method.getReturnType() != FeatureGroup.Result.class) {
                continue;
            } if (method.getParameterCount() != 1) {
                continue;
            } if (method.getParameterTypes()[0] != FeatureGroup.Args.class) {
                continue;
            }

            method.setAccessible(true);

            Function<FeatureGroup.Args, FeatureGroup.Result> feature = args -> {
                try {
                    return (FeatureGroup.Result) method.invoke(null, args);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to invoke static method: " + method.getName(), e);
                }
            };
            features.put(method.getName(), feature);
        }

        return features;
    }
}
