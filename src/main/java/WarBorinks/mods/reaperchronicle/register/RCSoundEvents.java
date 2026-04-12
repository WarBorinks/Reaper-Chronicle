package warborinks.mods.reaperchronicle.register;

import warborinks.mods.reaperchronicle.ReaperChronicle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCSoundEvents {
    private DeferredRegister<SoundEvent> SOUND_EVENT;

    private DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, name
        );
        return SOUND_EVENT.register(
            name, 
            () -> SoundEvent.createVariableRangeEvent(id)
        );
    }

    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_BREAK;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_STEP;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_PLACE;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_HIT;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SAND_FALL;

    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_BREAK;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_STEP;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_PLACE;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_HIT;
    public final DeferredHolder<SoundEvent, SoundEvent> NETHER_SOIL_FALL;

    public final SoundTypes SOUND_TYPES;

    RCSoundEvents(DeferredRegister<SoundEvent> soundEvent) {
        this.SOUND_EVENT = soundEvent;

        this.NETHER_SAND_BREAK = registerSoundEvent(
            "block.nether_sand.break"
        );
        this.NETHER_SAND_FALL = registerSoundEvent(
            "block.nether_sand.fall"
        );
        this.NETHER_SAND_HIT = registerSoundEvent(
            "block.nether_sand.hit"
        );
        this.NETHER_SAND_PLACE = registerSoundEvent(
            "block.nether_sand.place"
        );
        this.NETHER_SAND_STEP = registerSoundEvent(
            "block.nether_sand.step"
        );
        
        this.NETHER_SOIL_BREAK = registerSoundEvent(
            "block.nether_soil.break"
        );
        this.NETHER_SOIL_FALL = registerSoundEvent(
            "block.nether_soil.fall"
        );
        this.NETHER_SOIL_HIT = registerSoundEvent(
            "block.nether_soil.hit"
        );
        this.NETHER_SOIL_PLACE = registerSoundEvent(
            "block.nether_soil.place"
        );
        this.NETHER_SOIL_STEP = registerSoundEvent(
            "block.nether_soil.step"
        );

        this.SOUND_TYPES = new SoundTypes();
    }

    public class SoundTypes {
        public final DeferredSoundType NETHER_SAND = new DeferredSoundType(
            1.0f, 1.0f,
            NETHER_SAND_BREAK, NETHER_SAND_STEP, NETHER_SAND_PLACE, 
            NETHER_SAND_HIT, NETHER_SAND_FALL
        );
        
        public final DeferredSoundType NETHER_SOIL = new DeferredSoundType(
            1.0f, 1.0f,
            NETHER_SOIL_BREAK, NETHER_SOIL_STEP, NETHER_SOIL_PLACE, 
            NETHER_SOIL_HIT, NETHER_SOIL_FALL
        );
    }
}
