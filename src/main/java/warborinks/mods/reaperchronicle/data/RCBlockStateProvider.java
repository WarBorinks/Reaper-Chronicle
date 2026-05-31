package warborinks.mods.reaperchronicle.data;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.core.registries.RCRegistryNames;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;

public final class RCBlockStateProvider extends BlockStateProvider {
    public RCBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ReaperChronicle.MODID, exFileHelper);
    }

    @Override
    @SuppressWarnings("null")
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
        simpleBlockWithItem(
            RCBlocks.SOUL_ALTAR.get(),
            this.getSoulAltarModel()
        );
    }

    @SuppressWarnings("null")
    private ModelFile getSoulAltarModel() {
        BlockModelBuilder model = models()
            .getBuilder("block/" + RCRegistryNames.BlockEntityTypes.SOUL_ALTAR)
            .parent(new ModelFile.UncheckedModelFile("minecraft:block/block"))
            .texture("0", modLoc("block/" + RCRegistryNames.BlockEntityTypes.SOUL_ALTAR));
        
        model.element()
            .from(0, 0, 0).to(16, 8, 16)
            .face(Direction.NORTH).uvs(4, 14, 8, 16).texture("#0").end()
            .face(Direction.EAST).uvs(0, 14, 4, 16).texture("#0").end()
            .face(Direction.SOUTH).uvs(12, 14, 16, 16).texture("#0").end()
            .face(Direction.WEST).uvs(8, 14, 12, 16).texture("#0").end()
            .face(Direction.UP).uvs(8, 14, 4, 10).texture("#0").end()
            .face(Direction.DOWN).uvs(12, 10, 8, 14).texture("#0").end()
            .end();
        
        model.element()
            .from(4, 8, 4).to(12, 11, 12)
            .face(Direction.NORTH).uvs(6, 9.25f, 8, 10).texture("#0").end()
            .face(Direction.EAST).uvs(4, 9.25f, 6, 10).texture("#0").end()
            .face(Direction.SOUTH).uvs(10, 9.25f, 12, 10).texture("#0").end()
            .face(Direction.WEST).uvs(8, 9.25f, 10, 10).texture("#0").end()
            .face(Direction.UP).uvs(8, 9.25f, 6, 7.25f).texture("#0").end()
            .face(Direction.DOWN).uvs(10, 7.25f, 8, 9.25f).texture("#0").end()
            .end();
        
        model.element()
            .from(6, 11, 6).to(10, 12, 10)
            .face(Direction.NORTH).uvs(7, 7, 8, 7.25f).texture("#0").end()
            .face(Direction.EAST).uvs(6, 7, 7, 7.25f).texture("#0").end()
            .face(Direction.SOUTH).uvs(9, 7, 10, 7.25f).texture("#0").end()
            .face(Direction.WEST).uvs(8, 7, 9, 7.25f).texture("#0").end()
            .face(Direction.UP).uvs(8, 7, 7, 6).texture("#0").end()
            .face(Direction.DOWN).uvs(9, 6, 8, 7).texture("#0").end()
            .end();
        
        int[][] pillars = {
            {0, 8, 0, 3, 14, 3},
            {0, 8, 13, 3, 14, 16},
            {13, 8, 13, 16, 14, 16},
            {13, 8, 0, 16, 14, 3}
        };
        for (int[] p : pillars) {
            model.element()
                .from(p[0], p[1], p[2]).to(p[3], p[4], p[5])
                .face(Direction.NORTH).uvs(1.75f, 12.5f, 2.5f, 14).texture("#0").end()
                .face(Direction.EAST).uvs(1, 12.5f, 1.75f, 14).texture("#0").end()
                .face(Direction.SOUTH).uvs(3.25f, 12.5f, 4, 14).texture("#0").end()
                .face(Direction.WEST).uvs(2.5f, 12.5f, 3.25f, 14).texture("#0").end()
                .face(Direction.UP).uvs(2.5f, 12.5f, 1.75f, 11.75f).texture("#0").end()
                .face(Direction.DOWN).uvs(3.25f, 11.75f, 2.5f, 12.5f).texture("#0").end()
                .end();
        }
        
        return model;
    }
}
