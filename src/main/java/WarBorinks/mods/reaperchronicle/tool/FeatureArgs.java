package warborinks.mods.reaperchronicle.tool;

import java.util.Arrays;
import java.util.List;

public final class FeatureArgs {
    private List<Object> args;

    public FeatureArgs(List<Object> objects) {
        this.args = objects;
    }

    public FeatureArgs(Object... objects) {
        this(Arrays.asList(objects));
    }

    public <T> T get(int index, Class<T> argType) {
        return argType.cast(args.get(index));
    }
}
