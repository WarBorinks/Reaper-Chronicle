package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
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

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforgespi.language.ModFileScanData;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.IFeatureClass;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureAnnotations.Feature;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureAnnotations.FeatureToolset;

public final class FeatureGetter {
    private static final Class<Feature> featureAnnotationType = Feature.class;
    private static final Class<FeatureToolset> classAnnotationType = FeatureToolset.class;

    public static @Nonnull Map<String, Map<List<Class<?>>, FeatureInterface>> getFeatures(IFeatureClass featureClass) {
        Class<? extends IFeatureClass> cls = featureClass.getClass();
        Map<String, Map<List<Class<?>>, FeatureInterface>> features = new HashMap<>();

        for (Method method : collectAnnotatedMethods(cls)) {
            Feature featureAnnotation = method.getAnnotation(featureAnnotationType);

            String name = featureAnnotation.name().isEmpty() ? method.getName() : featureAnnotation.name();
            Class<?>[] paramTypes = method.getParameterTypes();

            FeatureInterface feature = args -> {
                return RCUtil.invokeMethod(featureClass, cls, method, args.getValues());
            };
            
            features.computeIfAbsent(name, k -> new HashMap<>())
                .put(List.of(paramTypes), feature);
        }

        return features;
    }

    private static Set<Method> collectAnnotatedMethods(Class<?> cls) {
        Set<Method> result = new LinkedHashSet<>();
        for (Method m : cls.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Feature.class)) {
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
    public static Map<ResourceLocation, List<IFeatureClass>> getClasses(ModFileScanData modFileScanData) {
        return modFileScanData.getAnnotatedBy(classAnnotationType, ElementType.TYPE)
            .map(data -> {
                try {
                    return Class.forName(data.clazz().getClassName());
                } catch (ClassNotFoundException e) {
                    ReaperChronicle.LOGGER.warn(
                        "Failed to load class: {}",
                        data.clazz().getClassName()
                    );
                    return null;
                }
            }).filter(Objects::nonNull)
            .filter(IFeatureClass.class::isAssignableFrom) 
            .map(cls -> {
                try {
                    FeatureToolset featureToolset = cls.getAnnotation(classAnnotationType);
                    ResourceLocation key = ResourceLocation.fromNamespaceAndPath(
                        featureToolset.namespace(), featureToolset.id()
                    );
                    IFeatureClass instance = (IFeatureClass) cls.getDeclaredConstructor().newInstance();
                    return new AbstractMap.SimpleEntry<>(key, instance);
                } catch (Exception e) {
                    ReaperChronicle.LOGGER.warn(
                        "Failed to instantiate or read annotation from class: {}",
                        cls.getName()
                    );
                    return null;
                }
            }).filter(Objects::nonNull)
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
