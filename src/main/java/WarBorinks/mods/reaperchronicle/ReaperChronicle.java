/* SPDX-License-Identifier: GPL-3.0-only */
package WarBorinks.mods.reaperchronicle;

import WarBorinks.mods.reaperchronicle.register.RCBlockRegister;
import WarBorinks.mods.reaperchronicle.register.RCCreativeModeTabRegister;
import WarBorinks.mods.reaperchronicle.register.RCItemRegister;
import WarBorinks.mods.reaperchronicle.register.RCMobEffectRegister;
import WarBorinks.mods.reaperchronicle.register.RCSoundEventRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ReaperChronicle.MODID)
public class ReaperChronicle {
    public static final String MODID = "reaperchronicle";

    public ReaperChronicle(IEventBus bus, ModContainer container) {
        RCSoundEventRegister.register(bus);
        RCBlockRegister.register(bus);
        RCItemRegister.register(bus);
        RCMobEffectRegister.register(bus);
        RCCreativeModeTabRegister.register(bus);
    }
}
