package warborinks.mods.reaperchronicle.world.item.crafting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import warborinks.mods.reaperchronicle.RCUtil;
import warborinks.mods.reaperchronicle.util.ItemStackSave;
import warborinks.mods.reaperchronicle.util.TagKeySave;

public class ReaperRecipeIngredient implements ICustomIngredient {
    public static final ReaperRecipeIngredient EMPTY = new ReaperRecipeIngredient(Stream.empty());

    @SuppressWarnings("null")
    private static final MapCodec<Ingredient.Value> VALUE_CODEC = NeoForgeExtraCodecs.xor(
        ItemValue.CODEC, TagValue.CODEC
    ).xmap(
        either -> either.map(itemValue -> itemValue, tagValue -> tagValue),
        value -> {
            if (value instanceof ItemValue itemValue) {
                return Either.left(itemValue);
            } else if (value instanceof TagValue tagValue) {
                return Either.right(tagValue);
            } else {
                throw new UnsupportedOperationException("This is neither an item value nor a tag value.");
            }
        }
    );

    @SuppressWarnings("null")
    public static final MapCodec<ReaperRecipeIngredient> CODEC = NeoForgeExtraCodecs.xor(
        RecordCodecBuilder.<ReaperRecipeIngredient>mapCodec(instance -> instance.group(
            VALUE_CODEC.fieldOf("value").forGetter(ing -> ing.values[0])
        ).apply(instance, value -> ReaperRecipeIngredient.fromValues(Stream.of(value)))),
        RecordCodecBuilder.<ReaperRecipeIngredient>mapCodec(instance -> instance.group(
            VALUE_CODEC.codec().listOf().fieldOf("values").forGetter(ing -> List.of(ing.values))
        ).apply(instance, values -> ReaperRecipeIngredient.fromValues(values.stream())))
    ).xmap(
        either -> either.map(ing -> ing, ing -> ing),
        ing -> {
            if (ing.values.length == 1) {
                return Either.left(ing);
            } else {
                return Either.right(ing);
            }
        }
    ).validate(ing -> {
        if (ing.isEmpty()) {
            return DataResult.error(() -> "Cannot serialize empty ingredient using the map codec");
        }
        return DataResult.success(ing);
    });

    private final Ingredient.Value[] values;

    @SuppressWarnings("null")
    private ReaperRecipeIngredient(Stream<? extends Ingredient.Value> values) {
        List<? extends Ingredient.Value> list = values.toList();

        List<ItemValue> itemValues = list.stream()
            .filter(value -> value != null && value instanceof ItemValue)
            .map(ItemValue.class::cast)
            .collect(RCUtil.getMergedListCollector(
                itemValue -> new ItemStackSave(itemValue.itemStack()),
                itemValue -> itemValue.itemStack().getCount(),
                Integer::sum,
                e -> new ItemValue(ItemStackSave.getStackFromEntry(e))
            ));
        List<TagValue> tagValues = list.stream()
            .filter(value -> value != null && value instanceof TagValue)
            .map(TagValue.class::cast)
            .collect(RCUtil.getMergedListCollector(
                tagValue -> new TagKeySave<Item>(tagValue.tag()), TagValue::count,
                Integer::sum,
                e -> new TagValue(e.getKey().tag(), e.getValue())
            ));
        
        List<Ingredient.Value> valueList = new ArrayList<>();
        valueList.addAll(itemValues);
        valueList.addAll(tagValues);

        this.values = valueList.toArray(new Ingredient.Value[0]);
    }

    private ReaperRecipeIngredient(Ingredient.Value[] values) {
        this(Stream.of(values));
    }

    public static ReaperRecipeIngredient of() {
        return EMPTY;
    }

    public static ReaperRecipeIngredient of(@Nonnull ItemLike item, int amount) {
        return of(new ItemStack(item, amount));
    }
    
    @SuppressWarnings("null")
    public static ReaperRecipeIngredient of(@Nonnull ItemLike... items) {
        return of(Arrays.stream(items).map(item -> new ItemStack(item, 1)));
    }

    public static ReaperRecipeIngredient of(Stream<ItemStack> stacks) {
        return fromValues(stacks.filter(stack -> !stack.isEmpty()).map(ItemValue::new));
    }
    public static ReaperRecipeIngredient of(ItemStack... stacks) {
        return of(Arrays.stream(stacks));
    }

    public static ReaperRecipeIngredient of(TagKey<Item> tag, int amount) {
        return fromValues(Stream.of(new TagValue(tag, amount)));
    }
    public static ReaperRecipeIngredient of(TagKey<Item> tag) {
        return of(tag, 1);
    }

    public static ReaperRecipeIngredient fromValues(Stream<? extends Ingredient.Value> stream) {
        ReaperRecipeIngredient ing = new ReaperRecipeIngredient(stream);
        return ing.isEmpty() ? EMPTY : ing;
    }

    public boolean isEmpty() {
        return this.values.length == 0;
    }

    @Override
    @SuppressWarnings("null")
    public boolean test(@Nonnull ItemStack stack) {
        return this.getItems().anyMatch(
            itemStack -> ItemStack.isSameItemSameComponents(stack, itemStack) && 
                stack.getCount() >= itemStack.getCount()
        );
    }

    @Override
    public Stream<ItemStack> getItems() {
        return Arrays.stream(this.values).flatMap(value -> value.getItems().stream());
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return RCIngredientTypes.REAPER.get();
    }

    @SuppressWarnings("null")
    public static record ItemValue(ItemStack itemStack) implements Ingredient.Value {
        static final MapCodec<ItemValue> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                ItemStack.ITEM_NON_AIR_CODEC.fieldOf("item").forGetter(itemValue -> itemValue.itemStack.getItemHolder()),
                Codec.INT.fieldOf("count").orElse(1).forGetter(itemValue -> itemValue.itemStack.getCount()),
                DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY)
                    .forGetter(itemValue -> itemValue.itemStack.getComponentsPatch())
            ).apply(instance, (item, count, component) -> new ItemValue(new ItemStack(item, count, component)))
        );

        @Override
        public Collection<ItemStack> getItems() {
            return Collections.singleton(this.itemStack);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (other instanceof ItemValue itemValue) {
                return ItemStack.isSameItemSameComponents(itemValue.itemStack, this.itemStack);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 31 * ItemStack.hashItemAndComponents(this.itemStack) + this.itemStack.getCount();
        }
    }

    public static final record TagValue(TagKey<Item> tag, int count) implements Ingredient.Value {
        @SuppressWarnings("null")
        static final MapCodec<TagValue> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(TagValue::tag),
                Codec.INT.fieldOf("count").forGetter(TagValue::count)
            ).apply(instance, TagValue::new)
        );

        @Override
        @SuppressWarnings("null")
        public Collection<ItemStack> getItems() {
            List<ItemStack> list = new ArrayList<>();

            for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(this.tag)) {
                list.add(new ItemStack(holder, this.count));
            }

            if (list.isEmpty()) {
                ItemStack itemStack = new ItemStack(Blocks.BARRIER);
                itemStack.set(DataComponents.CUSTOM_NAME, Component.literal("Empty Tag: " + this.tag.location()));
                list.add(itemStack);
            }
            return list;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            } else if (other instanceof TagValue tagValue) {
                return tagValue.tag.location().equals(this.tag.location()) && tagValue.count == this.count;
            } else {
                return false;
            }
        }
    }
}
