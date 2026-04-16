package warborinks.mods.reaperchronicle.item;

import java.util.function.Supplier;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import warborinks.mods.reaperchronicle.crystal.Crystal;

@SuppressWarnings("null")
public class CrystalItem extends Item {
    private final Supplier<Crystal> crystal;

    public CrystalItem(Supplier<Crystal> crystal, Item.Properties properties) {
        super(properties);
        this.crystal = crystal;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (this.crystal.get().getAttribute().findAttribute("useOn")) {
            return super.useOn(context);
        } else {
            return this.crystal.get().getAttribute().useAttribute("useOn", InteractionResult.class, context);
        }
    }
}
