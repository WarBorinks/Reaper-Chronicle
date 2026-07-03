package warborinks.mods.reaperchronicle.util;

import net.minecraft.tags.TagKey;

public record TagKeySave<T>(TagKey<T> tag) {
    @Override
    public int hashCode() {
        int registry = this.tag.registry().registry().hashCode() * 31 + this.tag.registry().location().hashCode();
        int location = this.tag.location().hashCode();
        return registry * 31 + location;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof TagKeySave save) {
            return save.tag.location().equals(this.tag.location());
        } else {
            return false;
        }
    }
}
