package warborinks.mods.reaperchronicle.world.level.levelgen.modifier;

import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.levelgen.feature.RCPlacedFeatures;

@SuppressWarnings("null")
public final class RCBiomeModifiers {
    public static final Function<BootstrapContext<BiomeModifier>, BiomeModifier> NETHER_SAND = ctx -> {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderSet<Biome> biomeHolderSet = biomes.getOrThrow(BiomeTags.IS_NETHER);

        HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> placedFeatureHolder = placedFeatures.getOrThrow(RCPlacedFeatures.Keys.NETHER_SAND);

        return new AddFeaturesBiomeModifier(
            biomeHolderSet,
            HolderSet.direct(placedFeatureHolder),
            GenerationStep.Decoration.UNDERGROUND_ORES
        );
    };
    
    public static final Function<BootstrapContext<BiomeModifier>, BiomeModifier> NETHER_SOIL = ctx -> {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderSet<Biome> biomeHolderSet = biomes.getOrThrow(BiomeTags.IS_NETHER);

        HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> placedFeatureHolder = placedFeatures.getOrThrow(RCPlacedFeatures.Keys.NETHER_SOIL);

        return new AddFeaturesBiomeModifier(
            biomeHolderSet,
            HolderSet.direct(placedFeatureHolder),
            GenerationStep.Decoration.UNDERGROUND_ORES
        );
    };

    public static final class Keys {
        public static final ResourceKey<BiomeModifier> NETHER_SAND = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SAND)
        );
        public static final ResourceKey<BiomeModifier> NETHER_SOIL = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Blocks.NETHER_SOIL)
        );
    }
}
