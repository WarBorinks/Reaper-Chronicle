package warborinks.mods.reaperchronicle.world.level.levelgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SAND = ResourceKey.create(
        Registries.CONFIGURED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.ConfiguredFeatures.NETHER_SAND
        ));
        
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SOIL = ResourceKey.create(
        Registries.CONFIGURED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.ConfiguredFeatures.NETHER_SOIL
        ));
}
