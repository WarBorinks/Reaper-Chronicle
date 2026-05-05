package warborinks.mods.reaperchronicle.world.reaper;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;
import warborinks.mods.reaperchronicle.world.reaper.reaper_attribute.ReaperAttribute;

@SuppressWarnings("null")
public class DeadReaper extends Reaper {
    public DeadReaper(String title, String text, ReaperAttribute... attributes) {
        super(title, text, attributes);
    }

    @Override
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
    public boolean isSpecialAttack(ItemStack stack) {
        return stack.getOrDefault(RCDataComponentTypes.TEXT_INDEX, 0) == this.getText().length() - 1;
    }

    @Override
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
        tooltipComponents.add(Component.literal(this.getTitleWithStyle() + ": " + this.getText()));
    }
}
