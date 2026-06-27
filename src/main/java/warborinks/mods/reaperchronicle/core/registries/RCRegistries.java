package warborinks.mods.reaperchronicle.core.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

@SuppressWarnings("null")
public final class RCRegistries {
    public static final Registry<Crystal> CRYSTAL = new RegistryBuilder<>(Keys.CRYSTAL)
        .sync(true)
        .defaultKey(ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.Crystals.EMPTY_CRYSTAL
        )).create();
    public static final Registry<Reaper> REAPER = new RegistryBuilder<>(Keys.REAPER)
        .sync(true)
        .defaultKey(ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.Reapers.COMMON_REAPER
        )).create();
    public static final Registry<ReaperAttribute> REAPER_ATTRIBUTE = new RegistryBuilder<>(Keys.REAPER_ATTRIBUTE)
        .sync(true)
        .defaultKey(ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCRegistryNames.ReaperAttributes.EMPTY_ATTRIBUTE
        )).create();

    public static class Keys {
        public static final ResourceKey<Registry<Crystal>> CRYSTAL = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Registries.CRYSTAL)
        );
        public static final ResourceKey<Registry<Reaper>> REAPER = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Registries.REAPER)
        );
        public static final ResourceKey<Registry<ReaperAttribute>> REAPER_ATTRIBUTE = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, RCRegistryNames.Registries.REAPER_ATTRIBUTE)
        );
    }
    
    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    private static class Events {
        @SubscribeEvent
        private static void onNewRegistry(NewRegistryEvent event) {
            event.register(CRYSTAL);
            event.register(REAPER);
            event.register(REAPER_ATTRIBUTE);
        }
    }
}
