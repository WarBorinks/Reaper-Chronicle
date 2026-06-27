/* SPDX-License-Identifier: GPL-3.0-only */
package warborinks.mods.reaperchronicle;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import warborinks.mods.reaperchronicle.core.registries.RCDeferredRegisters;

@Mod(ReaperChronicle.MODID)
public final class ReaperChronicle {
    @Nonnull public static final String MODID = "reaperchronicle";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static IEventBus EVENT_BUS;
    public static ModContainer CONTAINER;

    public ReaperChronicle(IEventBus bus, ModContainer container) {
        EVENT_BUS = bus;
        CONTAINER = container;
        
        RCDeferredRegisters.register(bus);
    }
}
