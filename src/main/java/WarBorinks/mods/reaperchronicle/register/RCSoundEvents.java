package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCSoundEvents {
    public static DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT, 
        ReaperChronicle.MODID
    );

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, name
        );
        return SOUND_EVENT.register(
            name, 
            () -> SoundEvent.createVariableRangeEvent(id)
        );
    }

    public class SoundTypes {
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_BREAK = registerSoundEvent(
            "block.nether_sand.break"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_STEP = registerSoundEvent(
            "block.nether_sand.step"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_PLACE = registerSoundEvent(
            "block.nether_sand.place"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_HIT = registerSoundEvent(
            "block.nether_sand.hit"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_FALL = registerSoundEvent(
            "block.nether_sand.fall"
        );
        public static final DeferredSoundType NETHER_SAND = new DeferredSoundType(
            1.0f, 1.0f,
            NETHER_SAND_BREAK, NETHER_SAND_STEP, NETHER_SAND_PLACE, 
            NETHER_SAND_HIT, NETHER_SAND_FALL
        );

        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_BREAK = registerSoundEvent(
            "block.nether_soil.break"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_STEP = registerSoundEvent(
            "block.nether_soil.step"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_PLACE = registerSoundEvent(
            "block.nether_soil.place"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_HIT = registerSoundEvent(
            "block.nether_soil.hit"
        );
        public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_FALL = registerSoundEvent(
            "block.nether_soil.fall"
        );
        public static final DeferredSoundType NETHER_SOIL = new DeferredSoundType(
            1.0f, 1.0f,
            NETHER_SOIL_BREAK, NETHER_SOIL_STEP, NETHER_SOIL_PLACE, 
            NETHER_SOIL_HIT, NETHER_SOIL_FALL
        );
    }

    public static void register(IEventBus bus) {
        SOUND_EVENT.register(bus);
    }
}
