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

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NETHER_BLOCKS = CREATIVE_MODE_TAB.register(
        "nether_blocks", 
        () -> CreativeModeTab.builder()
            .title(Component.translatable("creative_mode_tab.reaperchronicle.nether_blocks"))
            .icon(() -> new ItemStack(RCItems.BlockItems.NETHER_SOIL.get()))
            .displayItems((parameters, output) -> {
                output.accept(RCItems.BlockItems.NETHER_SOIL.get());
                output.accept(RCItems.BlockItems.NETHER_SAND.get());
            })
        .build()
    );

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }
}
