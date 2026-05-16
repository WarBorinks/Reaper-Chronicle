package warborinks.mods.reaperchronicle.core.registries;

import javax.annotation.Nonnull;

public class RCRegistryNames {
    public static class Blocks {
        @Nonnull public static final String NETHER_SAND = "nether_sand";
        @Nonnull public static final String NETHER_SOIL = "nether_soil";
    }

    public static class BlockEntityTypes {
        @Nonnull public static final String SOUL_ALTAR = "soul_altar";
    }

    public static class CreativeModeTabs {
        @Nonnull public static final String CRYSTALS = "crystals";
        @Nonnull public static final String CRYSTAL_INGREDIENTS = "crystal_ingredients";
        @Nonnull public static final String REAPERS = "reapers";
    }

    public static class Crystals {
        @Nonnull public static final String EMPTY_CRYSTAL = "empty_crystal";
        @Nonnull public static final String WATER_CRYSTAL = "water_crystal";
    }

    public static class DataComponentTypes {
        @Nonnull public static final String TEXT_INDEX = "text_index";
    }

    public static class Items {
        @Nonnull public static final String NETEHR_DEBRIS = "nether_debris";
    }

    public static class MobEffects {
        @Nonnull public static final String NETHER_POISON = "nether_poison";
    }

    public static class Reapers {
        @Nonnull public static final String COMMON_REAPER = "common_reaper";
    }

    public static class ReaperAttributes {
        @Nonnull public static final String EMPTY_ATTRIBUTE = "empty_attribute";
        @Nonnull public static final String WATER_ATTRIBUTE = "water_attribute";
    }

    public static class RecipeSerializers {
        @Nonnull public static final String REAPER = "reaper";
    }

    public static class RecipeTypes {
        @Nonnull public static final String REAPER = "reaper";
    }

    public static class Registries {
        @Nonnull public static String CRYSTAL = "crystal";
        @Nonnull public static String REAPER = "reaper";
        @Nonnull public static String REAPER_ATTRIBUTE = "reaper_attribute";
    }

    public static class SoundEvents {
        @Nonnull public static final String NETHER_SAND_BREAK = "block.nether_sand.break";
        @Nonnull public static final String NETHER_SAND_STEP = "block.nether_sand.step";
        @Nonnull public static final String NETHER_SAND_PLACE = "block.nether_sand.place";
        @Nonnull public static final String NETHER_SAND_HIT = "block.nether_sand.hit";
        @Nonnull public static final String NETHER_SAND_FALL = "block.nether_sand.fall";

        @Nonnull public static final String NETHER_SOIL_BREAK = "block.nether_soil.break";
        @Nonnull public static final String NETHER_SOIL_STEP = "block.nether_soil.step";
        @Nonnull public static final String NETHER_SOIL_PLACE = "block.nether_soil.place";
        @Nonnull public static final String NETHER_SOIL_HIT = "block.nether_soil.hit";
        @Nonnull public static final String NETHER_SOIL_FALL = "block.nether_soil.fall";
    }

    public static class Tags {
        public static class EntityTypeTags {
            @Nonnull public static final String NETEHR_MOBS = "nether_mobs";
        }
    }
}
