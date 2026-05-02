package warborinks.mods.reaperchronicle.data.worldgen;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@SuppressWarnings("null")
public abstract class WorldgenProvider implements DataProvider {
    private final DatapackBuiltinEntriesProvider internalProvider;

    private final Map<ResourceKey<ConfiguredFeature<?, ?>>,
        Function<BootstrapContext<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>>> configuredFeatures;
    private final Map<ResourceKey<PlacedFeature>,
        Function<BootstrapContext<PlacedFeature>, PlacedFeature>> placedFeatures;
    private final Map<ResourceKey<BiomeModifier>,
        Function<BootstrapContext<BiomeModifier>, BiomeModifier>> biomeModifiers;

    public WorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid) {
        this.configuredFeatures = new HashMap<>();
        this.placedFeatures = new HashMap<>();
        this.biomeModifiers = new HashMap<>();
        this.registerWorldgen();

        RegistrySetBuilder builder = new RegistrySetBuilder();
        if (!this.configuredFeatures.isEmpty()) {
            builder.add(
                Registries.CONFIGURED_FEATURE,
                ctx -> this.configuredFeatures.forEach((key, val) -> ctx.register(key, val.apply(ctx)))
            );
        } if (!this.placedFeatures.isEmpty()) {
            builder.add(
                Registries.PLACED_FEATURE,
                ctx -> this.placedFeatures.forEach((key, val) -> ctx.register(key, val.apply(ctx)))
            );
        } if (!this.biomeModifiers.isEmpty()) {
            builder.add(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ctx -> this.biomeModifiers.forEach((key, val) -> ctx.register(key, val.apply(ctx)))
            );
        }

        this.internalProvider = new DatapackBuiltinEntriesProvider(output, registries, builder, Set.of(modid));
    }

    protected abstract void registerWorldgen();

    protected void registerConfiguredFeature(ResourceKey<ConfiguredFeature<?, ?>> entryKey,
        Function<BootstrapContext<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> factory) {
        this.configuredFeatures.put(entryKey, factory);
    }
    protected void registerPlacedFeature(ResourceKey<PlacedFeature> entryKey,
        Function<BootstrapContext<PlacedFeature>, PlacedFeature> factory) {
        this.placedFeatures.put(entryKey, factory);
    }
    protected void registerBiomeModifier(ResourceKey<BiomeModifier> entryKey,
        Function<BootstrapContext<BiomeModifier>, BiomeModifier> factory) {
        this.biomeModifiers.put(entryKey, factory);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.internalProvider.run(output);
    }

    @Override
    public String getName() {
        return "Worldgens";
    }
}
