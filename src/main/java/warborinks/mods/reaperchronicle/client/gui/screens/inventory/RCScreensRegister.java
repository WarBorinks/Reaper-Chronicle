package warborinks.mods.reaperchronicle.client.gui.screens.inventory;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.inventory.RCMenuTypes;

@EventBusSubscriber(modid = ReaperChronicle.MODID, value = Dist.CLIENT)
public class RCScreensRegister {
    @SubscribeEvent
    @SuppressWarnings("null")
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(RCMenuTypes.SOUL_ALTAR.get(), SoulAltarScreen::new);
    }
}
