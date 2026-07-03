package warborinks.mods.reaperchronicle.util;

import java.util.List;

import javax.annotation.Nonnull;

import warborinks.mods.reaperchronicle.RCUtil;

public final class Args {
    private final List<Object> values;
    
    private Args(List<Object> values) {
        this.values = values;
    }

    public static Args of(Object... values) {
        return new Args(List.of(values));
    }

    public static Args of(Args args) {
        return new Args(args.getValues());
    }
    
    public <T> T get(int index, @Nonnull Class<T> type) {
        Object value = this.values.get(index);
        if (value == null) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("Cannot assign null to primitive type" + type);
            } else {
                return null;
            }
        }

        if (RCUtil.boxed(type).isInstance(value)) {
            return type.cast(value);
        } else {
            throw new ClassCastException("Parameter at index " + index + " is " + value.getClass() +
                " but requested as " + type);
        }
    }

    public List<Object> getValues() {
        return this.values;
    }
    public List<Object> getValues(int index) {
        return this.values.subList(index, this.values.size());
    }

    public int size() {
        return this.values.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Args args) {
            return args.values.equals(this.values);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.values.toString();
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }
}
