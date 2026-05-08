package warborinks.mods.reaperchronicle.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("null")
public record ReaperRecipeIngredient(Ingredient ingredient, Integer count) {
    public static final Codec<ReaperRecipeIngredient> CODEC = RecordCodecBuilder.create(
        inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ReaperRecipeIngredient::ingredient),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ReaperRecipeIngredient::count)
        ).apply(inst, ReaperRecipeIngredient::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ReaperRecipeIngredient> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, ReaperRecipeIngredient::ingredient,
        ByteBufCodecs.VAR_INT, ReaperRecipeIngredient::count,
        ReaperRecipeIngredient::new
    );
}
