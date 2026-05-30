package warborinks.mods.reaperchronicle.world.reaper;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

public final class Reapers {
    private static final DeferredRegister<Reaper> REGISTRAR = RCDeferredRegisters.REAPER;

    public static final DeferredHolder<Reaper, Reaper> COMMON_REAPER = REGISTRAR.register(
        RCRegistryNames.Reapers.COMMON_REAPER,
        () -> new Reaper("", 2.0, 0.0) {
            public void onReap(LivingEntity target, LivingEntity attacker, ItemStack stack) {}
            public boolean isSpecialAttack(ItemStack stack) {
                return false;
            }
            public int getConsumption(LivingEntity target, ItemStack stack) {
                return 2;
            }
            public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {}
        }
    );

    public static void load() {}
}
