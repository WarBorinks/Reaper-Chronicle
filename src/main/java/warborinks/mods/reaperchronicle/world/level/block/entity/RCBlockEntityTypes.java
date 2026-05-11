package warborinks.mods.reaperchronicle.world.level.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> REGISTRAR = RCDeferredRegisters.BLOCK_ENTITY_TYPE;

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulAltar>> SOUL_ALTAR = REGISTRAR.register(
        RCRegistryNames.BlockEntityTypes.SOUL_ALTAR,
        () -> BlockEntityType.Builder.of(
            SoulAltar::new,
            RCBlocks.SOUL_ALTAR.get()
        ).build(null)
    );

    public static void load() {}
}
