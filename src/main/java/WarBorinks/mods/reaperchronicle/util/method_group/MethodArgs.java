package warborinks.mods.reaperchronicle.util.method_group;

import java.util.Arrays;
import java.util.List;

public final class MethodArgs {
    private final List<Object> args;

    public MethodArgs(List<Object> objects) {
        this.args = objects;
    }

    public MethodArgs(Object... objects) {
        this(Arrays.asList(objects));
    }

    public <T> T get(int index, Class<T> argType) {
        return argType.cast(args.get(index));
    }
}
