package warborinks.mods.reaperchronicle.register;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("null")
public class RCCreativeModeTabs {
    private final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB;

    public final DeferredHolder<CreativeModeTab, CreativeModeTab> CRYSTAL_INGREDIENTS;

    RCCreativeModeTabs(DeferredRegister<CreativeModeTab> creativeModeTab) {
        this.CREATIVE_MODE_TAB = creativeModeTab;

        this.CRYSTAL_INGREDIENTS = CREATIVE_MODE_TAB.register(
            "crystal_ingredients",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("creative_mode_tab.reaperchronicle.crystal_ingredients"))
                .icon(() -> new ItemStack(RCDeferredRegisters.ITEMS.NETHER_DEBRIS.get()))
                .displayItems((parameters, output) -> {
                    output.accept(RCDeferredRegisters.ITEMS.NETHER_DEBRIS.get());
                    output.accept(RCDeferredRegisters.BLOCK_ITEMS.NETHER_SAND.get());
                    output.accept(RCDeferredRegisters.BLOCK_ITEMS.NETHER_SOIL.get());
                })
            .build()
        );
    }
}
