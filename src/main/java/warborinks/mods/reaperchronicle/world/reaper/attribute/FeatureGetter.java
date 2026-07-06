package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforgespi.language.ModFileScanData;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.Feature;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureToolset;

final class FeatureGetter {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Class<Feature> featureAnnotationType = Feature.class;
    private static final Class<FeatureToolset> classAnnotationType = FeatureToolset.class;

    static @Nonnull Map<String, Map<List<Class<?>>, FeatureInterface>> getFeatures(Class<?> cls) {
        Map<String, Map<List<Class<?>>, FeatureInterface>> features = new HashMap<>();
        for (Method method : collectAnnotatedMethods(cls)) {
            Feature featureAnnotation = method.getAnnotation(featureAnnotationType);

            String name = featureAnnotation.name().isEmpty() ? method.getName() : featureAnnotation.name();
            Class<?>[] paramTypes = method.getParameterTypes();

            FeatureInterface feature = args -> {
                return RCUtil.invokeMethod(null, cls, method, args.getValues());
            };
            
            features.computeIfAbsent(name, k -> new HashMap<>())
                .put(List.of(paramTypes), feature);
        }

        return features;
    }

    private static Set<Method> collectAnnotatedMethods(Class<?> cls) {
        Set<Method> result = new LinkedHashSet<>();
        for (Method m : cls.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(Feature.class)) {
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

    @SuppressWarnings("null")
    static Map<ResourceLocation, List<Class<?>>> getClasses(ModFileScanData modFileScanData) {
        return modFileScanData.getAnnotatedBy(classAnnotationType, ElementType.TYPE)
            .map(data -> {
                try {
                    return Class.forName(data.clazz().getClassName());
                } catch (ClassNotFoundException e) {
                    LOGGER.warn(
                        "Failed to load class: {}",
                        data.clazz().getClassName()
                    );
                    return null;
                }
            }).filter(Objects::nonNull)
            .map(cls -> {
                FeatureToolset featureToolset = cls.getAnnotation(classAnnotationType);
                ResourceLocation key = ResourceLocation.fromNamespaceAndPath(
                    featureToolset.namespace(), featureToolset.id()
                );
                return new AbstractMap.SimpleEntry<>(key, cls);
            })
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> new ArrayList<>(List.of(entry.getValue())),
                (a, b) -> {
                    a.addAll(b);
                    return a;
                }
            ));
    }
}
