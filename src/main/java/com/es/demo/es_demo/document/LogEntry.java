package com.es.demo.es_demo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(indexName = "logs")
public class LogEntry implements Serializable {
    @Id
    private String id;
    private String message;
    private LocalDateTime timestamp;

    public LogEntry() {
    }

    private LogEntry(Builder builder) {
        setId(builder.id);
        setMessage(builder.message);
        setTimestamp(builder.timestamp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static final class Builder {
        private String id;
        private String message;
        private LocalDateTime timestamp;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public Builder timestamp(LocalDateTime val) {
            timestamp = val;
            return this;
        }

        public LogEntry build() {
            return new LogEntry(this);
        }
    }
}
