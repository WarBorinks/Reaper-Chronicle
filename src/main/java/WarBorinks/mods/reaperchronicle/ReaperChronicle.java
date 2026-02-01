/* SPDX-License-Identifier: GPL-3.0-only */
package WarBorinks.mods.reaperchronicle;

import WarBorinks.mods.reaperchronicle.register.RCBlocks;
import WarBorinks.mods.reaperchronicle.register.RCCreativeModeTabs;
import WarBorinks.mods.reaperchronicle.register.RCItems;
import WarBorinks.mods.reaperchronicle.register.RCMobEffects;
import WarBorinks.mods.reaperchronicle.register.RCSoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ReaperChronicle.MODID)
public class ReaperChronicle {
    public static final String MODID = "reaperchronicle";

    public ReaperChronicle(IEventBus bus, ModContainer container) {
        RCSoundEvents.register(bus);
        RCBlocks.register(bus);
        RCItems.register(bus);
        RCMobEffects.register(bus);
        RCCreativeModeTabs.register(bus);
    }
}
