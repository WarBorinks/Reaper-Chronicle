package warborinks.mods.reaperchronicle.data.language;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.data.LanguageProvider;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

public abstract class ExtendedLanguageProvider extends LanguageProvider {
    public ExtendedLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @SuppressWarnings("null")
    protected void add(@Nonnull Component key, @Nonnull String name) {
        if (key.getContents() instanceof TranslatableContents contents) {
            add(contents.getKey(), name);
        }
    }
    @SuppressWarnings("null")
    protected void addComponent(@Nonnull Supplier<? extends Component> key, @Nonnull String name) {
        this.add(key.get(), name);
    }

    @SuppressWarnings("null")
    protected void add(@Nonnull CreativeModeTab key, @Nonnull String name) {
        add(RCUtil.makeCreativeModeTabDescriptionId(key), name);
    }
    @SuppressWarnings("null")
    protected void addCreativeModeTab(@Nonnull Supplier<? extends CreativeModeTab> key, @Nonnull String name) {
        this.add(key.get(), name);
    }

    @SuppressWarnings("null")
    protected void add(@Nonnull Crystal key, @Nonnull String name) {
        add(key.getDescriptionId(), name);
    }
    @SuppressWarnings("null")
    protected void addCrystal(@Nonnull Supplier<? extends Crystal> key, @Nonnull String name) {
        this.add(key.get(), name);
    }

    @SuppressWarnings("null")
    protected void add(@Nonnull Reaper key, @Nonnull String name,
        @Nonnull String title, @Nonnull String writer, @Nonnull String text) {
        add(key.getDescriptionId(), name);
        add(key.getTitleTranslationKey(), title);
        add(key.getWriterTranslationKey(), writer);
        add(key.getTextTranslationKey(), text);
    }
    @SuppressWarnings("null")
    protected void addReaper(@Nonnull Supplier<? extends Reaper> key, @Nonnull String name,
        @Nonnull String title, @Nonnull String writer, @Nonnull String text) {
        this.add(key.get(), name, title, writer, text);
    }

    @SuppressWarnings("null")
    protected void add(@Nonnull ReaperAttribute key, @Nonnull String name) {
        add(key.getDescriptionId(), name);
    }
    @SuppressWarnings("null")
    protected void addReaperAttribute(@Nonnull Supplier<? extends ReaperAttribute> key, @Nonnull String name) {
        this.add(key.get(), name);
    }
}
