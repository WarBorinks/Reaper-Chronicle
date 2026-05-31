package warborinks.mods.reaperchronicle.data;

import net.minecraft.data.PackOutput;
import warborinks.mods.reaperchronicle.ReaperChronicle;
import warborinks.mods.reaperchronicle.data.language.ExtendedLanguageProvider;
import warborinks.mods.reaperchronicle.world.effect.RCMobEffects;
import warborinks.mods.reaperchronicle.world.item.RCCreativeModeTabs;
import warborinks.mods.reaperchronicle.world.item.RCItems;
import warborinks.mods.reaperchronicle.world.level.block.RCBlocks;
import warborinks.mods.reaperchronicle.world.level.crystal.Crystals;
import warborinks.mods.reaperchronicle.world.reaper.Reapers;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttributes;

public final class RCLanguageProvider extends ExtendedLanguageProvider {
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
    @SuppressWarnings("null")
    protected void addTranslations() {
        addBlock(
            RCBlocks.NETHER_SAND,
            choose("Nether Sand", "冥沙", "玄砂")
        );
        addBlock(
            RCBlocks.NETHER_SOIL,
            choose("Nether Soil", "冥土", "幽壤")
        );
        addBlock(
            RCBlocks.SOUL_ALTAR,
            choose("Soul Altar", "灵魂祭坛", "靈魂祭壇")
        );

        addCreativeModeTab(
            RCCreativeModeTabs.CRYSTAL_INGREDIENTS,
            choose("Crystal Ingredients", "结晶原料", "晶胚")
        );
        addCreativeModeTab(
            RCCreativeModeTabs.CRYSTALS,
            choose("Crystals", "结晶", "結晶")
        );
        addCreativeModeTab(
            RCCreativeModeTabs.REAPERS,
            choose("Reapers", "渡魂刃", "渡魂刃")
        );

        addCrystal(
            Crystals.EMPTY_CRYSTAL,
            choose("Emtpy Crystal", "空结晶", "空結晶")
        );
        addCrystal(
            Crystals.WATER_CRYSTAL,
            choose("Water Crystal", "水之结晶", "水之結晶")
        );

        addItem(
            RCItems.NETHER_DEBRIS,
            choose("Nether Debris", "冥界残骸", "冥墟遺骸")
        );
        
        addEffect(
            RCMobEffects.NETHER_POISON,
            choose("Nether Poison", "冥毒", "陰沴")
        );

        addReaper(
            Reapers.COMMON_REAPER,
            choose("Common Reaper", "凡刃", "凡刃"),
            "", "", ""
        );

        addReaperAttribute(
            ReaperAttributes.EMPTY_ATTRIBUTE,
            choose("", "", "")
        );
        addReaperAttribute(
            ReaperAttributes.WATER_ATTRIBUTE,
            choose("Water", "水", "水")
        );
    }
}
