package warborinks.mods.reaperchronicle.core.registries;

import javax.annotation.Nonnull;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

@SuppressWarnings("null")
public class RCRegistryNames {
    public static class Blocks {
        public static final String NETHER_SAND = "nether_sand";
        public static final String NETHER_SOIL = "nether_soil";
    }

    public static class CreativeModeTabs {
        public static final String CRYSTALS = "crystals";
        public static final String CRYSTAL_INGREDIENTS = "crystal_ingredients";
        
        public static String getCreativeModeTabDescriptionId(@Nonnull CreativeModeTab creativeModeTab) {
            return Util.makeDescriptionId("creative_mode_tab", BuiltInRegistries.CREATIVE_MODE_TAB.getKey(creativeModeTab));
        }

        public static String getCreativeModeTabDescriptionId(@Nonnull String namespace, @Nonnull String id) {
            return getCreativeModeTabDescriptionId(ResourceLocation.fromNamespaceAndPath(namespace, id));
        }
        public static String getCreativeModeTabDescriptionId(@Nonnull ResourceLocation resourceLocation) {
            return Util.makeDescriptionId("creative_mode_tab", resourceLocation);
        }
    }

    public static class Crystals {
        public static final String EMPTY_CRYSTAL = "empty_crystal";
        public static final String WATER_CRYSTAL = "water_crystal";
    }

    public static class Items {
        public static final String NETEHR_DEBRIS = "nether_debris";
    }

    public static class MobEffects {
        public static final String NETHER_POISON = "nether_poison";
    }

    public static class ReaperAttributes {
        public static final String EMPTY_ATTRIBUTE = "empty_attribute";
        public static final String WATER_ATTRIBUTE = "water_attribute";
    }

    public static class SoundEvents {
        public static final String NETHER_SAND_BREAK = "block.nether_sand.break";
        public static final String NETHER_SAND_STEP = "block.nether_sand.step";
        public static final String NETHER_SAND_PLACE = "block.nether_sand.place";
        public static final String NETHER_SAND_HIT = "block.nether_sand.hit";
        public static final String NETHER_SAND_FALL = "block.nether_sand.fall";

        public static final String NETHER_SOIL_BREAK = "block.nether_soil.break";
        public static final String NETHER_SOIL_STEP = "block.nether_soil.step";
        public static final String NETHER_SOIL_PLACE = "block.nether_soil.place";
        public static final String NETHER_SOIL_HIT = "block.nether_soil.hit";
        public static final String NETHER_SOIL_FALL = "block.nether_soil.fall";
    }

    public static class Tags {
        public static class EntityTypeTags {
            public static final String NETEHR_MOBS = "nether_mobs";
        }
    }
}
