package warborinks.mods.reaperchronicle.data;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCLootTableProvider extends LootTableProvider {
    public RCLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
            output, Collections.emptySet(),
            List.of(
                new SubProviderEntry(RCBlockLootSubProvider::new, LootContextParamSets.BLOCK)
            ),
            registries
        );
    }

    private static class RCBlockLootSubProvider extends BlockLootSubProvider {
        public RCBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            dropSelf(RCBlocks.NETHER_SAND.get());
            dropSelf(RCBlocks.NETHER_SOIL.get());
            dropSelf(RCBlocks.SOUL_ALTAR.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return RCDeferredRegisters.BLOCK.getEntries()
                .stream()
                .map((e) -> (Block) e.value())
                .toList();
        }
    }
}
