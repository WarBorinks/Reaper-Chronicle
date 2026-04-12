package warborinks.mods.reaperchronicle.register;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.ReaperChronicle;

@SuppressWarnings("null")
public class RCDeferredRegisters {
    static final DeferredRegister<Block> BLOCK = DeferredRegister.create(
        BuiltInRegistries.BLOCK, 
        ReaperChronicle.MODID
    );

    static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB, 
        ReaperChronicle.MODID
    );

    static final DeferredRegister<Item> ITEM = DeferredRegister.create(
        BuiltInRegistries.ITEM, 
        ReaperChronicle.MODID
    );
    
    static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(
        BuiltInRegistries.MOB_EFFECT, 
        ReaperChronicle.MODID
    );
    
    static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT, 
        ReaperChronicle.MODID
    );

    public static final RCBlockItems BLOCK_ITEMS = new RCBlockItems(ITEM);
    public static final RCBlocks BLOCKS = new RCBlocks(BLOCK);
    public static final RCCreativeModeTabs CREATIVE_MODE_TABS = new RCCreativeModeTabs(CREATIVE_MODE_TAB);
    public static final RCItems ITEMS = new RCItems(ITEM);
    public static final RCMobEffects MOB_EFFECTS = new RCMobEffects(MOB_EFFECT);
    public static final RCSoundEvents SOUND_EVENTS = new RCSoundEvents(SOUND_EVENT);

    public static void register(IEventBus bus) {
        BLOCK.register(bus);
        CREATIVE_MODE_TAB.register(bus);
        ITEM.register(bus);
        MOB_EFFECT.register(bus);
        SOUND_EVENT.register(bus);
    }
}
