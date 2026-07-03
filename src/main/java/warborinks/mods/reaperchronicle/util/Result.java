package warborinks.mods.reaperchronicle.util;

import javax.annotation.Nonnull;

import warborinks.mods.reaperchronicle.RCUtil;

public final class Result {
    private final Object value;
    private final boolean isVoid;

    private Result(Object value, boolean isVoid) {
        this.value = value;
        this.isVoid = isVoid;
    }

    public static Result of(Object value) {
        return new Result(value, false);
    }

    public static Result empty() {
        return new Result(null, true);
    }
    
    public <T> T get(@Nonnull Class<T> type) {
        if (this.isVoid() && type != Void.class) {
            throw new VoidResultException();
        }

        if (this.value == null) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("Result is null, cannot be cast to primitive " + type);
            } else {
                return null;
            }
        }
        
        if (RCUtil.boxed(type).isInstance(this.value)) {
            return type.cast(this.value);
        } else {
            throw new ClassCastException("Result is " + value.getClass() + " but requested as " + type);
        }
    }

    public boolean isVoid() {
        return this.isVoid;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof Result result) {
            return result.value.equals(this.value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    public static final class VoidResultException extends RuntimeException {
        public VoidResultException() {
            super("The value of the result is void, please pass in Void.class");
        }
    }
}
