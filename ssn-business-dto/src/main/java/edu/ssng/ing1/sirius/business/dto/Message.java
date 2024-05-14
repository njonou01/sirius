package edu.ssng.ing1.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private int id_message;
    private int sender_id;
    private int receiver_id;
    private String message_text;
    private byte[] media;
    private Timestamp sent_at;


    public final Message build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "id_message", "sender_id", "receiver_id",
                "message_text", "sent_at");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return saveMessageStatement(preparedStatement, sender_id, receiver_id, message_text);
    }

    public Message() {
    }
    public Message(Integer sender_id, Integer receiver_id, String message_text, byte[] media) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_text = message_text;
        this.media = media;
    }

    public Message(Integer id_message, Integer sender_id, Integer receiver_id, String message_text, byte[] media,
            Timestamp sent_at) {
        this.id_message = id_message;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_text = message_text;
        this.media = media;
        this.sent_at = sent_at;
    }

    public Integer getIdMessage() {
        return this.id_message;
    }

    public void setIdMessage(Integer id_message) {
        this.id_message = id_message;
    }

    public Integer getSenderId() {
        return this.sender_id;
    }

    public void setSenderId(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public Integer getReceiverId() {
        return this.receiver_id;
    }

    public void setReceiverId(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessageText() {
        return this.message_text;
    }

    public void setMessageText(String message_text) {
        this.message_text = message_text;
    }

    public byte[] getMedia() {
        return this.media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public Timestamp getSentAt() {
        return this.sent_at;
    }

    public void setSentAt(Timestamp sent_at) {
        this.sent_at = sent_at;
    }

    public Message id_message(Integer id_message) {
        setIdMessage(id_message);
        return this;
    }

    public Message sender_id(Integer sender_id) {
        setSenderId(sender_id);
        return this;
    }

    public Message receiver_id(Integer receiver_id) {
        setReceiverId(receiver_id);
        return this;
    }

    public Message message_text(String message_text) {
        setMessageText(message_text);
        return this;
    }

    
    public Message sent_at(Timestamp sent_at) {
        setSentAt(sent_at);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id_message, message.id_message) && Objects.equals(sender_id, message.sender_id)
                && Objects.equals(receiver_id, message.receiver_id) && Objects.equals(message_text, message.message_text)
                && Objects.equals(media, message.media) && Objects.equals(sent_at, message.sent_at);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement saveMessageStatement(PreparedStatement preparedStatement,
            final Object... fieldValues)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            } else if (fieldValue instanceof Integer) {
                preparedStatement.setInt(++ix, (Integer) fieldValue);
            } else if (fieldValue instanceof Timestamp) {
                preparedStatement.setTimestamp(++ix, (Timestamp) fieldValue);
            } else {
                throw new IllegalArgumentException("Unsupported field value type: " + fieldValue.getClass().getName());
            }
        }
        preparedStatement.setTimestamp(++ix, sent_at);
        return preparedStatement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_message, sender_id, receiver_id, message_text, media, sent_at);
    }

    @Override
    public String toString() {
        return "{" +
                " id_message='" + getIdMessage() + "'" +
                ", sender_id='" + getSenderId() + "'" +
                ", receiver_id='" + getReceiverId() + "'" +
                ", message_text='" + getMessageText() + "'" +
                ", media='" + getMedia() + "'" +
                ", sent_at='" + getSentAt() + "'" +
                "}";
    }
}
