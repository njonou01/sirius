package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashSet;
import java.util.Set;

public class Messages {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("messages")
    private Set<Message> messages = new LinkedHashSet<Message>();

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public final Messages add(final Message message) {
        messages.add(message);
        return this;
    }
    
    @Override
    public String toString() {
        return "Messages{" +
                "messages=" + messages +
                '}';
    }
}
