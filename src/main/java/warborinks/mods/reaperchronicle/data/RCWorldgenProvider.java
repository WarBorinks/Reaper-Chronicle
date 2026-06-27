package warborinks.mods.reaperchronicle.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.data.worldgen.WorldgenProvider;
import warborinks.mods.reaperchronicle.data.worldgen.WorldgenSupplier;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;
import warborinks.mods.reaperchronicle.world.level.levelgen.feature.RCConfiguredFeatures;
import warborinks.mods.reaperchronicle.world.level.levelgen.feature.RCPlacedFeatures;
import warborinks.mods.reaperchronicle.world.level.levelgen.modifier.RCBiomeModifiers;

public final class RCWorldgenProvider extends WorldgenProvider {
    public RCWorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ReaperChronicle.MODID);
    }

    @Override
    protected void registerWorldgen() {
        registerConfiguredFeature(
            RCConfiguredFeatures.NETHER_SAND,
            ConfiguredFeatures.NETHER_SAND
        );
        registerConfiguredFeature(
            RCConfiguredFeatures.NETHER_SOIL,
            ConfiguredFeatures.NETHER_SOIL
        );
        
        registerPlacedFeature(
            RCPlacedFeatures.NETHER_SAND,
            PlacedFeatures.NETHER_SAND
        );
        registerPlacedFeature(
            RCPlacedFeatures.NETHER_SOIL,
            PlacedFeatures.NETHER_SOIL
        );
        
        registerBiomeModifier(
            RCBiomeModifiers.NETHER_SAND,
            BiomeModifiers.NETHER_SAND
        );
        registerBiomeModifier(
            RCBiomeModifiers.NETHER_SOIL,
            BiomeModifiers.NETHER_SOIL
        );
    }

    @SuppressWarnings("null")
    private static final class ConfiguredFeatures {
        public static final WorldgenSupplier<ConfiguredFeature<?, ?>> NETHER_SAND = ctx -> 
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

        public static final WorldgenSupplier<ConfiguredFeature<?, ?>> NETHER_SOIL = ctx -> 
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
    }

    @SuppressWarnings("null")
    private static final class PlacedFeatures {
        public static final WorldgenSupplier<PlacedFeature> NETHER_SAND = ctx -> {
            HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);
            Holder<ConfiguredFeature<?, ?>> configuredFeatureHolder = configuredFeatures.getOrThrow(
                RCConfiguredFeatures.NETHER_SAND
            );

            return new PlacedFeature(
                configuredFeatureHolder,
                List.of(
                    BiomeFilter.biome(),
                    CountPlacement.of(4),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32)),
                    InSquarePlacement.spread()
                )
            );
        };

        public static final WorldgenSupplier<PlacedFeature> NETHER_SOIL = ctx -> {
            HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);
            Holder<ConfiguredFeature<?, ?>> configuredFeatureHolder = configuredFeatures.getOrThrow(
                RCConfiguredFeatures.NETHER_SOIL
            );

            return new PlacedFeature(
                configuredFeatureHolder,
                List.of(
                    BiomeFilter.biome(),
                    CountPlacement.of(1),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32)),
                    InSquarePlacement.spread()
                )
            );
        };
    }

    @SuppressWarnings("null")
    private static final class BiomeModifiers {
        public static final WorldgenSupplier<BiomeModifier> NETHER_SAND = ctx -> {
            HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
            HolderSet<Biome> biomeHolderSet = biomes.getOrThrow(BiomeTags.IS_NETHER);

            HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
            Holder<PlacedFeature> placedFeatureHolder = placedFeatures.getOrThrow(RCPlacedFeatures.NETHER_SAND);

            return new AddFeaturesBiomeModifier(
                biomeHolderSet,
                HolderSet.direct(placedFeatureHolder),
                GenerationStep.Decoration.UNDERGROUND_ORES
            );
        };

        public static final WorldgenSupplier<BiomeModifier> NETHER_SOIL = ctx -> {
            HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
            HolderSet<Biome> biomeHolderSet = biomes.getOrThrow(BiomeTags.IS_NETHER);

            HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
            Holder<PlacedFeature> placedFeatureHolder = placedFeatures.getOrThrow(RCPlacedFeatures.NETHER_SOIL);

            return new AddFeaturesBiomeModifier(
                biomeHolderSet,
                HolderSet.direct(placedFeatureHolder),
                GenerationStep.Decoration.UNDERGROUND_ORES
            );
        };
    }
}
