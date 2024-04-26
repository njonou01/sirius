package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class BeFriends {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("befriends")
    private Set<BeFriend> befriends = new LinkedHashSet<BeFriend>();

    public BeFriends() {
    }

    public BeFriends(Set<BeFriend> befriends) {
        this.befriends = befriends;
    }

    public Set<BeFriend> getBefriends() {
        return this.befriends;
    }

    public void setBefriends(Set<BeFriend> befriends) {
        this.befriends = befriends;
    }

    public BeFriends befriends(Set<BeFriend> befriends) {
        setBefriends(befriends);
        return this;
    }
    public final BeFriends add(final BeFriend university) {
        befriends.add(university);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " befriends='" + getBefriends() + "'" +
                "}";
    }

}
