package com.sporticus.services.converters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 29/05/2017.
 */
public class Converter<F,T> {
    public List<T> convert(Iterable<F> from) {
        List<T> list = new ArrayList<>();
        if (from != null) {
            for (F ps : from) {
                list.add((T)ps);
            }
        }
        return list;
    }
}