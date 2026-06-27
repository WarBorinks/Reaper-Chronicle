package warborinks.mods.reaperchronicle.world.level.levelgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCPlacedFeatures {
    public static final ResourceKey<PlacedFeature> NETHER_SAND = ResourceKey.create(
        Registries.PLACED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.PlacedFeatures.NETHER_SAND
        ));
    public static final ResourceKey<PlacedFeature> NETHER_SOIL = ResourceKey.create(
        Registries.PLACED_FEATURE,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.PlacedFeatures.NETHER_SOIL
        ));
}
