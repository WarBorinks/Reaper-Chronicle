package warborinks.mods.reaperchronicle.register;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.attribute.Attribute;

public class RCAttributes {
    private final DeferredRegister<Attribute> CRYSTAL_ATTRIBUTE;

    public final DeferredHolder<Attribute, Attribute> EMPTY_ATTRIBUTE;

    RCAttributes(DeferredRegister<Attribute> crystal_attribute) {
        this.CRYSTAL_ATTRIBUTE = crystal_attribute;

        this.EMPTY_ATTRIBUTE = this.CRYSTAL_ATTRIBUTE.register(
            "empty_attribute",
            () -> new Attribute()
        );
    }
}
