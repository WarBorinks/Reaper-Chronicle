package warborinks.mods.reaperchronicle.core.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;

@SuppressWarnings("null")
public class RCDataComponentTypes {
    private static final DeferredRegister<DataComponentType<?>> REGISTRAR = RCDeferredRegisters.DATA_COMPONENT_TYPE;

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TEXT_INDEX = REGISTRAR.register(
        RCRegistryNames.DataComponentTypes.TEXT_INDEX,
        () -> new DataComponentType.Builder<Integer>()
            .persistent(ExtraCodecs.NON_NEGATIVE_INT)
            .networkSynchronized(ByteBufCodecs.VAR_INT)
            .build()
    );

    public static void load() {}
}
