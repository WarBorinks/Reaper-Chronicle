package warborinks.mods.reaperchronicle.core.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.sounds.RCSoundEvents;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;
import warborinks.mods.reaperchronicle.world.item.RCCreativeModeTabs;
import warborinks.mods.reaperchronicle.world.item.RCItems;
import warborinks.mods.reaperchronicle.world.item.crafting.RCRecipeSerializers;
import warborinks.mods.reaperchronicle.world.item.crafting.RCRecipeTypes;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributes;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystals;

@SuppressWarnings("null")
public class RCDeferredRegisters {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(
        BuiltInRegistries.BLOCK,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<Crystal> CRYSTAL = DeferredRegister.create(
        RCRegistries.Keys.CRYSTAL,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(
        BuiltInRegistries.ITEM,
        ReaperChronicle.MODID
    );
    
    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(
        BuiltInRegistries.MOB_EFFECT,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<ReaperAttribute> REAPER_ATTRIBUTE = DeferredRegister.create(
        RCRegistries.Keys.REAPER_ATTRIBUTE,
        ReaperChronicle.MODID
    );

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
        BuiltInRegistries.RECIPE_SERIALIZER,
        ReaperChronicle.MODID
    );
    
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(
        BuiltInRegistries.RECIPE_TYPE,
        ReaperChronicle.MODID
    );
    
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(
        BuiltInRegistries.SOUND_EVENT,
        ReaperChronicle.MODID
    );

    public static void register(IEventBus bus) {
        ReaperAttributes.load();
        Crystals.load();

        RCBlocks.load();
        RCCreativeModeTabs.load();
        RCDataComponentTypes.load();
        RCItems.load();
        RCMobEffects.load();
        RCRecipeSerializers.load();
        RCRecipeTypes.load();
        RCSoundEvents.load();

        BLOCK.register(bus);
        CREATIVE_MODE_TAB.register(bus);
        CRYSTAL.register(bus);
        DATA_COMPONENT_TYPE.register(bus);
        ITEM.register(bus);
        MOB_EFFECT.register(bus);
        REAPER_ATTRIBUTE.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPE.register(bus);
        SOUND_EVENT.register(bus);

        RCRegistries.register(bus);
    }
}
