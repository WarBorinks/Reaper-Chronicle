package warborinks.mods.reaperchronicle.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.world.item.CrystalItems;
import warborinks.mods.reaperchronicle.world.item.RCItems;

@SuppressWarnings("null")
public class RCItemModelProvider extends ItemModelProvider {
    public RCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ReaperChronicle.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(CrystalItems.EMPTY_CRYSTAL.get());
        basicItem(CrystalItems.WATER_CRYSTAL.get());

        basicItem(RCItems.NETHER_DEBRIS.get());
    }
}
