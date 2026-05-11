package warborinks.mods.reaperchronicle.data;

import net.minecraft.data.PackOutput;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.data.language.ExtendedLanguageProvider;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;
import warborinks.mods.reaperchronicle.world.item.RCCreativeModeTabs;
import warborinks.mods.reaperchronicle.world.item.RCItems;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributes;
import warborinks.mods.reaperchronicle.world.reaper.crystal.Crystals;

@SuppressWarnings("null")
public class RCLanguageProvider extends ExtendedLanguageProvider {
    private final String locale;

    public RCLanguageProvider(PackOutput output, String locale) {
        super(output, ReaperChronicle.MODID, locale);
        this.locale = locale;
    }

    private String choose(String en_us, String zh_cn, String lzh) {
        if (this.locale == "en_us") {
            return en_us;
        } else if (this.locale == "zh_cn") {
            return zh_cn;
        } else if (this.locale == "lzh") {
            return lzh;
        } else {
            return "";
        }
    }

    @Override
    protected void addTranslations() {
        add(
            RCBlocks.NETHER_SAND.get(),
            choose("Nether Sand", "冥沙", "玄砂")
        );
        add(
            RCBlocks.NETHER_SOIL.get(),
            choose("Nether Soil", "冥土", "幽壤")
        );
        add(
            RCBlocks.SOUL_ALTAR.get(),
            choose("Soul Altar", "灵魂祭坛", "靈魂祭壇")
        );

        add(
            RCCreativeModeTabs.CRYSTAL_INGREDIENTS.get(),
            choose("Crystal Ingredients", "结晶原料", "晶胚")
        );
        add(
            RCCreativeModeTabs.CRYSTALS.get(),
            choose("Crystals", "结晶", "結晶")
        );
        add(
            RCCreativeModeTabs.REAPERS.get(),
            choose("Reapers", "渡魂刃", "渡魂刃")
        );

        add(
            Crystals.EMPTY_CRYSTAL.get(),
            choose("Emtpy Crystal", "空结晶", "空結晶")
        );
        add(
            Crystals.WATER_CRYSTAL.get(),
            choose("Water Crystal", "水之结晶", "水之結晶")
        );

        add(
            RCItems.NETHER_DEBRIS.get(),
            choose("Nether Debris", "冥界残骸", "冥墟遺骸")
        );
        
        add(
            RCMobEffects.NETHER_POISON.get(),
            choose("Nether Poison", "冥毒", "陰沴")
        );

        add(
            ReaperAttributes.EMPTY_ATTRIBUTE.get(),
            choose("Emtpy", "无", "無")
        );
        add(
            ReaperAttributes.WATER_ATTRIBUTE.get(),
            choose("Water", "水", "水")
        );
    }
}
