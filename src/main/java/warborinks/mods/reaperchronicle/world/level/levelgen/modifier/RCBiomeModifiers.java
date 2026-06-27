package warborinks.mods.reaperchronicle.world.level.levelgen.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCBiomeModifiers {
    public static final ResourceKey<BiomeModifier> NETHER_SAND = ResourceKey.create(
        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.BiomeModifiers.NETHER_SAND
        ));
    public static final ResourceKey<BiomeModifier> NETHER_SOIL = ResourceKey.create(
        NeoForgeRegistries.Keys.BIOME_MODIFIERS,
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.BiomeModifiers.NETHER_SOIL
        ));
}
