package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Activites {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("activites")
    private  Set<Activite> activites = new LinkedHashSet<Activite>();

    public Set<Activite> getActivites() {
        return activites;
    }
    
    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
    }

    public final Activites add (final Activite activite) {
        activites.add(activite);
        return this;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "activite=" + activites +
                '}';
    }
}
