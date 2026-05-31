package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.Util;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforgespi.language.ModFileScanData;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureClass;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureGetter;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;

public class ReaperAttribute extends ReaperAttributeBehaviour {
    private final int color;

    private boolean locked = false;

    @Nullable private String descriptionId;
    
    public ReaperAttribute(int color) {
        super();
        this.color = color;
    }

    @Override
    public void add(@Nonnull String name,
        @Nonnull FeatureInterface feature, Class<?>... paramTypes) {
        try {
            this.addOrThrow(name, feature, paramTypes);
        } catch (LockedException exception) {}
    }
    
    public void addOrThrow(@Nonnull String name,
        @Nonnull FeatureInterface feature, Class<?>... paramTypes) {
        if (this.isLocked()) {
            throw new LockedException(this);
        } else {
            super.add(name, feature, paramTypes);
        }
    }

    public ReaperAttribute addFeature(@Nonnull String name,
        @Nonnull FeatureInterface feature, Class<?>... paramTypes) {
        try {
            return this.addFeatureOrThrow(name, feature, paramTypes);
        } catch (LockedException exception) {
            return this;
        }
    }
    public ReaperAttribute addFeatureOrThrow(@Nonnull String name,
        @Nonnull FeatureInterface feature, Class<?>... paramTypes) {
        if (this.isLocked()) {
            throw new LockedException(this);
        } else {
            add(name, feature, paramTypes);
            return this;
        }
    }

    public ReaperAttribute addFeatures(@Nonnull FeatureClass featureClass) {
        try {
            return this.addFeaturesOrThrow(featureClass);
        } catch (LockedException exception) {
            return this;
        }
    }
    @SuppressWarnings("null")
    public ReaperAttribute addFeaturesOrThrow(@Nonnull FeatureClass featureClass) {
        if (this.isLocked()) {
            throw new LockedException(this);
        } else {
            Map<String, Map<List<Class<?>>, FeatureInterface>> features = FeatureGetter.getFeatures(featureClass);
            features.forEach((name, featureMap) -> {
                featureMap.forEach((paramTypes, feature) -> {
                    this.addFeature(name, feature, paramTypes.toArray(new Class<?>[0]));
                });
            });
            return this;
        }
    }
    
    public <T> T invoke(@Nonnull String name, @Nonnull Class<T> resType, Object... args)
        throws NoSuchFeatureException, Throwable {
        return invoke(name, args).get(resType);
    }
    public <T> T invokeOrSilence(@Nonnull String name, @Nonnull Class<T> resType, Object... args) {
        return this.invokeOrDeal(name, (a, t) -> {}, resType, args);
    }
    public <T> T invokeOrDefault(@Nonnull String name, T defaultValue,
        @Nonnull Class<T> resType, Object... args) {
        return this.invokeOrGet(name, () -> defaultValue, resType, args);
    }
    public <T> T invokeOrGet(@Nonnull String name, @Nonnull Supplier<T> getter,
        @Nonnull Class<T> resType, Object... args) {
        try {
            return this.invoke(name, resType, args);
        } catch (Throwable throwable) {
            return getter.get();
        }
    }
    public <T> T invokeOrDeal(@Nonnull String name, BiConsumer<Object[], Throwable> deal,
        @Nonnull Class<T> resType, Object... args) {
        T result;
        try {
            result = this.invoke(name, resType, args);
        } catch (Throwable throwable) {
            deal.accept(args, throwable);
            result = null;
        }
        return result;
    }
    public <T> T invokeOrDealAndGet(@Nonnull String name, BiFunction<Object[], Throwable, T> dealAndget,
        @Nonnull Class<T> resType, Object... args) {
        try {
            return this.invoke(name, resType, args);
        } catch (Throwable throwable) {
            return dealAndget.apply(args, throwable);
        }
    }
    
    public void lock() {
        this.locked = true;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistryNames.Registries.REAPER_ATTRIBUTE, RCRegistries.REAPER_ATTRIBUTE.getKey(this));
        }

        return this.descriptionId;
    }
    
    public static final class LockedException extends RuntimeException {
        public LockedException(ReaperAttribute reaperAttribute) {
            super("The ReaperAttribute " + reaperAttribute.getDescriptionId() + " is locked!");
        }
    }

    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    private static final class Events {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        @SuppressWarnings("null")
        private static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            ModFileScanData modFileScanData = RCUtil.getModFileScanDataByModContainer(ReaperChronicle.getModContainer());
            Map<String, FeatureClass> idToFeatureClass = FeatureGetter.getClasses(modFileScanData);
            for (ReaperAttribute reaperAttribute : RCRegistries.REAPER_ATTRIBUTE) {
                String id = RCRegistries.REAPER_ATTRIBUTE.getKey(reaperAttribute).getPath();
                FeatureClass featureClass = idToFeatureClass.get(id);
                if (featureClass != null) {
                    reaperAttribute.addFeatures(featureClass);
                }
                reaperAttribute.lock();
            }
        }
    }
}
