package ovh.paulem.simpleores.stonecutter;

import java.util.function.Function;

public record SwapMethod<A, B, C, D>(Function<A, B> a, Function<C, D> b) {
    public B makeA(A arg) {
        return a.apply(arg);
    }

    public D makeC(C arg) {
        return b.apply(arg);
    }
}
