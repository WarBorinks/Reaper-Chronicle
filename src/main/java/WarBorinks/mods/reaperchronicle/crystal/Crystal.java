package warborinks.mods.reaperchronicle.crystal;

import java.util.function.Supplier;

import warborinks.mods.reaperchronicle.attribute.Attribute;

public class Crystal {
    private final Supplier<Attribute> attribute;

    public Crystal(Supplier<Attribute> attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return this.attribute.get();
    }
}
