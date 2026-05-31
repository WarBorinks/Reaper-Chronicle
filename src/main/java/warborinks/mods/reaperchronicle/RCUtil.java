package warborinks.mods.reaperchronicle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

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

    public static String getCreativeModeTabDescriptionId(@Nonnull CreativeModeTab creativeModeTab) {
        return Util.makeDescriptionId("creative_mode_tab", BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab));
    }
    
    @SuppressWarnings("null")
    public static String getCreativeModeTabDescriptionId(@Nonnull String namespace, @Nonnull String id) {
        return getCreativeModeTabDescriptionId(ResourceLocation.fromNamespaceAndPath(namespace, id));
    }
    public static String getCreativeModeTabDescriptionId(@Nonnull ResourceLocation resourceLocation) {
        return Util.makeDescriptionId("creative_mode_tab", resourceLocation);
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

    public static <T, R> Result invokeMethodFromInstance(T instance, Class<?> beCalled, String name,
        Class<R> resultType, List<Class<?>> argTypes, List<Object> args) throws Throwable {
        MethodHandles.Lookup lookup;
        try {
            lookup = MethodHandles.privateLookupIn(beCalled, MethodHandles.lookup());
        } catch (IllegalAccessException exception) {
            return Result.empty();
        }

        MethodHandle methodHandle;
        try {
            methodHandle = lookup.unreflect(beCalled.getMethod(name, argTypes.toArray(new Class<?>[0])));
        } catch (Exception exception) {
            return Result.empty();
        }

        List<Object> argList = new ArrayList<>();
        argList.add(instance);
        argList.addAll(args);
        return Result.of(methodHandle.invoke(
            argList.toArray()
        ));
    }
    public static <T, R> Result invokeMethodFromInstance(T instance, String name,
        Class<R> resultType, List<Class<?>> argTypes, List<Object> args) throws Throwable {
        return invokeMethodFromInstance(instance, instance.getClass(), name, resultType, argTypes, args);
    }
    public static <T, R> Result invokeMethodFromInstance(T instance, Class<?> beCalled, String name,
        Class<R> resultType, Class<?> argType, List<Object> args) throws Throwable {
        return invokeMethodFromInstance(instance, beCalled, name, resultType, List.of(argType), args);
    }
    public static <T, R> Result invokeMethodFromInstance(T instance, String name,
        Class<R> resultType, Class<?> argType, List<Object> args) throws Throwable {
        return invokeMethodFromInstance(instance, instance.getClass(), name, resultType, argType, args);
    }
}
