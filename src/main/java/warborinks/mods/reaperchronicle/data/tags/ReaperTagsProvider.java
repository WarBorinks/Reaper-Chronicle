package warborinks.mods.reaperchronicle.data.tags;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;

@SuppressWarnings("null")
public abstract class ReaperTagsProvider extends IntrinsicHolderTagsProvider<Reaper> {
    public ReaperTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
        String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, RCRegistries.Keys.REAPER, lookupProvider,
            reaper -> RCRegistries.REAPER.getResourceKey(reaper).orElseThrow(),
            modid, existingFileHelper);
    }
}
