package warborinks.mods.reaperchronicle.world.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public class RCCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTRAR = RCDeferredRegisters.CREATIVE_MODE_TAB;

    private static String getTranslatableString(String name) {
        return RCUtil.getCreativeModeTabDescriptionId(
            ReaperChronicle.MODID, name
        );
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CRYSTAL_INGREDIENTS = REGISTRAR.register(
        RCRegistryNames.CreativeModeTabs.CRYSTAL_INGREDIENTS,
        () -> CreativeModeTab.builder()
            .title(Component.translatable(getTranslatableString(RCRegistryNames.CreativeModeTabs.CRYSTAL_INGREDIENTS)))
            .icon(() -> new ItemStack(RCItems.NETHER_DEBRIS.get()))
            .displayItems((parameters, output) -> {
                output.accept(RCItems.NETHER_DEBRIS.get());
                output.accept(RCBlockItems.NETHER_SAND.get());
                output.accept(RCBlockItems.NETHER_SOIL.get());
            })
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CRYSTALS = REGISTRAR.register(
        RCRegistryNames.CreativeModeTabs.CRYSTALS,
        () -> CreativeModeTab.builder()
            .title(Component.translatable(getTranslatableString(RCRegistryNames.CreativeModeTabs.CRYSTALS)))
            .icon(() -> new ItemStack(CrystalItems.EMPTY_CRYSTAL.get()))
            .displayItems((parameters, output) -> {
                output.accept(CrystalItems.EMPTY_CRYSTAL.get());
                output.accept(CrystalItems.WATER_CRYSTAL.get());
            })
            .build()
    );
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REAPERS = REGISTRAR.register(
        RCRegistryNames.CreativeModeTabs.REAPERS,
        () -> CreativeModeTab.builder()
            .title(Component.translatable(getTranslatableString(RCRegistryNames.CreativeModeTabs.REAPERS)))
            .icon(() -> new ItemStack(RCItems.NETHER_DEBRIS.get()))
            .displayItems((parameters, output) -> {
                output.accept(ReaperItems.COMMON_REAPER.get());
            })
            .build()
    );

    public static void load() {}
}
