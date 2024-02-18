package cc.hamarr.utils;

public final class ObjectUtils {

    private ObjectUtils(){}

    public static void assertNonNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg + " is null");
        }
    }
}
