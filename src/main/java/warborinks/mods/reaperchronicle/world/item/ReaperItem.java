package warborinks.mods.reaperchronicle.world.item;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.world.item.component.RCItemAttributeNames;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;

@SuppressWarnings("null")
public class ReaperItem extends Item {
    private final Supplier<Reaper> reaper;

    private static final ResourceLocation ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, RCItemAttributeNames.ReaperItem.ATTACK_DAMAGE
    );
    private static final ResourceLocation ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, RCItemAttributeNames.ReaperItem.ATTACK_SPEED
    );

    public ReaperItem(Supplier<Reaper> reaper, Properties properties) {
        super(properties);
        this.reaper = reaper;
    }

    public static ItemAttributeModifiers createAttributes(double damage, double speed) {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE, damage, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            ).add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(ATTACK_SPEED, speed, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            ).build();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Reaper reaper = this.reaper.get();
        reaper.onReap(target, attacker, stack);
        stack.hurtAndBreak(reaper.getConsumption(target, stack), attacker, EquipmentSlot.MAINHAND);

        int text_index = stack.getOrDefault(RCDataComponentTypes.TEXT_INDEX, 0);
        text_index = (text_index + 1) % reaper.getText().length();
        stack.set(RCDataComponentTypes.TEXT_INDEX, text_index);

        return true;
    }

    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
        List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        this.reaper.get().appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        this.reaper.get().getAttributes().forEach(
            attribute -> tooltipComponents.add(
                Component.translatable(attribute.getDescriptionId())
                    .withColor(attribute.getColor())
            )
        );
    }

    public Reaper getReaper() {
        return this.reaper.get();
    }
    
    @Override
    public String getDescriptionId() {
        return this.reaper.get().getDescriptionId();
    }

    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
            if (event.getEntity().level().isClientSide()) {
                return;
            }

            if (!(event.getSource().getEntity() instanceof Player player)) {
                return;
            }

            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof ReaperItem reaperItem)) {
                return;
            }

            Reaper reaper = reaperItem.getReaper();
            LivingEntity target = event.getEntity();
            float newDamage = reaper.getModifiedDamage(
                target, player,
                event.getOriginalDamage(),
                reaper.isSpecialAttack(stack)
            );

            event.setNewDamage(newDamage);
        }
    }
}
