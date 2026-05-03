package warborinks.mods.reaperchronicle.world.reaper;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.world.reaper.reaper_attribute.ReaperAttribute;

public abstract class Reaper {
    private final String text;
    private final Set<ReaperAttribute> attributes;

    @Nullable private String descriptionId;

    public Reaper(String text, ReaperAttribute... attributes) {
        this.text = text;
        this.attributes = Set.of(attributes);
    }

    public abstract void onReap(LivingEntity target, LivingEntity attacker, ItemStack stack);
    public abstract int getConsumption();

    public int addDamage(LivingEntity target, LivingEntity attacker, int damage, boolean specialAttack) {
        AtomicInteger newDamage = new AtomicInteger(damage);
        this.attributes.forEach(
            attribute -> {
                if (specialAttack) {
                    newDamage.set(attribute.apply(
                        "addDamageOnSpecialAttack",
                        Integer.class,
                        target, attacker, newDamage.get()
                    ));
                } else {
                    newDamage.set(attribute.apply(
                        "addDamageOnCommonAttack",
                        Integer.class,
                        target, attacker, newDamage.get()
                    ));
                }
            }
        );

        return newDamage.get();
    }

    public String getText() {
        return this.text;
    }

    public Set<ReaperAttribute> getAttributes() {
        return this.attributes;
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("reaper", RCRegistries.REAPER.getKey(this));
        }

        return this.descriptionId;
    }
}
