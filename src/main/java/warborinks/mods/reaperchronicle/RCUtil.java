package warborinks.mods.reaperchronicle;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

@SuppressWarnings("null")
public class RCUtil {
    public static String getCreativeModeTabDescriptionId(@Nonnull CreativeModeTab creativeModeTab) {
        return Util.makeDescriptionId("creative_mode_tab", BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab));
    }
    
    public static String getCreativeModeTabDescriptionId(@Nonnull String namespace, @Nonnull String id) {
        return getCreativeModeTabDescriptionId(ResourceLocation.fromNamespaceAndPath(namespace, id));
    }
    public static String getCreativeModeTabDescriptionId(@Nonnull ResourceLocation resourceLocation) {
        return Util.makeDescriptionId("creative_mode_tab", resourceLocation);
    }

    public static boolean checkEmptyforComponent(Component component) {
        if (component.getString() == "") {
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
    
    public static void addComponentToComponentListWithCheckingEmpty(List<Component> list, Component component) {
        addComponentsToComponentListWithCheckingEmpty(list, List.of(component));
    }
    public static void addComponentsToComponentListWithCheckingEmpty(List<Component> list, List<Component> components) {
        for (Component component : components) {
            if (!checkEmptyforComponent(component)) {
                list.add(component);
            }
        }
    }
}
