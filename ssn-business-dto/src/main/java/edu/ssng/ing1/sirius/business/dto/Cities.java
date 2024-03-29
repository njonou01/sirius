package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.ssng.ing1.City;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Cities {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cities")
    private Set<City> cities = new LinkedHashSet<City>();

    public Set<City> getStudents() {
        return cities;
    }

    public void setStudents(Set<City> cities) {
        this.cities = cities;
    }

    public final Cities add(final City city) {
        cities.add(city);
        return this;
    }

    public Map<Integer, City> toMap() {
        Map<Integer, City> map = new HashMap<>();
        for (City city : cities) {
            map.put(city.getZipcode(), city);
        }
        return map;
    }

    @Override
    public String toString() {
        return "Cities{" +
                "cities=" + cities +
                '}';
    }
}
