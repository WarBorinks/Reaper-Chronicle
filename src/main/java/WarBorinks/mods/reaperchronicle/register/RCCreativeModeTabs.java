package WarBorinks.mods.reaperchronicle.register;

import WarBorinks.mods.reaperchronicle.ReaperChronicle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCCreativeModeTabs {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
        BuiltInRegistries.CREATIVE_MODE_TAB, 
        ReaperChronicle.MODID
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CRYSTAL_INGREDIENTS = CREATIVE_MODE_TAB.register(
        "crystal_ingredients",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("creative_mode_tab.reaperchronicle.crystal_ingredients"))
            .icon(() -> new ItemStack(RCItems.NETHER_DEBRIS.get()))
            .displayItems((parameters, output) -> {
                output.accept(RCItems.NETHER_DEBRIS.get());
                output.accept(RCItems.NETHER_SAND.get());
                output.accept(RCItems.NETHER_SOIL.get());
            })
        .build()
    );

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }
}
