package warborinks.mods.reaperchronicle.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import warborinks.mods.reaperchronicle.ReaperChronicle;

@EventBusSubscriber(modid = ReaperChronicle.MODID)
public final class Datagen {
    @SubscribeEvent
    private static void onGatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        RCBlockStateProvider blockStateProvider = new RCBlockStateProvider(output, existingFileHelper);
        RCItemModelProvider itemModelProvider = new RCItemModelProvider(output, existingFileHelper);
        RCSoundDefinitionsProvider soundDefinitionsProvider = new RCSoundDefinitionsProvider(output, existingFileHelper);

        RCLanguageProvider enusLanguageProvider = new RCLanguageProvider(output, "en_us");
        RCLanguageProvider zhcnLanguageProvider = new RCLanguageProvider(output, "zh_cn");
        RCLanguageProvider lzhLanguageProvider = new RCLanguageProvider(output, "lzh");

        RCLootTableProvider lootTableProvider = new RCLootTableProvider(output, lookupProvider);
        RCRecipeProvider recipeProvider = new RCRecipeProvider(output, lookupProvider);
        RCWorldgenProvider worldgenProvider = new RCWorldgenProvider(output, lookupProvider);

        RCTagsProvider.RCBlockTagsProvider blockTagsProvider = new RCTagsProvider.RCBlockTagsProvider(
            output, lookupProvider, existingFileHelper
        );
        RCTagsProvider.RCEntityTypeTagsProvider entityTypeTagsProvider = new RCTagsProvider.RCEntityTypeTagsProvider(
            output, lookupProvider, existingFileHelper
        );

        if (event.includeClient()) {
            event.addProvider(blockStateProvider);
            event.addProvider(itemModelProvider);
            event.addProvider(soundDefinitionsProvider);

            event.addProvider(enusLanguageProvider);
            event.addProvider(zhcnLanguageProvider);
            event.addProvider(lzhLanguageProvider);
        } if (event.includeServer()) {
            event.addProvider(lootTableProvider);
            event.addProvider(recipeProvider);
            event.addProvider(worldgenProvider);

            event.addProvider(blockTagsProvider);
            event.addProvider(entityTypeTagsProvider);
        }
    }
}
