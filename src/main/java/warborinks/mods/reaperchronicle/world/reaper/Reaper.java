package warborinks.mods.reaperchronicle.world.reaper;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public abstract class Reaper {
    private final String title;
    private final String text;
    private final Set<Supplier<ReaperAttribute>> attributes;

    @Nullable private String descriptionId;

    @SafeVarargs
    public Reaper(String title, String text, Supplier<ReaperAttribute>... attributes) {
        this.title = title;
        this.text = text;
        this.attributes = Set.of(attributes);
    }

    public abstract void onReap(LivingEntity target, LivingEntity attacker, ItemStack stack);
    public abstract boolean isSpecialAttack(ItemStack stack);
    public abstract int getConsumption(LivingEntity target, ItemStack stack);
    public abstract void appendHoverText(ItemStack stack, Item.TooltipContext context,
        List<Component> tooltipComponents, TooltipFlag tooltipFlag);

    public float getModifiedDamage(LivingEntity target, LivingEntity attacker, float damage, boolean specialAttack) {
        float newDamage = damage;
        for (Supplier<ReaperAttribute> attribute : attributes) {
            if (specialAttack) {
                newDamage = attribute.get().apply(
                    "addDamageOnSpecialAttack",
                    Float.class,
                    target, attacker, newDamage
                );
            } else {
                newDamage = attribute.get().apply(
                    "addDamageOnCommonAttack",
                    Float.class,
                    target, attacker, newDamage
                );
            }
        }
        return newDamage;
    }

    public String getTitle() {
        return this.title;
    }
    public String getTitleWithStyle() {
        return "《" + this.title + "》";
    }

    public String getText() {
        return this.text;
    }

    public Set<ReaperAttribute> getAttributes() {
        return this.attributes.stream()
            .map(Supplier<ReaperAttribute>::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistries.Names.REAPER, RCRegistries.REAPER.getKey(this));
        }

        return this.descriptionId;
    }
}
