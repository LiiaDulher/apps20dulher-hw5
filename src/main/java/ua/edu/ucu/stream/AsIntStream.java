package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsIntStream implements IntStream {

    private List<Integer> elements;
    private boolean start = false;
    private boolean used = false;

    private AsIntStream(int... values) {
        elements = new ArrayList<>();
        for (int value: values){
            elements.add(value);
        }
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        return elementsSum()*1.0/elements.size();
    }

    @Override
    public Integer max() {
        return getExpectedValue(1);
    }

    @Override
    public Integer min() {
        return getExpectedValue(-1);
    }

    private Integer getExpectedValue(int expected) {
        int result = elements.get(0);
        for (Integer elem: elements){
            if (compare(result, elem) == expected){
                result = elem;
            }
        }
        return result;
    }

    private Integer compare(int x, int y) {
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
        for (Integer elem: elements){
            if (!differentValues.contains(elem)){
                counter++;
                differentValues.add(elem);
            }
        }
        return counter;
    }

    @Override
    public Integer sum() {
        return elementsSum();
    }

    private Integer elementsSum(){
        int sum = 0;
        for (Integer elem: elements) {
            sum += elem;
        }
        return sum;
    }

    private int[] listToIntArray(List<Integer> integerList){
        int[] result = new int[integerList.size()];
        for (int i = 0; i< integerList.size();i++) {
            result[i] = (int) integerList.get(i);
        }
        return result;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        List filteredElements = new ArrayList();
        for (Integer elem: elements) {
            if (predicate.test(elem)) {
                filteredElements.add(elem);
            }
        }
        return AsIntStream.of(listToIntArray(filteredElements));
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer elem: elements){
            action.accept(elem);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        List mappedElements = new ArrayList();
        for (Integer elem: elements){
            mappedElements.add(mapper.apply(elem));

        }
        return AsIntStream.of(listToIntArray(mappedElements));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        List streamElements = new ArrayList();
        for (Integer elem: elements) {
            IntStream newStream = func.applyAsIntStream(elem);
            streamElements.addAll(Arrays.asList(newStream.toArray()));
        }
        return AsIntStream.of(listToIntArray(streamElements));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        for (Integer elem: elements) {
            result = op.apply(result, elem);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        return listToIntArray(elements);
    }

}
