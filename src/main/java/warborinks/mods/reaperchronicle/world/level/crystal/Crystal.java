package warborinks.mods.reaperchronicle.world.level.crystal;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.item.CrystalItem;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public class Crystal implements ItemLike {
    private final Set<Supplier<ReaperAttribute>> attributes;

    @Nullable private Item item;
    
    @Nullable private String descriptionId;

    public Crystal() {
        this.attributes = Set.of();
    }

    @SafeVarargs
    public Crystal(@Nonnull Supplier<ReaperAttribute>... attributes) {
        this.attributes = Set.of(attributes);
    }

    @Override
    public Item asItem() {
        if (this.item == null) {
            this.item = CrystalItem.byCrystal(this);
        }

        return this.item;
    }

    public Set<ReaperAttribute> getAttributes() {
        return this.attributes.stream()
            .map(Supplier<ReaperAttribute>::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistryNames.Registries.CRYSTAL, RCRegistries.CRYSTAL.getKey(this));
        }

        return this.descriptionId;
    }
}
