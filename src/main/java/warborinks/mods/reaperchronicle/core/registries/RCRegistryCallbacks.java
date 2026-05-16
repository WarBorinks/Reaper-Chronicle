package warborinks.mods.reaperchronicle.core.registries;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;
import net.neoforged.neoforge.registries.callback.ClearCallback;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

public class RCRegistryCallbacks {
    public static class ItemCallbacks implements AddCallback<Item>, ClearCallback<Item> {
        @Nonnull public static final ItemCallbacks INSTANCE = new ItemCallbacks();
        @Nonnull public static final Map<Crystal, Item> CRYSTAL_TO_ITEM_MAP = new HashMap<>();
        @Nonnull public static final Map<Reaper, Item> REAPER_TO_ITEM_MAP = new HashMap<>();

        @Override
        public void onAdd(@Nonnull Registry<Item> registry, int id, @Nonnull ResourceKey<Item> key, @Nonnull Item item) {
            if (item instanceof CrystalItem crystalItem) {
                crystalItem.registerCrystals(CRYSTAL_TO_ITEM_MAP, item);
            } if (item instanceof ReaperItem reaperItem) {
                reaperItem.registerReapers(REAPER_TO_ITEM_MAP, item);
            }
        }
        
        @Override
        public void onClear(@Nonnull Registry<Item> registry, boolean full) {
            if (full) {
                CRYSTAL_TO_ITEM_MAP.clear();
                REAPER_TO_ITEM_MAP.clear();
            }
        }
    }

    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onModifyRegistries(ModifyRegistriesEvent event) {
            BuiltInRegistries.ITEM.addCallback(ItemCallbacks.INSTANCE);
        }
    }
}
