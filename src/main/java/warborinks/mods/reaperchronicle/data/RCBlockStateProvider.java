package warborinks.mods.reaperchronicle.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

@SuppressWarnings("null")
public class RCBlockStateProvider extends BlockStateProvider {
    public RCBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ReaperChronicle.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(
            RCBlocks.NETHER_SAND.get(),
            models().cubeAll(
                RCRegistryNames.Blocks.NETHER_SAND,
                modLoc("block/" + RCRegistryNames.Blocks.NETHER_SAND)
            )
        );
        simpleBlockWithItem(
            RCBlocks.NETHER_SOIL.get(),
            models().cubeAll(
                RCRegistryNames.Blocks.NETHER_SOIL,
                modLoc("block/" + RCRegistryNames.Blocks.NETHER_SOIL)
            )
        );
    }
}
