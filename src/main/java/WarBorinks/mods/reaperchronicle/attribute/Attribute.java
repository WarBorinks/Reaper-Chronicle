package warborinks.mods.reaperchronicle.attribute;

import java.util.function.Function;

import warborinks.mods.reaperchronicle.util.method_group.MethodArgs;
import warborinks.mods.reaperchronicle.util.method_group.MethodGroup;
import warborinks.mods.reaperchronicle.util.method_group.MethodResult;

public class Attribute {
    protected MethodGroup features;

    public Attribute() {
        this.features = new MethodGroup();
    }

    public void addAttribute(String name, Function<MethodArgs, MethodResult> attribute) {
        this.features.addMethod(name, attribute);
    }

    public <T> T useAttribute(String name, Class<T> resType, Object... objects) {
        return this.features.apply(name, resType, objects);
    }

    public boolean findAttribute(String name) {
        return this.features.findMethod(name);
    }
}
