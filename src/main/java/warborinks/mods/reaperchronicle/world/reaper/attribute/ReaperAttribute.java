package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.Util;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;

@SuppressWarnings("null")
public class ReaperAttribute {
    private final int color;
    private final Map<String, Function<Args, Result>> features;

    @Nullable private String descriptionId;
    
    public ReaperAttribute(int color) {
        this.color = color;
        this.features = new HashMap<>();
    }

    public ReaperAttribute addFeature(@Nonnull String name, @Nonnull Function<Args, Result> feature) {
        this.features.put(name, feature);
        return this;
    }

    public ReaperAttribute addFeatures(@Nonnull Map<String, Function<Args, Result>> features) {
        this.features.putAll(features);
        return this;
    }
    public ReaperAttribute addFeatures(@Nonnull ReaperAttribute reaperAttribute) {
        return this.addFeatures(reaperAttribute.features);
    }
    
    public ReaperAttribute addFeatures(@Nonnull Class<?> cls) {
        this.features.putAll(FeatureGetter.get(cls));
        return this;
    }
    

    public <T> T apply(@Nonnull String name, Class<T> resType, Object... objects) {
        if (this.findFeature(name)) {
            return this.features.get(name).apply(new Args(objects)).get(resType);
        } else {
            return null;
        }
    }
    public <T> T apply(@Nonnull String name, Supplier<T> ifThereNot, Class<T> resType, Object... objects) {
        if (this.findFeature(name)) {
            return this.apply(name, resType, objects);
        } else {
            return ifThereNot == null ? null : ifThereNot.get();
        }
    }

    public boolean findFeature(@Nonnull String name) {
        return this.features.containsKey(name);
    }

    public static final class Args {
        private final List<Object> args;

        public Args(Object... objects) {
            this.args = List.of(objects);
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

        public <T> T get(Class<T> resType) {
            return resType == null ? null : resType.cast(this.res);
        }
    }

    
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Feature {
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistries.Names.REAPER_ATTRIBUTE, RCRegistries.REAPER_ATTRIBUTE.getKey(this));
        }

        return this.descriptionId;
    }

    public int getColor() {
        return this.color;
    }
}
