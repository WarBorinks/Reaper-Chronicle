package warborinks.mods.reaperchronicle.data.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;

@FunctionalInterface
public interface WorldgenSupplier<T> {
    public T get(BootstrapContext<T> ctx);
}
