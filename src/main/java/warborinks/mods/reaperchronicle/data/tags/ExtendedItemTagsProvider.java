package warborinks.mods.reaperchronicle.data.tags;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

@SuppressWarnings("null")
public abstract class ExtendedItemTagsProvider extends ItemTagsProvider {
    private final CompletableFuture<TagsProvider.TagLookup<Crystal>> crystalTags;
    private final CompletableFuture<TagsProvider.TagLookup<Reaper>> reaperTags;
    
    private final Map<TagKey<Crystal>, TagKey<Item>> crystalTagsToCopy = new HashMap<>();
    private final Map<TagKey<Reaper>, TagKey<Item>> reaperTagsToCopy = new HashMap<>();

    public ExtendedItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
        CompletableFuture<TagsProvider.TagLookup<Block>> blockTags,
        CompletableFuture<TagsProvider.TagLookup<Crystal>> crystalTags,
        CompletableFuture<TagsProvider.TagLookup<Reaper>> reaperTags,
        String modid, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, modid, existingFileHelper);
        this.crystalTags = crystalTags;
        this.reaperTags = reaperTags;
    }

    protected void copyCrystal(TagKey<Crystal> crystalTag, TagKey<Item> itemTag) {
        this.crystalTagsToCopy.put(crystalTag, itemTag);
    }
    protected void copyReaper(TagKey<Reaper> reaperTag, TagKey<Item> itemTag) {
        this.reaperTagsToCopy.put(reaperTag, itemTag);
    }
    
    @Override
    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return super.createContentsProvider().thenCombine(
            this.crystalTags,
            (provider, crystalLookup) -> {
                this.crystalTagsToCopy.forEach((crystalTag, itemTag) -> {
                    TagBuilder tagBuilder = this.getOrCreateRawBuilder(itemTag);
                    Optional<TagBuilder> optional = crystalLookup.apply(crystalTag);
                    TagBuilder fromBuilder = optional.orElseThrow(
                        () -> new IllegalStateException("Missing crystal tag " + itemTag.location())
                    );
                    fromBuilder.build().forEach(tagBuilder::add);
                    fromBuilder.getRemoveEntries().forEach(tagBuilder::remove);
                });
                return provider;
            }
        ).thenCombine(
            this.reaperTags,
            (provider, reaperLookup) -> {
                this.reaperTagsToCopy.forEach((reaperTag, itemTag) -> {
                    TagBuilder tagBuilder = this.getOrCreateRawBuilder(itemTag);
                    Optional<TagBuilder> optional = reaperLookup.apply(reaperTag);
                    TagBuilder fromBuilder = optional.orElseThrow(
                        () -> new IllegalStateException("Missing reaper tag " + itemTag.location())
                    );
                    fromBuilder.build().forEach(tagBuilder::add);
                    fromBuilder.getRemoveEntries().forEach(tagBuilder::remove);
                });
                return provider;
            }
        );
    }
}
