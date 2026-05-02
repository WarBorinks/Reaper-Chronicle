package warborinks.mods.reaperchronicle.world.reaper.crystal;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.Util;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.world.reaper.reaper_attribute.ReaperAttribute;

public class Crystal {
    private final Set<Supplier<ReaperAttribute>> attributes;
    
    @Nullable private String descriptionId;

    @SafeVarargs
    public Crystal(@Nonnull Supplier<ReaperAttribute>... attributes) {
        this.attributes = Set.of(attributes);
    }

    public Set<ReaperAttribute> getAttributes() {
        return this.attributes.stream()
            .map(Supplier<ReaperAttribute>::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("crystal", RCRegistries.CRYSTAL.getKey(this));
        }

        return this.descriptionId;
    }
}
