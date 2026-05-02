/* SPDX-License-Identifier: GPL-3.0-only */
package warborinks.mods.reaperchronicle;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;

@Mod(ReaperChronicle.MODID)
public class ReaperChronicle {
    public static final String MODID = "reaperchronicle";

    public ReaperChronicle(IEventBus bus, ModContainer container) {
        RCDeferredRegisters.register(bus);
    }
}
