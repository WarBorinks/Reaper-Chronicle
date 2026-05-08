package warborinks.mods.reaperchronicle.core.registries;

import javax.annotation.Nonnull;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

@SuppressWarnings("null")
public class RCRegistries {
    public static final Registry<Crystal> CRYSTAL = new RegistryBuilder<>(Keys.CRYSTAL)
        .sync(true)
        .create();
    public static final Registry<Reaper> REAPER = new RegistryBuilder<>(Keys.REAPER)
        .sync(true)
        .create();
    public static final Registry<ReaperAttribute> REAPER_ATTRIBUTE = new RegistryBuilder<>(Keys.REAPER_ATTRIBUTE)
        .sync(true)
        .create();

    private static void registerCustomRegistries(NewRegistryEvent event) {
        event.register(CRYSTAL);
        event.register(REAPER);
        event.register(REAPER_ATTRIBUTE);
    }

    static void register(IEventBus bus) {
        bus.addListener(RCRegistries::registerCustomRegistries);
    }

    public static class Keys {
        public static final ResourceKey<Registry<Crystal>> CRYSTAL = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, Names.CRYSTAL)
        );
        public static final ResourceKey<Registry<Reaper>> REAPER = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, Names.REAPER)
        );
        public static final ResourceKey<Registry<ReaperAttribute>> REAPER_ATTRIBUTE = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, Names.REAPER_ATTRIBUTE)
        );
    }

    public static class Names {
        @Nonnull public static String CRYSTAL = "crystal";
        @Nonnull public static String REAPER = "reaper";
        @Nonnull public static String REAPER_ATTRIBUTE = "reaper_attribute";
    }
}
