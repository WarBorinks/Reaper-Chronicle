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
public record SoulAltarExtractItemPayload(SoulAltar.ListType listType, int index, int amount)
    implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SoulAltarExtractItemPayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(
            ReaperChronicle.MODID, RCPacketPayloadNames.SoulAltar.EXTRACT_ITEM
        ));
    
    public static final StreamCodec<FriendlyByteBuf, SoulAltarExtractItemPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.fromCodec(SoulAltar.ListType.CODEC),
        SoulAltarExtractItemPayload::listType,
        ByteBufCodecs.VAR_INT,
        SoulAltarExtractItemPayload::index,
        ByteBufCodecs.VAR_INT,
        SoulAltarExtractItemPayload::amount,
        SoulAltarExtractItemPayload::new
    );

    public static void handle(final SoulAltarExtractItemPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof SoulAltarMenu menu) {
                menu.handleExtractItem(payload.listType(), payload.index(), payload.amount());
            }
        });
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
