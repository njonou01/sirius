package edu.ssng.ing1.sirius.commons;

import java.util.HashMap;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

@JsonRootName(value = "SomeInfo")
public class SomeInfo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String info;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> ipAdress;

    private HashMap<String,String> mapInfo;



    public SomeInfo(){


    }


    public SomeInfo(String info,Set<String> ipAdress,HashMap<String,String> mapInfo){
        this.info=info;
        this.ipAdress=ipAdress;

    }




    @JsonSetter("info")
    public String getInfo() {
        return info;
    }

    @JsonSetter("ipAdress")
    public Set<String> getIpAdress() {
        return ipAdress;
    }

    @JsonSetter("mapInfo")
    public HashMap<String, String> getMapInfo() {
        return mapInfo;
    }

    @JsonSetter("mapInfo")
    public void setMapInfo(HashMap<String, String> mapInfo) {
        this.mapInfo = mapInfo;
    }


    @JsonSetter("info")    
    public void setInfo(String info) {
        this.info = info;
    }

    @JsonSetter("ipAdress")
    public void setIpAdress(Set<String> ipAdress) {
        this.ipAdress = ipAdress;
    }



    @Override
    public String toString() {
        return "Request{" +
                "info=" + info +
                ", ipAdress='" + ipAdress + '\'' +
                // ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
