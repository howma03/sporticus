package com.sporticus.services.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mark on 10/06/2017.
 */
public class DtoList<T> {

    protected Collection<T> data = new ArrayList<>();

    public DtoList() {

    }

    public DtoList(final List<T> data) {
        this.data.addAll(data);
    }

	public Collection<T> getData() {
        return data;
    }

    public void setData(final Collection<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public void add(final T object) {
        this.data.add(object);
    }

    public Integer getLength() {
        return this.data.size();
    }
}
