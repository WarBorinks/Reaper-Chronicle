package warborinks.mods.reaperchronicle.world.level.levelgen.feature;

import java.util.List;
import java.util.function.Function;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCConfiguredFeatures {
    public static final Function<BootstrapContext<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> NETHER_SAND = ctx -> 
        new ConfiguredFeature<>(
            Feature.ORE,
            new OreConfiguration(
                List.of(
                    OreConfiguration.target(
                        new BlockMatchTest(Blocks.SOUL_SAND),
                        RCBlocks.NETHER_SAND.get().defaultBlockState()
                    ),
                    OreConfiguration.target(
                        new BlockMatchTest(Blocks.SOUL_SOIL),
                        RCBlocks.NETHER_SAND.get().defaultBlockState()
                    ),
                    OreConfiguration.target(
                        new TagMatchTest(BlockTags.BASE_STONE_NETHER),
                        RCBlocks.NETHER_SAND.get().defaultBlockState()
                    )
                ),
                10
            )
        );

    public static final Function<BootstrapContext<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> NETHER_SOIL = ctx -> 
        new ConfiguredFeature<>(
            Feature.ORE,
            new OreConfiguration(
                List.of(
                    OreConfiguration.target(
                        new BlockMatchTest(Blocks.SOUL_SAND),
                        RCBlocks.NETHER_SOIL.get().defaultBlockState()
                    ),
                    OreConfiguration.target(
                        new BlockMatchTest(Blocks.SOUL_SOIL),
                        RCBlocks.NETHER_SOIL.get().defaultBlockState()
                    ),
                    OreConfiguration.target(
                        new TagMatchTest(BlockTags.BASE_STONE_NETHER),
                        RCBlocks.NETHER_SOIL.get().defaultBlockState()
                    )
                ),
                5
            )
        );

    public static class Keys {
        public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SAND = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SAND)
        );
        public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SOIL = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SOIL)
        );
    }
}
