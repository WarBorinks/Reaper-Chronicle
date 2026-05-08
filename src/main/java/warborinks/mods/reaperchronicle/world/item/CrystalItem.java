package warborinks.mods.reaperchronicle.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystal;

@SuppressWarnings({"null", "unchecked"})
public class CrystalItem extends Item {
    private final Supplier<Crystal> crystal;

    public CrystalItem(Supplier<Crystal> crystal, Properties properties) {
        super(properties);
        this.crystal = crystal;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        List<ReaperAttribute> attributesHavingUseOn = new ArrayList<>();
        this.crystal.get().getAttributes().forEach(
            attribute -> {
                if (attribute.findFeature("useOn")) {
                    attributesHavingUseOn.add(attribute);
                }
            }
        );

        if (attributesHavingUseOn.size() == 1) {
            return attributesHavingUseOn.getFirst().apply(
                "useOn", InteractionResult.class,
                context
            );
        } else {
            return super.useOn(context);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        List<ReaperAttribute> attributesHavingUse = new ArrayList<>();
        this.crystal.get().getAttributes().forEach(
            attribute -> {
                if (attribute.findFeature("use")) {
                    attributesHavingUse.add(attribute);
                }
            }
        );

        if (attributesHavingUse.size() == 1) {
            return attributesHavingUse.getFirst().apply(
                "use", InteractionResultHolder.class,
                level, player, hand
            );
        } else {
            return super.use(level, player, hand);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
        List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        this.crystal.get().getAttributes().forEach(
            attribute -> tooltipComponents.add(
                Component.translatable(attribute.getDescriptionId())
                    .withColor(attribute.getColor())
            )
        );
    }

    public Crystal getCrystal() {
        return this.crystal.get();
    }

    @Override
    public String getDescriptionId() {
        return this.crystal.get().getDescriptionId();
    }
}
