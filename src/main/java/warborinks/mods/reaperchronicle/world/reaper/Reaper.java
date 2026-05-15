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
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public abstract class Reaper {
    private final String absoluteText;
    private final Set<Supplier<ReaperAttribute>> attributes;

    @Nullable private Component title;
    @Nullable private Component writer;
    @Nullable private Component text;
    
    @Nullable private String titleTranslationKey;
    @Nullable private String writerTranslationKey;
    @Nullable private String textTranslationKey;
    @Nullable private String descriptionId;

    public Reaper(String absoluteText) {
        this.absoluteText = absoluteText;
        this.attributes = Set.of();
    }

    @SafeVarargs
    public Reaper(String absoluteText, Supplier<ReaperAttribute>... attributes) {
        this.absoluteText = absoluteText;
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

    public String getTitleTranslationKey() {
        if (this.titleTranslationKey == null) {
            this.titleTranslationKey = this.getDescriptionId() + ".title";
        }

        return this.titleTranslationKey;
    }
    public Component getTitle() {
        if (this.title == null) {
            this.title = Component.translatable(this.getDescriptionId() + ".title");
        }

        return this.title;
    }

    public String getWriterTranslationKey() {
        if (this.writerTranslationKey == null) {
            this.writerTranslationKey = this.getDescriptionId() + ".writer";
        }

        return this.writerTranslationKey;
    }
    public Component getWriter() {
        if (this.writer == null) {
            this.writer = Component.translatable(this.getDescriptionId() + ".writer");
        }

        return this.writer;
    }

    public String getTextTranslationKey() {
        if (this.textTranslationKey == null) {
            this.textTranslationKey = this.getDescriptionId() + ".text";
        }

        return this.textTranslationKey;
    }
    public Component getText() {
        if (this.text == null) {
            this.text = Component.translatable(this.getDescriptionId() + ".text");
        }

        return this.text;
    }

    public String getAbsoluteText() {
        return this.absoluteText;
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
