package warborinks.mods.reaperchronicle.util.method_group;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class MethodGroup {
    private final Map<String, Function<MethodArgs, MethodResult>> methods;

    public MethodGroup() {
        this.methods = new HashMap<>();
    }

    public void addMethod(String name, Function<MethodArgs, MethodResult> method) {
        if (method != null) {
            this.methods.put(name, method);
        }
    }

    public <T> T apply(String name, Class<T> resType, Object... objects) {
        if (this.findMethod(name)) {
            return this.methods.get(name).apply(new MethodArgs(objects)).get(resType);
        } else {
            return null;
        }
    }

    public boolean findMethod(String name) {
        return this.methods.containsKey(name);
    }
}
