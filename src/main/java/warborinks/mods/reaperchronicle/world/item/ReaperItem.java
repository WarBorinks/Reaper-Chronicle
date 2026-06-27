package warborinks.mods.reaperchronicle.world.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;

public class ReaperItem extends Item {
    private static final ResourceLocation ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, "reaper/attack_damage"
    );
    private static final ResourceLocation ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, "reaper/attack_speed"
    );

    private static final Map<Reaper, Item> BY_REAPER = new HashMap<>();

    private final Supplier<Reaper> reaper;

    public ReaperItem(Supplier<Reaper> reaper, @Nonnull Properties properties) {
        super(properties);
        this.reaper = reaper;
    }

    public static Item byReaper(Reaper reaper) {
        return BY_REAPER.getOrDefault(reaper, Items.AIR);
    }

    @Override
    @SuppressWarnings("null")
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        Reaper reaper = this.reaper.get();
        reaper.onReap(target, attacker, stack);
        stack.hurtAndBreak(reaper.getConsumption(target, stack), attacker, EquipmentSlot.MAINHAND);

        int text_index = stack.getOrDefault(RCDataComponentTypes.TEXT_INDEX, 0);
        text_index = (text_index + 1) % reaper.getAbsoluteText().length();
        stack.set(RCDataComponentTypes.TEXT_INDEX, text_index);

        return true;
    }

    
    @Override
    @SuppressWarnings("null")
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context,
        @Nonnull List<Component> tooltipComponents, @Nonnull TooltipFlag tooltipFlag) {
        this.reaper.get().appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        this.reaper.get().getAttributes().forEach(
            attribute -> RCUtil.addComponentToComponentListWithIngnoringEmpty(
                tooltipComponents,
                Component.translatable(attribute.getDescriptionId())
                    .withColor(attribute.getColor())
            )
        );
    }

    public Reaper getReaper() {
        return this.reaper.get();
    }

    public void registerReapers(Map<Reaper, Item> reaperToItemMap, Item item) {
        reaperToItemMap.put(this.getReaper(), item);
    }
    
    @Override
    public String getDescriptionId() {
        return this.reaper.get().getDescriptionId();
    }

    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    private static class Events {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        private static void onLivingDamagePre(LivingDamageEvent.Pre event) {
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

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        @SuppressWarnings("null")
        private static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
            ItemStack stack = event.getItemStack();
            if (!(stack.getItem() instanceof ReaperItem reaperItem)) {
                return;
            }

            Reaper reaper = reaperItem.getReaper();
            event.addModifier(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                    ATTACK_DAMAGE,
                    reaper.getDamage(),
                    AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            );
            event.addModifier(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                    ATTACK_SPEED,
                    reaper.getSpeed(),
                    AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.MAINHAND
            );
        }
        
        @SubscribeEvent
        private static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof ReaperItem reaperItem) {
                    BY_REAPER.put(reaperItem.getReaper(), item);
                }
            }
        }
    }
}
