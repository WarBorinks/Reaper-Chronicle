package warborinks.mods.reaperchronicle.world.reaper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.item.ReaperItem;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.SpecialFeatures;

public abstract class Reaper implements ItemLike {
    private final String absoluteText;
    private final double damage;
    private final double speed;
    private final Set<Supplier<ReaperAttribute>> attributes;
    private final Map<ResourceKey<Enchantment>, Integer> enchantments;

    @Nullable private Item item;

    @Nullable private Component titleComponent;
    @Nullable private Component writerComponent;
    @Nullable private Component textComponent;
    
    @Nullable private String titleTranslationKey;
    @Nullable private String writerTranslationKey;
    @Nullable private String textTranslationKey;
    @Nullable private String descriptionId;

    public Reaper(Properties properties) {
        this.absoluteText = properties.absoluteText;
        this.damage = properties.damage;
        this.speed = properties.speed;
        this.attributes = properties.attributes;
        this.enchantments = properties.enchantments;
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
                newDamage = attribute.get().invokeOrDefault(
                    SpecialFeatures.SPECIAL_ATTACK,
                    newDamage, Float.class,
                    target, attacker, newDamage
                );
            } else {
                newDamage = attribute.get().invokeOrDefault(
                    SpecialFeatures.COMMON_ATTACK,
                    newDamage, Float.class,
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
    @SuppressWarnings("null")
    public Component getTitleComponent() {
        if (this.titleComponent == null) {
            this.titleComponent = Component.translatable(this.getTitleTranslationKey());
        }

        return this.titleComponent;
    }

    public String getWriterTranslationKey() {
        if (this.writerTranslationKey == null) {
            this.writerTranslationKey = this.getDescriptionId() + ".writer";
        }

        return this.writerTranslationKey;
    }
    @SuppressWarnings("null")
    public Component getWriterComponent() {
        if (this.writerComponent == null) {
            this.writerComponent = Component.translatable(this.getWriterTranslationKey());
        }

        return this.writerComponent;
    }

    public String getTextTranslationKey() {
        if (this.textTranslationKey == null) {
            this.textTranslationKey = this.getDescriptionId() + ".text";
        }

        return this.textTranslationKey;
    }
    @SuppressWarnings("null")
    public Component getTextComponent() {
        if (this.textComponent == null) {
            this.textComponent = Component.translatable(this.getTextTranslationKey());
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

    @SuppressWarnings("null")
    public Set<ReaperAttribute> getAttributes() {
        return this.attributes.stream()
            .map(Supplier<ReaperAttribute>::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    @SuppressWarnings("null")
    public ItemEnchantments getEnchantments(Level level) {
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        Registry<Enchantment> registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        this.enchantments.forEach((key, lvl) -> {
            mutable.set(registry.getHolderOrThrow(key), lvl);
        });

        return mutable.toImmutable();
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistryNames.Registries.REAPER, RCRegistries.REAPER.getKey(this));
        }

        return this.descriptionId;
    }

    @Override
    public Item asItem() {
        if (this.item == null) {
            this.item = ReaperItem.byReaper(this);
        }

        return this.item;
    }

    @Override
    public String toString() {
        return RCRegistries.REAPER.wrapAsHolder(this).getRegisteredName();
    }
    
    public static class Properties {
        private String absoluteText = "";
        private double damage = 0;
        private double speed = 0;
        private final Set<Supplier<ReaperAttribute>> attributes = new HashSet<>();
        private final Map<ResourceKey<Enchantment>, Integer> enchantments = new HashMap<>();

        public Properties absoluteText(String absoluteText) {
            this.absoluteText = absoluteText;
            return this;
        }

        public Properties damage(double damage) {
            this.damage = damage;
            return this;
        }

        public Properties speed(double speed) {
            this.speed = speed;
            return this;
        }

        public Properties addAttribute(Supplier<ReaperAttribute> attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public Properties addEnchantment(ResourceKey<Enchantment> enchantment, int level) {
            this.enchantments.put(enchantment, level);
            return this;
        }
    }
}
