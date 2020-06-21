package ez.android.common.util;

import java.util.Collection;

/**
 *
 */
public class MathUtil {

    /**
     *
     * @param values
     * @return
     */
    public static int sum(Integer...values) {
        int sum = 0;
        for (int value : values) {
            sum += value;
        }
        return sum;
    }

    /**
     *
     * @param values
     * @return
     */
    public static long sum(Long...values) {
        long sum = 0;
        for (long value : values) {
            sum += value;
        }
        return sum;
    }

    /**
     *
     * @param values
     * @return
     */
    public static double sum(Double...values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum;
    }

    /**
     *
     * @param values
     * @return
     */
    public static float sum(Float...values) {
        float sum = 0;
        for (float value : values) {
            sum += value;
        }
        return sum;
    }

    /**
     *
     * @param valueList
     * @return
     */
    public static Double sum(Collection<Double> valueList) {
        return sum(valueList.toArray(new Double[valueList.size()]));
    }
//
//    public static Number sum(Number...numbers) {
//        Number sum = 0;
//        for (Number value : numbers) {
//            sum = add(sum, value);
//        }
//        return sum;
//    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static Number add(Number a, Number b) {
        if(a instanceof Double || b instanceof Double) {
            return a.doubleValue() + b.doubleValue();
        } else if(a instanceof Float || b instanceof Float) {
            return a.floatValue() + b.floatValue();
        } else if(a instanceof Long || b instanceof Long) {
            return a.longValue() + b.longValue();
        } else {
            return a.intValue() + b.intValue();
        }
    }

    /**
     *
     * @param values
     * @return
     */
    public static double avg(Integer...values) {
        if(values.length == 0) return 0;
        return 1.0 * sum(values) / values.length;
    }

    /**
     *
     * @param values
     * @return
     */
    public static double avg(Long...values) {
        if(values.length == 0) return 0;
        return 1.0 * sum(values) / values.length;
    }

    /**
     *
     * @param values
     * @return
     */
    public static double avg(Double...values) {
        if(values.length == 0) return 0;
        return sum(values) / values.length;
    }

    /**
     *
     * @param values
     * @return
     */
    public static double avg(Float...values) {
        if(values.length == 0) return 0;
        return sum(values) / values.length;
    }

//    public static double avg(Number...numbers) {
//        return sum(numbers).doubleValue() / numbers.length;
//    }

//    public static double avg(Collection<Number> valueList) {
//        return sum(valueList).doubleValue() / valueList.size();
//    }

    /**
     *
     * @param valueList
     * @return
     */
    public static double avg(Collection<Double> valueList) {
        if(valueList.isEmpty()) return 0;
        return sum(valueList) / valueList.size();
    }
}
