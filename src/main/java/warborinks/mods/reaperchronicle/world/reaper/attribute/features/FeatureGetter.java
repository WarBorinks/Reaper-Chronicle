package warborinks.mods.reaperchronicle.world.reaper.attribute.features;

import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import net.neoforged.neoforgespi.language.ModFileScanData;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Args;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Result;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.TheSameFeatureException;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureAnnotations.Feature;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureAnnotations.FeatureToolset;

public final class FeatureGetter {
    private static final Class<Feature> featureAnnotationType = Feature.class;
    private static final Class<FeatureToolset> classAnnotationType = FeatureToolset.class;

    public static Map<String, Map<List<Class<?>>, FeatureInterface>> getFeatures(FeatureClass featureClass) {
        Class<? extends FeatureClass> cls = featureClass.getClass();
        Map<String, Map<List<Class<?>>, FeatureInterface>> features = new HashMap<>();

        MethodHandles.Lookup lookup;
        try {
            lookup = MethodHandles.privateLookupIn(cls, MethodHandles.lookup());
        } catch (IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }

        for (Method method : collectAnnotatedMethods(cls)) {
            Feature featureAnnotation = method.getAnnotation(featureAnnotationType);

            String name = featureAnnotation.name();
            Class<?>[] paramTypes = featureAnnotation.types();

            MethodHandle methodHandle;
            try {
                methodHandle = lookup.unreflect(method);
            } catch (IllegalAccessException exception) {
                continue;
            }

            FeatureInterface feature = args -> {
                return (Result) methodHandle.invoke(featureClass, args);
            };
            features.computeIfAbsent(name, k -> new HashMap<>())
                .merge(
                    List.of(paramTypes), feature,
                    (o, n) -> {
                        throw new TheSameFeatureException(name, paramTypes);
                    }
                );
        }

        return features;
    }

    private static Set<Method> collectAnnotatedMethods(Class<?> cls) {
        Set<Method> result = new LinkedHashSet<>();
        for (Method m : cls.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Feature.class)) {
                validateMethodSignature(m);
                result.add(m);
            }
        }

        Class<?> superClass = cls.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            result.addAll(collectAnnotatedMethods(superClass));
        }

        for (Class<?> iface : cls.getInterfaces()) {
            result.addAll(collectAnnotatedMethods(iface));
        }

        return result;
    }

    private static void validateMethodSignature(Method method) {
        if (!Modifier.isStatic(method.getModifiers())
                && method.getReturnType() == Result.class
                && method.getParameterCount() == 1
                && method.getParameterTypes()[0] == Args.class) {
            return;
        }
        throw new IllegalStateException("Invalid @Feature method signature: " + method);
    }

    public static Map<String, FeatureClass> getClasses(ModFileScanData modFileScanData) {
    return modFileScanData.getAnnotatedBy(classAnnotationType, ElementType.TYPE)
        .map(data -> {
            try {
                return Class.forName(data.clazz().getClassName());
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull)
        .filter(FeatureClass.class::isAssignableFrom) 
        .map(cls -> {
            try {
                String id = cls.getAnnotation(classAnnotationType).id();
                FeatureClass instance = (FeatureClass) cls.getDeclaredConstructor().newInstance();
                return new AbstractMap.SimpleEntry<>(id, instance);
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull)
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
    }
}
