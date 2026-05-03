package warborinks.mods.reaperchronicle.world.item;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.component.RCDataComponentTypes;
import warborinks.mods.reaperchronicle.world.item.component.ItemAttributePaths;
import warborinks.mods.reaperchronicle.world.reaper.Reaper;

@SuppressWarnings("null")
public class ReaperItem extends Item {
    private final Supplier<Reaper> reaper;

    private static final ResourceLocation ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, ItemAttributePaths.ReaperItem.ATTACK_DAMAGE
    );
    private static final ResourceLocation ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(
        ReaperChronicle.MODID, ItemAttributePaths.ReaperItem.ATTACK_SPEED
    );

    public ReaperItem(Supplier<Reaper> reaper, Properties properties) {
        super(properties);
        this.reaper = reaper;
    }

    public static ItemAttributeModifiers createAttributes(int damage, double speed) {
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
        this.reaper.get().onReap(target, attacker, stack);

        int text_index = stack.getOrDefault(RCDataComponentTypes.TEXT_INDEX, 0);
        text_index = (text_index + 1) % this.reaper.get().getText().length();
        stack.set(RCDataComponentTypes.TEXT_INDEX, text_index);

        stack.hurtAndBreak(this.reaper.get().getConsumption(), attacker, EquipmentSlot.MAINHAND);

        return true;
    }
}
