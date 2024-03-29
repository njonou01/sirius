package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashSet;
import java.util.Set;

public class Universities {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("universities")
    private Set<University> universities = new LinkedHashSet<University>();

    public Set<University> getUniversities() {
        return universities;
    }

    public void setUniversities(Set<University> universities) {
        this.universities = universities;
    }

    public final Universities add(final University university) {
        universities.add(university);
        return this;
    }
    
    @Override
    public String toString() {
        return "Universities{" +
                "universities=" + universities +
                '}';
    }
}
