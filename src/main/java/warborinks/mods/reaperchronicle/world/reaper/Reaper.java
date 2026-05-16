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
import net.minecraft.world.level.ItemLike;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public abstract class Reaper implements ItemLike {
    private final String absoluteText;
    private final double damage;
    private final double speed;
    private final Set<Supplier<ReaperAttribute>> attributes;

    @Nullable private Item item;

    @Nullable private Component titleComponent;
    @Nullable private Component writerComponent;
    @Nullable private Component textComponent;
    
    @Nullable private String titleTranslationKey;
    @Nullable private String writerTranslationKey;
    @Nullable private String textTranslationKey;
    @Nullable private String descriptionId;

    public Reaper(String absoluteText, double damage, double speed) {
        this.absoluteText = absoluteText;
        this.damage = damage;
        this.speed = speed;
        this.attributes = Set.of();
    }

    @SafeVarargs
    public Reaper(String absoluteText, double damage, double speed, Supplier<ReaperAttribute>... attributes) {
        this.absoluteText = absoluteText;
        this.damage = damage;
        this.speed = speed;
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

    @Override
    public Item asItem() {
        if (this.item == null) {
            this.item = ReaperItem.byReaper(this);
        }

        return this.item;
    }

    public String getTitleTranslationKey() {
        if (this.titleTranslationKey == null) {
            this.titleTranslationKey = this.getDescriptionId() + ".title";
        }

        return this.titleTranslationKey;
    }
    public Component getTitleComponent() {
        if (this.titleComponent == null) {
            this.titleComponent = Component.translatable(this.getDescriptionId() + ".title");
        }

        return this.titleComponent;
    }

    public String getWriterTranslationKey() {
        if (this.writerTranslationKey == null) {
            this.writerTranslationKey = this.getDescriptionId() + ".writer";
        }

        return this.writerTranslationKey;
    }
    public Component getWriterComponent() {
        if (this.writerComponent == null) {
            this.writerComponent = Component.translatable(this.getDescriptionId() + ".writer");
        }

        return this.writerComponent;
    }

    public String getTextTranslationKey() {
        if (this.textTranslationKey == null) {
            this.textTranslationKey = this.getDescriptionId() + ".text";
        }

        return this.textTranslationKey;
    }
    public Component getTextComponent() {
        if (this.textComponent == null) {
            this.textComponent = Component.translatable(this.getDescriptionId() + ".text");
        }

        return this.textComponent;
    }

    public String getAbsoluteText() {
        return this.absoluteText;
    }

    public double getDamage() {
        return this.damage;
    }
    
    public double getSpeed() {
        return this.speed;
    }

    public Set<ReaperAttribute> getAttributes() {
        return this.attributes.stream()
            .map(Supplier<ReaperAttribute>::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistryNames.Registries.REAPER, RCRegistries.REAPER.getKey(this));
        }

        return this.descriptionId;
    }
}
