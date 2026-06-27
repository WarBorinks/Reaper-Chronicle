package warborinks.mods.reaperchronicle.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import warborinks.mods.reaperchronicle.ReaperChronicle;

@EventBusSubscriber(modid = ReaperChronicle.MODID)
public final class RCPayloadsRegister {
    @SubscribeEvent
    @SuppressWarnings("null")
    private static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);

        registrar.playToServer(
            SoulAltarExtractItemPayload.TYPE,
            SoulAltarExtractItemPayload.STREAM_CODEC,
            SoulAltarExtractItemPayload::handle
        );

        registrar.playToServer(
            SoulAltarShiftPagePayload.TYPE,
            SoulAltarShiftPagePayload.STREAM_CODEC,
            SoulAltarShiftPagePayload::handle
        );

        registrar.playToServer(
            SoulAltarSortPayload.TYPE,
            SoulAltarSortPayload.STREAM_CODEC,
            SoulAltarSortPayload::handle
        );
    }
}
