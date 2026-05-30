package warborinks.mods.reaperchronicle.world.reaper.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import warborinks.mods.reaperchronicle.world.reaper.attribute.features.FeatureInterface;

public class ReaperAttributeBehaviour {
    private final Map<String, Map<List<Class<?>>, FeatureInterface>> featureMap = new ConcurrentHashMap<>();

    protected ReaperAttributeBehaviour() {}

    public void add(@Nonnull String name,
        @Nonnull FeatureInterface feature, Class<?>... paramTypes) {
        this.featureMap.computeIfAbsent(name, k -> new ConcurrentHashMap<>())
        .merge(
            List.of(paramTypes), feature,
            (o, n) -> {
                throw new TheSameFeatureException(name, paramTypes);
            }
        );
    }

    public Result invoke(String name, Object... args) throws NoSuchFeatureException, Throwable {
        Map<List<Class<?>>, FeatureInterface> features = this.featureMap.get(name);
        if (features == null) {
            throw new NoSuchFeatureException("Feature not found: " + name);
        }

        List<Class<?>> argTypes = Arrays.stream(args)
            .<Class<?>>map(obj -> obj == null ? null : obj.getClass())
            .toList();
        
        List<Map.Entry<List<Class<?>>, FeatureInterface>> candidates = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, FeatureInterface> feature : features.entrySet()) {
            if (feature.getKey().size() == args.length) {
                candidates.add(feature);
            }
        }

        if (candidates.isEmpty()) {
            throw new NoSuchFeatureException(
                "No overload of '" + name + "' takes " + args.length + " argument(s)"
            );
        }

        List<Map.Entry<List<Class<?>>, FeatureInterface>> compatible = candidates.stream()
            .filter(e -> this.isCompatible(e.getKey(), argTypes))
            .toList();

        if (compatible.isEmpty()) {
            throw new IllegalArgumentException(
                "Argument types " + argTypes + " do not match any overload of '" + name + "'"
            );
        }

        Map.Entry<List<Class<?>>, FeatureInterface> best = this.findMostSpecific(compatible, name);

        Args argWrapper = Args.of(args);
        return best.getValue().invoke(argWrapper);
    }

    public boolean findFeature(@Nonnull String name) {
        return this.featureMap.containsKey(name);
    }

    private boolean isCompatible(List<Class<?>> paramTypes, List<Class<?>> argTypes) {
        for (int i = 0; i < paramTypes.size(); i++) {
            Class<?> paramType = paramTypes.get(i);
            Class<?> argType = argTypes.get(i);
            if (argType == null) {
                if (paramType.isPrimitive()) {
                    return false;
                }
            } else {
                if (!isAssignable(paramType, argType)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAssignable(Class<?> paramType, Class<?> argType) {
        paramType = boxed(paramType);
        argType = boxed(argType);
        return paramType.isAssignableFrom(argType);
    }

    private Map.Entry<List<Class<?>>, FeatureInterface> findMostSpecific(
        List<Map.Entry<List<Class<?>>, FeatureInterface>> candidates, String name) {
        if (candidates.size() == 1) {
            return candidates.getFirst();
        }

        List<Map.Entry<List<Class<?>>, FeatureInterface>> mostSpecific = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, FeatureInterface> candidate : candidates) {
            boolean isMostSpecific = true;
            for (Map.Entry<List<Class<?>>, FeatureInterface> other : candidates) {
                if (candidate == other) {
                    continue;
                } else if (this.isMoreSpecific(other.getKey(), candidate.getKey())) {
                    isMostSpecific = false;
                    break;
                }
            }

            if (isMostSpecific) {
                mostSpecific.add(candidate);
            }
        }

        if (mostSpecific.size() == 1) {
            return mostSpecific.getFirst();
        } else {
            throw new AmbiguousFeatureException(
                "Ambiguous feature overload for '" + name + "'. " + "Candidates: " + mostSpecific
            );
        }
    }

    private boolean isMoreSpecific(List<Class<?>> types1, List<Class<?>> types2) {
        for (int i = 0; i < types1.size(); i++) {
            if (!types2.get(i).isAssignableFrom(types1.get(i))) {
                return false;
            }
        }

        return true;
    }

    private static Class<?> boxed(Class<?> primitive) {
        return switch (primitive.getName()) {
            case "boolean" -> Boolean.class;
            case "byte"    -> Byte.class;
            case "char"    -> Character.class;
            case "short"   -> Short.class;
            case "int"     -> Integer.class;
            case "long"    -> Long.class;
            case "float"   -> Float.class;
            case "double"  -> Double.class;
            case "void"    -> Void.class;
            default        -> primitive;
        };
    }

    public static final class Args {
        private final List<Object> values;

        private Args(List<Object> values) {
            this.values = values;
        }

        public static Args of(Object... values) {
            return new Args(List.of(values));
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

            if (boxed(type).isInstance(value)) {
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
    }
    public static final class Result {
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

            if (boxed(type).isInstance(this.value)) {
                return type.cast(this.value);
            } else {
                throw new ClassCastException("Result is " + value.getClass() + " but requested as " + type);
            }
        }

        public boolean isVoid() {
            return this.isVoid;
        }

        public static final class VoidResultException extends RuntimeException {
            public VoidResultException() {
                super("The value of the result is void, please pass in Void.class");
            }
        }
    }

    public static final class TheSameFeatureException extends RuntimeException {
        public TheSameFeatureException(String name, Class<?>... paramTypes) {
            super(
                "The feature " + name + "(" +
                Arrays.toString(paramTypes).replaceAll("^\\[(.*)]$", "$1")
                + ") has the same one"
            );
        }
    }
    public static final class AmbiguousFeatureException extends RuntimeException {
        public AmbiguousFeatureException(String message) {
            super(message);
        }
    }
    public static final class NoSuchFeatureException extends NoSuchMethodException {
        public NoSuchFeatureException(String message) {
            super(message);
        }
    }
}
