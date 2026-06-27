package warborinks.mods.reaperchronicle.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.inventory.SoulAltarMenu;
import warborinks.mods.reaperchronicle.world.level.block.entity.SoulAltar;

@SuppressWarnings("null")
public record SoulAltarShiftPagePayload(SoulAltar.ListType listType, boolean right) implements CustomPacketPayload {
    public static final Type<SoulAltarShiftPagePayload> TYPE = new CustomPacketPayload.Type<>(
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCPacketPayloadNames.SoulAltar.SHIFT_PAGE
        ));
    
    public static final StreamCodec<FriendlyByteBuf, SoulAltarShiftPagePayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.fromCodec(SoulAltar.ListType.CODEC),
        SoulAltarShiftPagePayload::listType,
        ByteBufCodecs.BOOL,
        SoulAltarShiftPagePayload::right,
        SoulAltarShiftPagePayload::new
    );

    public static void handle(final SoulAltarShiftPagePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof SoulAltarMenu menu) {
                menu.handleShiftPage(payload.listType(), payload.right());
            }
        });
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
