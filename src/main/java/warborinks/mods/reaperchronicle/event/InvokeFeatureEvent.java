package warborinks.mods.reaperchronicle.event;

import java.util.List;

import net.neoforged.bus.api.ICancellableEvent;
import warborinks.mods.reaperchronicle.world.reaper.attribute.ReaperAttribute;

public class InvokeFeatureEvent extends ReaperAttributeEvent implements ICancellableEvent {
    private final String name;
    private final List<Class<?>> paramTypes;
    private final Object[] args;

    public InvokeFeatureEvent(ReaperAttribute reaperAttribute, String name, List<Class<?>> paramTypes, Object... args) {
        super(reaperAttribute);
        this.name = name;
        this.paramTypes = paramTypes;
        this.args = args;
    }

    public String getName() {
        return this.name;
    }

    public List<Class<?>> getTypes() {
        return this.paramTypes;
    }

    public Object[] getArgs() {
        return this.args;
    }
}
