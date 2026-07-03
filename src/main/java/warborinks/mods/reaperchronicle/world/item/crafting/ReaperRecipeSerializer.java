package warborinks.mods.reaperchronicle.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

@SuppressWarnings("null")
public class ReaperRecipeSerializer implements RecipeSerializer<ReaperRecipe> {
    public static final MapCodec<ReaperRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(ReaperRecipe::getGroup),
            Ingredient.CODEC.listOf().fieldOf("crystals").forGetter(ReaperRecipe::getCrystals),
            Ingredient.CODEC.listOf().fieldOf("reapers").forGetter(ReaperRecipe::getReapers),
            Ingredient.CODEC.listOf().fieldOf("others").forGetter(ReaperRecipe::getOthers),
            ItemStack.CODEC.fieldOf("result").forGetter(ReaperRecipe::getResult)
        ).apply(instance, ReaperRecipe::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ReaperRecipe> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, ReaperRecipe::getGroup,
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), ReaperRecipe::getCrystals,
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), ReaperRecipe::getReapers,
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), ReaperRecipe::getOthers,
        ItemStack.STREAM_CODEC, ReaperRecipe::getResult,
        ReaperRecipe::new
    );

    @Override
    public MapCodec<ReaperRecipe> codec() {
        return CODEC;
    }
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ReaperRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
