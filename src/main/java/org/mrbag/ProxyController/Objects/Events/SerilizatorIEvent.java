package org.mrbag.ProxyController.Objects.Events;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class SerilizatorIEvent extends JsonSerializer<IEvent> {

	@Override
	public void serialize(IEvent value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("type", value.getClass().getSimpleName());
		gen.writeStringField("data", value.presnt());
		gen.writeEndObject();
	}

}
