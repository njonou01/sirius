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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id_message, message.id_message) && Objects.equals(sender_id, message.sender_id)
                && Objects.equals(receiver_id, message.receiver_id)
                && Objects.equals(message_text, message.message_text)
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



    public int getId_message() {
        return this.id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public int getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return this.receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage_text() {
        return this.message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public byte[] getMedia() {
        return this.media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public Timestamp getSent_at() {
        return this.sent_at;
    }

    public void setSent_at(Timestamp sent_at) {
        this.sent_at = sent_at;
    }

    public Message id_message(int id_message) {
        setId_message(id_message);
        return this;
    }

    public Message sender_id(int sender_id) {
        setSender_id(sender_id);
        return this;
    }

    public Message receiver_id(int receiver_id) {
        setReceiver_id(receiver_id);
        return this;
    }

    public Message message_text(String message_text) {
        setMessage_text(message_text);
        return this;
    }

    public Message media(byte[] media) {
        setMedia(media);
        return this;
    }

    public Message sent_at(Timestamp sent_at) {
        setSent_at(sent_at);
        return this;
    }
    @Override
    public String toString() {
        return "{" +
            " id_message='" + getId_message() + "'" +
            ", sender_id='" + getSender_id() + "'" +
            ", receiver_id='" + getReceiver_id() + "'" +
            ", message_text='" + getMessage_text() + "'" +
            ", media='" + getMedia() + "'" +
            ", sent_at='" + getSent_at() + "'" +
            "}";
    }
}