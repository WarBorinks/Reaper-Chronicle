package warborinks.mods.reaperchronicle.world.level.block;

import net.neoforged.neoforge.common.util.DeferredSoundType;
import warborinks.mods.reaperchronicle.sounds.RCSoundEvents;

@SuppressWarnings("null")
public final class RCSoundTypes {
    public static final DeferredSoundType NETHER_SAND = new DeferredSoundType(
        1.0f, 1.0f,
        RCSoundEvents.NETHER_SAND_BREAK, RCSoundEvents.NETHER_SAND_STEP,
        RCSoundEvents.NETHER_SAND_PLACE, RCSoundEvents.NETHER_SAND_HIT, RCSoundEvents.NETHER_SAND_FALL
    );
    
    public static final DeferredSoundType NETHER_SOIL = new DeferredSoundType(
        1.0f, 1.0f,
        RCSoundEvents.NETHER_SOIL_BREAK, RCSoundEvents.NETHER_SOIL_STEP,
        RCSoundEvents.NETHER_SOIL_PLACE, RCSoundEvents.NETHER_SOIL_HIT, RCSoundEvents.NETHER_SOIL_FALL
    );
}
