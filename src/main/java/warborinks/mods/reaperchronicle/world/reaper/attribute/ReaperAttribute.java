package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforgespi.language.IModFileInfo;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistries;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.event.AddFeaturesEvent;
import warborinks.mods.reaperchronicle.event.InvokeFeatureEvent;
import warborinks.mods.reaperchronicle.util.Args;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.IFeatureClass;

public class ReaperAttribute extends ReaperAttributeBehaviour {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final int color;

    @Nullable private String descriptionId;
    
    public ReaperAttribute(Properties properties) {
        super(properties);
        this.color = properties.color;
    }
    
    public <T> T invoke(@Nonnull String name, @Nonnull Class<T> resType, Object... args)
        throws NoSuchFeatureException, Throwable {
        Map.Entry<List<Class<?>>, FeatureInterface> feature = find(name, args);
        List<Class<?>> paramTypes = feature.getKey();

        InvokeFeatureEvent event = new InvokeFeatureEvent(this, name, paramTypes, args);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            throw new InvocationCanceledException(this, name, paramTypes, args);
        }

        LOGGER.info(
            "Invoke {}#{}({}) for {}",
            this, name,
            paramTypes.toString().replaceAll("^\\[(.*)]$", "$1"),
            Arrays.toString(args)
        );

        return feature.getValue().invoke(Args.of(args)).get(resType);
    }
    public <T> T invokeOrSilence(@Nonnull String name, @Nonnull Class<T> resType, Object... args) {
        return this.invokeOrDeal(name, t -> {}, resType, args);
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
    public <T> T invokeOrDeal(@Nonnull String name, Consumer<Throwable> deal,
        @Nonnull Class<T> resType, Object... args) {
        T result;
        try {
            result = this.invoke(name, resType, args);
        } catch (Throwable throwable) {
            deal.accept(throwable);
            result = null;
        }
        return result;
    }
    public <T> T invokeOrDealAndGet(@Nonnull String name, Function<Throwable, T> dealAndget,
        @Nonnull Class<T> resType, Object... args) {
        try {
            return this.invoke(name, resType, args);
        } catch (Throwable throwable) {
            return dealAndget.apply(throwable);
        }
    }

    public int getColor() {
        return this.color;
    }

    public String getDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId(RCRegistryNames.Registries.REAPER_ATTRIBUTE, RCRegistries.REAPER_ATTRIBUTE.getKey(this));
        }

        return this.descriptionId;
    }

    @Override
    public String toString() {
        return RCRegistries.REAPER_ATTRIBUTE.wrapAsHolder(this).getRegisteredName();
    }

    public static class Properties extends ReaperAttributeBehaviour.Properties {
        private int color = 0xffffff;

        public Properties color(int color) {
            this.color = color;
            return this;
        }
    }

    public static class InvocationCanceledException extends Exception {
        public InvocationCanceledException(ReaperAttribute reaperAttribute,
            String name, List<Class<?>> paramTypes, Object... args) {
            super(
                "The operation of invoking " + reaperAttribute + "#" + name + "(" + 
                paramTypes.toString().replaceAll("^\\[(.*)]$", "$1") +
                ") for " + Arrays.toString(args) +
                " has canceled!"
            );
        }
    }

    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    private static final class Events {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        @SuppressWarnings("null")
        private static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            Map<ResourceLocation, List<IFeatureClass>> idToFeatureClass = new HashMap<>();
            for (IModFileInfo modFile : ModList.get().getModFiles()) {
                idToFeatureClass.putAll(FeatureGetter.getClasses(modFile.getFile().getScanResult()));
            }

            for (ReaperAttribute reaperAttribute : RCRegistries.REAPER_ATTRIBUTE) {
                ResourceLocation key = RCRegistries.REAPER_ATTRIBUTE.getKey(reaperAttribute);
                List<IFeatureClass> featureClasses = idToFeatureClass.get(key);
                if (featureClasses != null && !featureClasses.isEmpty()) {
                    featureClasses.forEach(featureClass -> {
                        if (featureClass != null) {
                            reaperAttribute.properties.addFeatures(featureClass);
                        }
                    });
                }

                AddFeaturesEvent addFeaturesEvent = new AddFeaturesEvent(reaperAttribute);
                ReaperChronicle.EVENT_BUS.post(addFeaturesEvent);

                reaperAttribute.properties.addFeatures(addFeaturesEvent.getFeatures());
                for (IFeatureClass featureClass : addFeaturesEvent.getFeatureClasses()) {
                    reaperAttribute.properties.addFeatures(featureClass);
                }

                reaperAttribute.complete();
            }
        }
    }
}
