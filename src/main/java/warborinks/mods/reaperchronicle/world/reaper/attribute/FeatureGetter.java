package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class FeatureGetter {
    private static final Class<ReaperAttribute.Feature> annotation = ReaperAttribute.Feature.class;

    static Map<String, Function<ReaperAttribute.Args, ReaperAttribute.Result>> get(Class<?> cls) {
        Map<String, Function<ReaperAttribute.Args, ReaperAttribute.Result>> features = new HashMap<>();
        for (Method method : cls.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(annotation)) {
                continue;
            } if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            } if (method.getReturnType() != ReaperAttribute.Result.class) {
                continue;
            } if (method.getParameterCount() != 1) {
                continue;
            } if (method.getParameterTypes()[0] != ReaperAttribute.Args.class) {
                continue;
            }

            method.setAccessible(true);

            Function<ReaperAttribute.Args, ReaperAttribute.Result> feature = args -> {
                try {
                    return (ReaperAttribute.Result) method.invoke(null, args);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to invoke static method: " + method.getName(), e);
                }
            };
            features.put(method.getName(), feature);
        }

        return features;
    }
}
