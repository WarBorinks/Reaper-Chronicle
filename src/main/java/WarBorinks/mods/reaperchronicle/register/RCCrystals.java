package warborinks.mods.reaperchronicle.register;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import warborinks.mods.reaperchronicle.crystal.Crystal;

public class RCCrystals {
    private final DeferredRegister<Crystal> CRYSTAL;

    public final DeferredHolder<Crystal, Crystal> EMPTY_CRYSTAL;

    RCCrystals(DeferredRegister<Crystal> crystal) {
        this.CRYSTAL = crystal;

        this.EMPTY_CRYSTAL = this.CRYSTAL.register(
            "empty_crystal",
            () -> new Crystal(() -> RCDeferredRegisters.ATTRIBUTES.EMPTY_ATTRIBUTE.get())
        );
    }
}
