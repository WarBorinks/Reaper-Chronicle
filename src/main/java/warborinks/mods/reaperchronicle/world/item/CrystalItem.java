package warborinks.mods.reaperchronicle.world.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.SpecialFeatures;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

public class CrystalItem extends Item {
    private static final Map<Crystal, Item> BY_CRYSTAL = new HashMap<>();

    private final Supplier<Crystal> crystal;

    public CrystalItem(Supplier<Crystal> crystal, @Nonnull Properties properties) {
        super(properties);
        this.crystal = crystal;
    }

    public static Item byCrystal(Crystal crystal) {
        return BY_CRYSTAL.getOrDefault(crystal, Items.AIR);
    }

    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        List<ReaperAttribute> attributesHavingUseOn = new ArrayList<>();
        this.crystal.get().getAttributes().forEach(
            attribute -> {
                if (attribute.findFeature(SpecialFeatures.USE_ON)) {
                    attributesHavingUseOn.add(attribute);
                }
            }
        );

        if (attributesHavingUseOn.size() > 0) {
            ReaperAttribute beCalled = attributesHavingUseOn.get(
                ThreadLocalRandom.current().nextInt(attributesHavingUseOn.size())
            );
            InteractionResult result = beCalled.invokeOrDealAndGet(
                SpecialFeatures.USE_ON,
                (args, throwable) -> {
                    ReaperChronicle.LOGGER.warn(
                        "The feature useOn({}) of the ReaperAttribute {} throws {}, fallback to default",
                        UseOnContext.class.getName(),
                        beCalled.getDescriptionId(),
                        throwable.toString()
                    );
                    return super.useOn(context);
                },
                InteractionResult.class,
                this, context
            );
            return result == null ? super.useOn(context) : result;
        } else {
            return super.useOn(context);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level,
        @Nonnull Player player, @Nonnull InteractionHand hand) {
        List<ReaperAttribute> attributesHavingUse = new ArrayList<>();
        this.crystal.get().getAttributes().forEach(
            attribute -> {
                if (attribute.findFeature(SpecialFeatures.USE)) {
                    attributesHavingUse.add(attribute);
                }
            }
        );

        if (attributesHavingUse.size() > 0) {
            ReaperAttribute beCalled = attributesHavingUse.get(
                ThreadLocalRandom.current().nextInt(attributesHavingUse.size())
            );
            InteractionResultHolder<ItemStack> result = beCalled.invokeOrDealAndGet(
                SpecialFeatures.USE,
                (args, throwable) -> {
                    ReaperChronicle.LOGGER.warn(
                        "The feature use({}, {}, {}) of the ReaperAttribute {} throws {}, fallback to default",
                        Level.class.getName(),
                        Player.class.getName(),
                        InteractionHand.class.getName(),
                        beCalled.getDescriptionId(),
                        throwable.toString()
                    );
                    return super.use(level, player, hand);
                },
                InteractionResultHolder.class,
                this, level, player, hand
            );
            return result == null ? super.use(level, player, hand) : result;
        } else {
            return super.use(level, player, hand);
        }
    }

    @Override
    @SuppressWarnings("null")
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context,
        @Nonnull List<Component> tooltipComponents, @Nonnull TooltipFlag tooltipFlag) {
        this.crystal.get().getAttributes().forEach(
            attribute -> RCUtil.addComponentToComponentListWithIngnoringEmpty(
                tooltipComponents,
                Component.translatable(attribute.getDescriptionId())
                    .withColor(attribute.getColor())
            )
        );
    }

    public Crystal getCrystal() {
        return this.crystal.get();
    }

    public void registerCrystals(Map<Crystal, Item> crystalToItemMap, Item item) {
        crystalToItemMap.put(this.getCrystal(), item);
    }

    @Override
    public String getDescriptionId() {
        return this.crystal.get().getDescriptionId();
    }
    
    @EventBusSubscriber(modid = ReaperChronicle.MODID)
    public static class Events {
        @SubscribeEvent
        public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (item instanceof CrystalItem crystalItem) {
                    BY_CRYSTAL.put(crystalItem.getCrystal(), item);
                }
            }
        }
    }
}
