package warborinks.mods.reaperchronicle.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.data.worldgen.WorldgenProvider;
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
            RCConfiguredFeatures.Keys.NETHER_SAND,
            RCConfiguredFeatures.NETHER_SAND
        );
        registerConfiguredFeature(
            RCConfiguredFeatures.Keys.NETHER_SOIL,
            RCConfiguredFeatures.NETHER_SOIL
        );
        
        registerPlacedFeature(
            RCPlacedFeatures.Keys.NETHER_SAND,
            RCPlacedFeatures.NETHER_SAND
        );
        registerPlacedFeature(
            RCPlacedFeatures.Keys.NETHER_SOIL,
            RCPlacedFeatures.NETHER_SOIL
        );
        
        registerBiomeModifier(
            RCBiomeModifiers.Keys.NETHER_SAND,
            RCBiomeModifiers.NETHER_SAND
        );
        registerBiomeModifier(
            RCBiomeModifiers.Keys.NETHER_SOIL,
            RCBiomeModifiers.NETHER_SOIL
        );
    }
}
