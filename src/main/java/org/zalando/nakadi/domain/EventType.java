package org.zalando.nakadi.domain;

import org.joda.time.DateTime;

public class EventType extends EventTypeBase {

    private DateTime updatedAt;
    private DateTime createdAt;

    private EventTypeSchema schema;

    public EventType(final EventTypeBase eventType, final String version, final DateTime createdAt,
                     final DateTime updatedAt) {
        super(eventType);
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;

        this.setSchema(new EventTypeSchema(eventType.getSchema(), version, updatedAt));
    }

    public EventType() {
        super();
    }

    @Override
    public EventTypeSchema getSchema() {
        return schema;
    }

    public void setSchema(final EventTypeSchema schema) {
        this.schema = schema;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(final DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public EventTypeResource asResource() {
        return new EventTypeResource(getName(), getAuthorization());
    }
}
