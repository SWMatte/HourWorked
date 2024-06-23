package assegnazione.ore;

public class Utility {
        public static <T> boolean isNotNullOrEmpty(T t) {
        if (t == null) {
            return false;
        }
        if (t instanceof String) {
            return !((String) t).isEmpty();
        }
        return true;
    }
}
