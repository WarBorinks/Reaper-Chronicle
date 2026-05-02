package warborinks.mods.reaperchronicle.world.reaper.reaper_attribute;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.Util;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;

@SuppressWarnings("null")
public class ReaperAttribute extends FeatureGroup {
    private final int color;

    @Nullable private String descriptionId;
    
    public ReaperAttribute(int color) {
        super();
        this.color = color;
    }

    public ReaperAttribute addFeature(@Nonnull String name, @Nonnull Function<Args, Result> feature) {
        return (ReaperAttribute) super.addFeature(name, feature);
    }

    public ReaperAttribute addFeatures(@Nonnull Map<String, Function<Args, Result>> features) {
        return (ReaperAttribute) super.addFeatures(features);
    }
    public ReaperAttribute addFeatures(@Nonnull FeatureGroup featureGroup) {
        return this.addFeatures(featureGroup.features);
    }
    
    public ReaperAttribute addFeatures(@Nonnull Class<?> cls) {
        return (ReaperAttribute) super.addFeatures(cls);
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("reaper_attribute", RCRegistries.REAPER_ATTRIBUTE.getKey(this));
        }

        return this.descriptionId;
    }

    public int getColor() {
        return this.color;
    }
}
