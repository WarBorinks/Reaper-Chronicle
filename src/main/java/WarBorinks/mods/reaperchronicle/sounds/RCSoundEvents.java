package warborinks.mods.reaperchronicle.sounds;

import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCSoundEvents {
    private static final DeferredRegister<SoundEvent> REGISTER = RCDeferredRegisters.SOUND_EVENT;

    private static Supplier<SoundEvent> getSoundEventSupplier(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, name
        );
        return () -> SoundEvent.createVariableRangeEvent(id);
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_BREAK = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_BREAK,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_BREAK)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_STEP = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_STEP,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_STEP)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_PLACE = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_PLACE,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_PLACE)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_HIT = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_HIT,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_HIT)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_FALL = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_FALL,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_FALL)
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_BREAK = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_BREAK,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_BREAK)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_STEP = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_STEP,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_STEP)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_PLACE = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_PLACE,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_PLACE)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_HIT = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_HIT,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_HIT)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_FALL = REGISTER.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_FALL,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_FALL)
    );

    public static void load() {}
}
