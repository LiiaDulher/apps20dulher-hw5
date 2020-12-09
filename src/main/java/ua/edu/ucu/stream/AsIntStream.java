package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.*;

public class AsIntStream implements IntStream {

    private int[] elements;
    private boolean start = false;
    private boolean used = false;

    private AsIntStream(int... values) {
        elements = values.clone();
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return elementsSum()*1.0/elements.length;
    }

    @Override
    public Integer max() {
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return getExpectedValue(1);
    }

    @Override
    public Integer min() {
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return getExpectedValue(-1);
    }

    private Integer getExpectedValue(int expected) {
        int result = elements[0];
        for (int elem: elements){
            if (compare(result, elem) == expected){
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
        long counter = 0;
        List differentValues = new ArrayList();
        for (int elem: elements){
            if (!differentValues.contains(elem)){
                counter++;
                differentValues.add(elem);
            }
        }
        return counter;
    }

    @Override
    public Integer sum() {
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }
        return elementsSum();
    }

    private Integer elementsSum(){
        int sum = 0;
        for (int elem: elements) {
            sum += elem;
        }
        return sum;
    }

    private int[] listToIntArray(List<Integer> integerList){
        int[] result = new int[integerList.size()];
        for (int i = 0; i< integerList.size(); i++) {
            result[i] = (int) integerList.get(i);
        }
        return result;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List filteredElements = new ArrayList();
        for (int elem: elements) {
            if (predicate.test(elem)) {
                filteredElements.add(elem);
            }
        }
        return AsIntStream.of(listToIntArray(filteredElements));
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int elem: elements){
            action.accept(elem);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int[] mappedElements = new int[elements.length];
        for (int i = 0; i < elements.length; i++){
            mappedElements[i] = mapper.apply(elements[i]);

        }
        return AsIntStream.of(mappedElements);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List streamElements = new ArrayList();
        for (int elem: elements) {
            IntStream newStream = func.applyAsIntStream(elem);
            streamElements.addAll(Arrays.asList(newStream.toArray()));
        }
        return AsIntStream.of(listToIntArray(streamElements));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        for (int elem: elements) {
            result = op.apply(result, elem);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        return elements.clone();
    }

}
