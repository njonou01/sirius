package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetOf<T> {

    private final String propertyName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty
    private Set<T> entities = new LinkedHashSet<>();

    public SetOf(String propertyName) {
        this.propertyName = propertyName;
    }

    public SetOf(String propertyName, Set<T> entities) {
        this.propertyName = propertyName;
        this.entities = entities;
    }

    public Set<T> get() {
        return this.entities;
    }

    public void set(Set<T> entities) {
        this.entities = entities;
    }

    public SetOf<T> entities(Set<T> entities) {
        set(entities);
        return this;
    }

    public final SetOf<T> add(final T item) {
        entities.add(item);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " " + propertyName + "='" + get() + "'" +
                "}";
    }
}
