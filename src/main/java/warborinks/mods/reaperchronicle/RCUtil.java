package warborinks.mods.reaperchronicle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;

import javax.annotation.Nonnull;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.ModFileScanData;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Result;

public final class RCUtil {
    public static ModFileScanData getModFileScanDataByModContainer(ModContainer modContainer) {
        return modContainer.getModInfo().getOwningFile().getFile().getScanResult();
    }
    public static ModFileScanData getModFileScanDataByModId(String modid) {
        return ModList.get().getModFileById(modid).getFile().getScanResult();
    }

    public static String makeCreativeModeTabDescriptionId(@Nonnull ResourceLocation resourceLocation) {
        return Util.makeDescriptionId("itemGroup", resourceLocation);
    }
    @SuppressWarnings("null")
    public static String makeCreativeModeTabDescriptionId(@Nonnull CreativeModeTab creativeModeTab) {
        return makeCreativeModeTabDescriptionId(BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab));
    }
    @SuppressWarnings("null")
    public static String makeCreativeModeTabDescriptionId(@Nonnull String namespace, @Nonnull String id) {
        return makeCreativeModeTabDescriptionId(ResourceLocation.fromNamespaceAndPath(namespace, id));
    }

    public static boolean checkEmptyforComponent(Component component) {
        if (component == null) {
            return false;
        } else if (component.getString() == "") {
            return false;
        } else if (component instanceof TranslatableContents translatableContents) {
            if (component.getString() == translatableContents.getKey()) {
                return false;
            }
        }

        return true;
    }

    public static Component joinComponentsWithIgnoringEmpty(List<Component> components) {
        return joinComponentsWithIgnoringEmpty(components, "");
    }
    @SuppressWarnings("null")
    public static Component joinComponentsWithIgnoringEmpty(List<Component> components, String space) {
        MutableComponent res = Component.empty();
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (checkEmptyforComponent(component)) {
                continue;
            }

            if (i > 0) {
                res.append(space);
            }
            res.append(component);
        }

        return res;
    }
    
    public static void addComponentToComponentListWithIngnoringEmpty(List<Component> list, Component component) {
        addComponentsToComponentListWithIngnoringEmpty(list, List.of(component));
    }
    public static void addComponentsToComponentListWithIngnoringEmpty(List<Component> list, List<Component> components) {
        for (Component component : components) {
            if (!checkEmptyforComponent(component)) {
                list.add(component);
            }
        }
    }

    private static final Map<Method, MethodHandle> METHOD_HANDLE_CACHE = new HashMap<>();
    public static <T> Result invokeMethod(T instance, Class<?> beCalled, Method method,
        List<Object> args) throws Throwable {
        MethodHandle methodHandle;
        if (METHOD_HANDLE_CACHE.containsKey(method)) {
            methodHandle = METHOD_HANDLE_CACHE.get(method);
        } else {   
            MethodHandles.Lookup lookup;
            try {
                lookup = MethodHandles.privateLookupIn(beCalled, MethodHandles.lookup());
                methodHandle = lookup.unreflect(method);
            } catch (Exception exception) {
                return Result.empty();
            }
        }

        List<Object> argList = new ArrayList<>();
        argList.add(instance);
        argList.addAll(args);
        return Result.of(methodHandle.invoke(
            argList.toArray()
        ));
    }
    public static <T> Result invokeMethod(T instance, Method method, List<Object> args) throws Throwable {
        return invokeMethod(instance, instance.getClass(), method, args);
    }
    public static <T> Result invokeMethod(T instance, Class<?> beCalled, Method method, Object arg) throws Throwable {
        return invokeMethod(instance, beCalled, method, List.of(arg));
    }
    public static <T> Result invokeMethod(T instance, Method method, Object arg) throws Throwable {
        return invokeMethod(instance, instance.getClass(), method, arg);
    }

    public static <T, R> Result invokeMethod(T instance, Class<?> beCalled, String name,
        Class<R> resultType, List<Class<?>> argTypes, List<Object> args) throws Throwable {
        Method method;
        try {
            method = beCalled.getMethod(name, argTypes.toArray(new Class<?>[0]));
        } catch (Exception exception) {
            return Result.empty();
        }

        return invokeMethod(instance, beCalled, method, args);
    }
    public static <T, R> Result invokeMethod(T instance, String name,
        Class<R> resultType, List<Class<?>> argTypes, List<Object> args) throws Throwable {
        return invokeMethod(instance, instance.getClass(), name, resultType, argTypes, args);
    }
    public static <T, R> Result invokeMethod(T instance, Class<?> beCalled, String name,
        Class<R> resultType, Class<?> argType, Object arg) throws Throwable {
        return invokeMethod(instance, beCalled, name, resultType, List.of(argType), List.of(arg));
    }
    public static <T, R> Result invokeMethod(T instance, String name,
        Class<R> resultType, Class<?> argType, Object arg) throws Throwable {
        return invokeMethod(instance, instance.getClass(), name, resultType, argType, arg);
    }
    public static <T, R, U> Collector<T, Map<R, U>, List<T>> getMergedListCollector(
            Function<T, R> keyGetter, Function<T, U> valueGetter,
            BiFunction<U, U, U> add,
            Function<Map.Entry<R, U>, T> constructor,
            Function<List<T>, List<T>> finisher
        ) {
            return Collector.of(
                HashMap<R, U>::new,
                (map, v) -> map.merge(keyGetter.apply(v), valueGetter.apply(v), add),
                (a, b) -> {
                    b.forEach((k, v) -> a.merge(k, v, add));
                    return a;
                },
                map -> finisher.apply(map.entrySet().stream()
                    .map(constructor)
                    .toList())
            );
        }
    public static <T, R, U> Collector<T, Map<R, U>, List<T>> getMergedListCollector(
        Function<T, R> keyGetter, Function<T, U> valueGetter,
        BiFunction<U, U, U> add,
        Function<Map.Entry<R, U>, T> constructor
    ) {
        return getMergedListCollector(keyGetter, valueGetter, add, constructor, Collections::unmodifiableList);
    }
}
