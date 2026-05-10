package warborinks.mods.reaperchronicle.data.language;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.data.LanguageProvider;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

@SuppressWarnings("null")
public abstract class ExtendedLanguageProvider extends LanguageProvider {
    public ExtendedLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    protected void add(CreativeModeTab key, String name) {
        add(RCRegistryNames.CreativeModeTabs.getCreativeModeTabDescriptionId(key), name);
    }

    protected void add(Crystal key, String name) {
        add(key.getDescriptionId(), name);
    }

    protected void add(Reaper key, String name, String title, String writer, String text) {
        add(key.getDescriptionId(), name);
        add(key.getTitleTranslationKey(), title);
        add(key.getWriterTranslationKey(), writer);
        add(key.getTextTranslationKey(), text);
    }

    protected void add(ReaperAttribute key, String name) {
        add(key.getDescriptionId(), name);
    }
}
