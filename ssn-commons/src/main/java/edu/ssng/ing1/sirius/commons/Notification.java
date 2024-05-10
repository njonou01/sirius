package edu.ssng.ing1.sirius.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


@JsonRootName(value = "Notification")
public class Notification {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;
    String order;
    String body;


    public Notification(){

    }
    public Notification(String message){
        this.message=message;
    }
    
    public Notification(String message,String order, String body){
        this.message=message;
        this.order=order;
        this.body=body;

    }




    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("order")
    public String getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(String order) {
        this.order = order;
    }
    
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message= " + message +
                ", order='" + order + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
    
}
