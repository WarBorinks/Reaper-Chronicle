package warborinks.mods.reaperchronicle.world.reaper;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;

public class DeadReaper extends Reaper {
    public DeadReaper(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("null")
    public void onReap(LivingEntity target, LivingEntity attacker, ItemStack stack) {
        if (!target.level().isClientSide()) {
            if (this.isSpecialAttack(stack)) {
                target.addEffect(new MobEffectInstance(
                    RCMobEffects.NETHER_POISON,
                    64, 1
                ));
            } else {
                target.addEffect(new MobEffectInstance(
                    RCMobEffects.NETHER_POISON,
                    32
                ));
            }
        }
    }

    @Override
    @SuppressWarnings("null")
    public boolean isSpecialAttack(ItemStack stack) {
        return stack.getOrDefault(RCDataComponentTypes.TEXT_INDEX, 0) == this.getAbsoluteText().length() - 1;
    }

    @Override
    @SuppressWarnings("null")
    public int getConsumption(LivingEntity target, ItemStack stack) {
        if (isSpecialAttack(stack)) {
            return 1;
        } else if (target.getType().is(EntityTypeTags.UNDEAD)) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
        List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        RCUtil.addComponentsToComponentListWithIngnoringEmpty(
            tooltipComponents,
            List.of(
                RCUtil.joinComponentsWithIgnoringEmpty(
                    List.of(getTitleComponent(), getWriterComponent()), " "
                ),
                getTextComponent()
            )
        );
    }
}
