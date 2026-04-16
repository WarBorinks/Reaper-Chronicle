package warborinks.mods.reaperchronicle.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.crystal.Crystal;
import warborinks.mods.reaperchronicle.attribute.Attribute;

@SuppressWarnings("null")
public class RCRegistries {
    public static final ResourceKey<Registry<Attribute>> ATTRIBUTE = ResourceKey.createRegistryKey(
        ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, "attribute")
    );
    public static final Registry<Attribute> ATTRIBUTE_REGISTRY = new RegistryBuilder<>(ATTRIBUTE)
        .create(); 
        
    public static final ResourceKey<Registry<Crystal>> CRYSTAL = ResourceKey.createRegistryKey(
        ResourceLocation.fromNamespaceAndPath(ReaperChronicle.MODID, "crystal")
    );
    public static final Registry<Crystal> CRYSTAL_REGISTRY = new RegistryBuilder<>(CRYSTAL)
        .create();

    private static void registerCustomRegistries(NewRegistryEvent event) {
        event.register(ATTRIBUTE_REGISTRY);
        event.register(CRYSTAL_REGISTRY);
    }

    static void register(IEventBus bus) {
        bus.addListener(RCRegistries::registerCustomRegistries);
    }
}
