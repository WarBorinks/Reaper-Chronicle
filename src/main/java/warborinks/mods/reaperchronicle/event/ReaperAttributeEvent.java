package warborinks.mods.reaperchronicle.event;

import net.neoforged.bus.api.Event;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public abstract class ReaperAttributeEvent extends Event {
    private final ReaperAttribute reaperAttribute;

    protected ReaperAttributeEvent(ReaperAttribute reaperAttribute) {
        this.reaperAttribute = reaperAttribute;
    }

    public ReaperAttribute getReaperAttribute() {
        return this.reaperAttribute;
    }
}
