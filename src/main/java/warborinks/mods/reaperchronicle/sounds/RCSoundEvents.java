package warborinks.mods.reaperchronicle.sounds;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public final class RCSoundEvents {
    private static final DeferredRegister<SoundEvent> REGISTRAR = RCDeferredRegisters.SOUND_EVENT;

    @Nonnull private static Supplier<SoundEvent> getSoundEventSupplier(@Nonnull String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, name
        );
        return () -> SoundEvent.createVariableRangeEvent(id);
    }

    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_BREAK = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_BREAK,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_BREAK)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_STEP = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_STEP,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_STEP)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_PLACE = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_PLACE,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_PLACE)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_HIT = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_HIT,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_HIT)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_FALL = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SAND_FALL,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SAND_FALL)
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_BREAK = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_BREAK,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_BREAK)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_STEP = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_STEP,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_STEP)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_PLACE = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_PLACE,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_PLACE)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_HIT = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_HIT,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_HIT)
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_FALL = REGISTRAR.register(
        RCRegistryNames.SoundEvents.NETHER_SOIL_FALL,
        getSoundEventSupplier(RCRegistryNames.SoundEvents.NETHER_SOIL_FALL)
    );

    public static void load() {}
}
