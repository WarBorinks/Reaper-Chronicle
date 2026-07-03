package warborinks.mods.reaperchronicle.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.inventory.SoulAltarMenu;

@SuppressWarnings("null")
public record SoulAltarSortPayload() implements CustomPacketPayload {
    public static final Type<SoulAltarSortPayload> TYPE = new CustomPacketPayload.Type<>(
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCPacketPayloadNames.SoulAltar.SORT
        ));
    
    public static final StreamCodec<FriendlyByteBuf, SoulAltarSortPayload> STREAM_CODEC = StreamCodec.unit(
        new SoulAltarSortPayload()
    );

    public static void handle(final SoulAltarSortPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof SoulAltarMenu menu) {
                menu.handleSort();
            }
        });
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
