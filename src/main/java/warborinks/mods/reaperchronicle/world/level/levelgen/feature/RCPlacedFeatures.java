package warborinks.mods.reaperchronicle.world.level.levelgen.feature;

import java.util.List;
import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCPlacedFeatures {
    public static final Function<BootstrapContext<PlacedFeature>, PlacedFeature> NETHER_SAND = ctx -> {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> configuredFeatureHolder = configuredFeatures.getOrThrow(
            RCConfiguredFeatures.Keys.NETHER_SAND
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
    
    public static final Function<BootstrapContext<PlacedFeature>, PlacedFeature> NETHER_SOIL = ctx -> {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> configuredFeatureHolder = configuredFeatures.getOrThrow(
            RCConfiguredFeatures.Keys.NETHER_SOIL
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
    
    public static final class Keys {
        public static final ResourceKey<PlacedFeature> NETHER_SAND = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SAND)
        );
        public static final ResourceKey<PlacedFeature> NETHER_SOIL = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SOIL)
        );
    }
}
