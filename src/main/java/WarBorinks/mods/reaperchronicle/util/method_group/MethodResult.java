package warborinks.mods.reaperchronicle.util.method_group;

public final class MethodResult {
    private final Object res;

    public MethodResult(Object res) {
        this.res = res;
    }

    public <T> T get(Class<T> resType) {
        return resType.cast(this.res);
    }
}
