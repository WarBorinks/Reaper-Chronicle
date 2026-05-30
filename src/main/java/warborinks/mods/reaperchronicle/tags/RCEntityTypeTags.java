package warborinks.mods.reaperchronicle.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCEntityTypeTags {
    public static final TagKey<EntityType<?>> NETHER_MOBS = TagKey.create(
        Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.Tags.EntityTypeTags.NETEHR_MOBS
        )
    );
}
