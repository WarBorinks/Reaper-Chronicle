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

    private static IEventBus eventBus;
    private static ModContainer modContainer;

    public ReaperChronicle(IEventBus bus, ModContainer container) {
        eventBus = bus;
        modContainer = container;
        
        RCDeferredRegisters.register(bus);
    }

    public static IEventBus getEventBus() {
        return eventBus;
    }
    
    public static ModContainer getModContainer() {
        return modContainer;
    }
}
