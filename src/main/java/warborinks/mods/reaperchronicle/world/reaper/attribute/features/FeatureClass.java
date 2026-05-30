package warborinks.mods.reaperchronicle.world.reaper.attribute.features;

import java.util.List;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Args;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributeBehaviour.Result;
import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureAnnotations.Feature;

public interface FeatureClass {
    @Feature(name = SpecialFeatures.COMMON_ATTACK, types = {LivingEntity.class, LivingEntity.class, Float.class})
    default public Result addDamageOnCommonAttack(Args args) {
        return Result.of(args.get(2, Float.class));
    }

    @Feature(name = SpecialFeatures.SPECIAL_ATTACK, types = {LivingEntity.class, LivingEntity.class, Float.class})
    default public Result addDamageOnSpecialAttack(Args args) {
        return Result.of(args.get(2, Float.class));
    }

    @Feature(name = SpecialFeatures.USE, types = {Item.class, Level.class, Player.class, InteractionHand.class})
    default public Result use(Args args) throws Throwable {
        return RCUtil.invokeMethodFromInstance(
            args.get(0, Item.class),
            Item.class,
            SpecialFeatures.USE,
            InteractionResultHolder.class,
            List.of(
                Level.class,
                Player.class,
                InteractionHand.class
            ), args.getValues(1)
        );
    }
    
    @Feature(name = SpecialFeatures.USE_ON, types = {Item.class, UseOnContext.class})
    default public Result useOn(Args args) throws Throwable {
        return RCUtil.invokeMethodFromInstance(
            args.get(0, Item.class),
            Item.class,
            SpecialFeatures.USE_ON,
            InteractionResult.class,
            UseOnContext.class,
            args.getValues(1)
        );
    }
}
