package ua.edu.ucu.stream;

import ua.edu.ucu.function.IntBinaryOperator;
import ua.edu.ucu.function.IntConsumer;
import ua.edu.ucu.function.IntPredicate;
import ua.edu.ucu.function.IntUnaryOperator;
import ua.edu.ucu.function.IntToIntStreamFunction;

import java.util.ArrayList;

public class AsIntStream implements IntStream {

    private int[] elements;
    private boolean isUsed = false;

    private AsIntStream(int... values) {
        elements = values.clone();
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public double average() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return elementsSum()*1.0/elements.length;
    }

    @Override
    public int max() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return getExpectedValue(1);
    }

    @Override
    public int min() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return getExpectedValue(-1);
    }

    private int getExpectedValue(int expected) {
        int result = elements[0];
        for (int elem: elements) {
            if (compare(result, elem) == expected) {
                result = elem;
            }
        }
        return result;
    }

    private int compare(int x, int y) {
        if (x == y) {
            return 0;
        }
        if (x < y) {
            return 1;
        }
        return -1;
    }

    @Override
    public long count() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        long counter = 0;
        ArrayList differentValues = new ArrayList();
        for (int elem: elements) {
            if (!differentValues.contains(elem)) {
                counter++;
                differentValues.add(elem);
            }
        }
        return counter;
    }

    @Override
    public int sum() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return elementsSum();
    }

    private int elementsSum() {
        int sum = 0;
        for (int elem: elements) {
            sum += elem;
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        if (isUsed) {
            throw new IllegalStateException();
        }
        int[] filteredElements = new int[elements.length];
        int counter = 0;
        for (int elem: elements) {
            if (predicate.test(elem)) {
                filteredElements[counter] = elem;
                counter++;
            }
        }
        int[] filterElements = new int[counter];
        System.arraycopy(filteredElements, 0, filterElements, 0, counter);
        return AsIntStream.of(filterElements);
    }

    @Override
    public void forEach(IntConsumer action) {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        for (int elem: elements) {
            action.accept(elem);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        if (isUsed) {
            throw new IllegalStateException();
        }
        int[] mappedElements = new int[elements.length];
        for (int i = 0; i < elements.length; i++) {
            mappedElements[i] = mapper.apply(elements[i]);

        }
        return AsIntStream.of(mappedElements);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        if (isUsed) {
            throw new IllegalStateException();
        }
        int[] streamElements = new int[0];
        for (int elem: elements) {
            IntStream newStream = func.applyAsIntStream(elem);
            int [] newElements = newStream.toArray();
            int[] resultElements = new int[streamElements.length
                    + newElements.length];
            System.arraycopy(streamElements, 0, resultElements, 0,
                    streamElements.length);
            System.arraycopy(newElements, 0, resultElements,
                    streamElements.length, newElements.length);
            streamElements = resultElements;
        }
        return AsIntStream.of(streamElements);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        int result = identity;
        for (int elem: elements) {
            result = op.apply(result, elem);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        if (isUsed) {
            throw new IllegalStateException();
        }
        isUsed = true;
        return elements.clone();
    }

}
