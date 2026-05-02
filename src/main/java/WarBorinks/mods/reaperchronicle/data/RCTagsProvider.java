package warborinks.mods.reaperchronicle.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.tags.RCEntityTypeTags;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCTagsProvider {
    public static class RCBlockTagsProvider extends BlockTagsProvider {
        public RCBlockTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ReaperChronicle.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(BlockTags.NEEDS_IRON_TOOL)
                .add(RCBlocks.NETHER_SAND.get(),
                    RCBlocks.NETHER_SOIL.get());

            tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(RCBlocks.NETHER_SAND.get(),
                    RCBlocks.NETHER_SOIL.get());
        }
    }

    public static class RCEntityTypeTagsProvider extends EntityTypeTagsProvider {
        public RCEntityTypeTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ReaperChronicle.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(RCEntityTypeTags.NETHER_MOBS)
                .add(EntityType.BLAZE,
                    EntityType.ENDERMAN,
                    EntityType.GHAST,
                    EntityType.HOGLIN,
                    EntityType.MAGMA_CUBE,
                    EntityType.PIGLIN,
                    EntityType.PIGLIN_BRUTE,
                    EntityType.STRIDER,
                    EntityType.WITHER_SKELETON,
                    EntityType.ZOGLIN,
                    EntityType.ZOMBIFIED_PIGLIN)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                    ReaperChronicle.MODID,
                    RCRegistryNames.Tags.EntityTypeTags.NETEHR_MOBS
                ));
        }
    }
}
