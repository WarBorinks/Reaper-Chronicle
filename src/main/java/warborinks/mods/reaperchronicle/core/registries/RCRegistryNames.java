package warborinks.mods.reaperchronicle.core.registries;

import javax.annotation.Nonnull;

public final class RCRegistryNames {
    public static final class BiomeModifiers {
        @Nonnull public static final String NETHER_SAND = Blocks.NETHER_SAND;
        @Nonnull public static final String NETHER_SOIL = Blocks.NETHER_SOIL;
    }

    public static final class Blocks {
        @Nonnull public static final String NETHER_SAND = "nether_sand";
        @Nonnull public static final String NETHER_SOIL = "nether_soil";
        @Nonnull public static final String SOUL_ALTAR = BlockEntityTypes.SOUL_ALTAR;
    }

    public static final class BlockEntityTypes {
        @Nonnull public static final String SOUL_ALTAR = "soul_altar";
    }

    public static final class ConfiguredFeatures {
        @Nonnull public static final String NETHER_SAND = Blocks.NETHER_SAND;
        @Nonnull public static final String NETHER_SOIL = Blocks.NETHER_SOIL;
    }

    public static final class CreativeModeTabs {
        @Nonnull public static final String CRYSTALS = "crystals";
        @Nonnull public static final String CRYSTAL_INGREDIENTS = "crystal_ingredients";
        @Nonnull public static final String REAPERS = "reapers";
    }

    public static final class Crystals {
        @Nonnull public static final String EMPTY_CRYSTAL = "empty_crystal";
        @Nonnull public static final String WATER_CRYSTAL = "water_crystal";
    }

    public static final class DataComponentTypes {
        @Nonnull public static final String ENCHANTED = "enchanted";
        @Nonnull public static final String TEXT_INDEX = "text_index";
    }

    public static final class IngredientTypes {
        @Nonnull public static final String REAPER = RecipeTypes.REAPER;
    }

    public static final class Items {
        @Nonnull public static final String COMMON_REAPER = Reapers.COMMON_REAPER;
        @Nonnull public static final String EMPTY_CRYSTAL = Crystals.EMPTY_CRYSTAL;
        @Nonnull public static final String NETEHR_DEBRIS = "nether_debris";
        @Nonnull public static final String NETHER_SAND = Blocks.NETHER_SAND;
        @Nonnull public static final String NETHER_SOIL = Blocks.NETHER_SOIL;
        @Nonnull public static final String SOUL_ALTAR = BlockEntityTypes.SOUL_ALTAR;
        @Nonnull public static final String WATER_CRYSTAL = Crystals.WATER_CRYSTAL;
    }

    public static final class MenuTypes {
        @Nonnull public static final String SOUL_ALTAR = BlockEntityTypes.SOUL_ALTAR;
    }

    public static final class MobEffects {
        @Nonnull public static final String NETHER_POISON = "nether_poison";
    }

    public static final class Reapers {
        @Nonnull public static final String COMMON_REAPER = "common_reaper";
    }

    public static final class ReaperAttributes {
        @Nonnull public static final String EMPTY_ATTRIBUTE = "empty_attribute";
        @Nonnull public static final String WATER_ATTRIBUTE = "water_attribute";
    }

    public static final class RecipeSerializers {
        @Nonnull public static final String REAPER = RecipeTypes.REAPER;
    }

    public static final class RecipeTypes {
        @Nonnull public static final String REAPER = "reaper";
    }

    public static final class Registries {
        @Nonnull public static String CRYSTAL = "crystal";
        @Nonnull public static String REAPER = "reaper";
        @Nonnull public static String REAPER_ATTRIBUTE = "reaper_attribute";
    }
    
    public static final class PlacedFeatures {
        @Nonnull public static final String NETHER_SAND = Blocks.NETHER_SAND;
        @Nonnull public static final String NETHER_SOIL = Blocks.NETHER_SOIL;
    }

    public static final class SoundEvents {
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

    public static final class Tags {
        public static final class EntityTypeTags {
            @Nonnull public static final String NETEHR_MOBS = "nether_mobs";
        }
    }
}
