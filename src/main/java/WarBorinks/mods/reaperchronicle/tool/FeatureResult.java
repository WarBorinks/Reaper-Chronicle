package warborinks.mods.reaperchronicle.tool;

public class FeatureResult {
    private Object res;

    public FeatureResult(Object res) {
        this.res = res;
    }

    public <T> T get(Class<T> resType) {
        return resType.cast(this.res);
    }
}
