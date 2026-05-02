package warborinks.mods.reaperchronicle.world.reaper.reaper_attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

@SuppressWarnings("null")
public class FeatureGroup {
    protected final Map<String, Function<Args, Result>> features;

    public FeatureGroup() {
        this.features = new HashMap<>();
    }

    public FeatureGroup addFeature(@Nonnull String name, @Nonnull Function<Args, Result> feature) {
        this.features.put(name, feature);
        return this;
    }

    public FeatureGroup addFeatures(@Nonnull Map<String, Function<Args, Result>> features) {
        this.features.putAll(features);
        return this;
    }
    public FeatureGroup addFeatures(@Nonnull FeatureGroup featureGroup) {
        return this.addFeatures(featureGroup.features);
    }
    
    public FeatureGroup addFeatures(@Nonnull Class<?> cls) {
        this.features.putAll(FeatureGetter.get(cls));
        return this;
    }

    public <T> T apply(@Nonnull String name, @Nonnull Class<T> resType, Object... objects) {
        if (this.findFeature(name)) {
            return this.features.get(name).apply(new Args(objects)).get(resType);
        } else {
            return null;
        }
    }

    public boolean findFeature(@Nonnull String name) {
        return this.features.containsKey(name);
    }

    public static final class Args {
        private final List<Object> args;

        public Args(Object... objects) {
            this.args = Arrays.asList(objects);
        }

        public <T> T get(@Nonnull Integer index, @Nonnull Class<T> argType) {
            return argType.cast(args.get(index));
        }
    }
    
    public static final class Result {
        private final Object res;

        public Result(Object res) {
            this.res = res;
        }

        public <T> T get(@Nonnull Class<T> resType) {
            return resType.cast(this.res);
        }
    }

    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Feature {
    }
}
