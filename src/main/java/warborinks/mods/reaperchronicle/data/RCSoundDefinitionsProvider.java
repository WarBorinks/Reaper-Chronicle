package warborinks.mods.reaperchronicle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.sounds.RCSoundEvents;
import warborinks.mods.reaperchronicle.sounds.RCSubtitles;

@SuppressWarnings("null")
public final class RCSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public RCSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ReaperChronicle.MODID, existingFileHelper);
    }

    private SoundDefinition.Sound[] createSounds(String name, Integer amount) {
        List<String> t = Arrays.asList(name.split("\\."));
        String path = String.join("/", t.toArray(new String[0])) + "/" + t.getLast();
        List<SoundDefinition.Sound> resList = new ArrayList<>();
        for (int num = 1; num <= amount; num++) {
            resList.add(
                sound(ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, path + ((Integer) num).toString()))
            );
        }
        return resList.toArray(new SoundDefinition.Sound[0]);
    }

    @Override
    public void registerSounds() {
        add(RCSoundEvents.NETHER_SAND_BREAK.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SAND_BREAK, 4))
                .subtitle(RCSubtitles.Block.BREAK)
        );
        add(RCSoundEvents.NETHER_SAND_STEP.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SAND_STEP, 3))
                .subtitle(RCSubtitles.Block.STEP)
        );
        add(RCSoundEvents.NETHER_SAND_PLACE.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SAND_PLACE, 4))
                .subtitle(RCSubtitles.Block.PLACE)
        );
        add(RCSoundEvents.NETHER_SAND_HIT.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SAND_HIT, 3))
                .subtitle(RCSubtitles.Block.HIT)
        );
        add(RCSoundEvents.NETHER_SAND_FALL.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SAND_FALL, 3))
                
        );
        
        
        add(RCSoundEvents.NETHER_SOIL_BREAK.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SOIL_BREAK, 4))
                .subtitle(RCSubtitles.Block.BREAK)
        );
        add(RCSoundEvents.NETHER_SOIL_STEP.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SOIL_STEP, 3))
                .subtitle(RCSubtitles.Block.STEP)
        );
        add(RCSoundEvents.NETHER_SOIL_PLACE.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SOIL_PLACE, 4))
                .subtitle(RCSubtitles.Block.PLACE)
        );
        add(RCSoundEvents.NETHER_SOIL_HIT.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SOIL_HIT, 3))
                .subtitle(RCSubtitles.Block.HIT)
        );
        add(RCSoundEvents.NETHER_SOIL_FALL.get(),
            definition()
                .with(createSounds(RCRegistryNames.SoundEvents.NETHER_SOIL_FALL, 3))
                
        );
    }
}
