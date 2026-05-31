package warborinks.mods.reaperchronicle.data.language;

import java.util.function.Supplier;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.data.LanguageProvider;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.level.crystal.Crystal;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public abstract class ExtendedLanguageProvider extends LanguageProvider {
    public ExtendedLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @SuppressWarnings("null")
    protected void add(CreativeModeTab key, String name) {
        add(RCUtil.getCreativeModeTabDescriptionId(key), name);
    }
    protected void addCreativeModeTab(Supplier<? extends CreativeModeTab> key, String name) {
        add(key.get(), name);
    }

    @SuppressWarnings("null")
    protected void add(Crystal key, String name) {
        add(key.getDescriptionId(), name);
    }
    protected void addCrystal(Supplier<? extends Crystal> key, String name) {
        add(key.get(), name);
    }

    @SuppressWarnings("null")
    protected void add(Reaper key, String name, String title, String writer, String text) {
        add(key.getDescriptionId(), name);
        add(key.getTitleTranslationKey(), title);
        add(key.getWriterTranslationKey(), writer);
        add(key.getTextTranslationKey(), text);
    }
    protected void addReaper(Supplier<? extends Reaper> key, String name, String title, String writer, String text) {
        add(key.get(), name, title, writer, text);
    }

    @SuppressWarnings("null")
    protected void add(ReaperAttribute key, String name) {
        add(key.getDescriptionId(), name);
    }
    protected void addReaperAttribute(Supplier<? extends ReaperAttribute> key, String name) {
        add(key.get(), name);
    }
}
