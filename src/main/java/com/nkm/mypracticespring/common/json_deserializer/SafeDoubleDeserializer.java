package com.nkm.mypracticespring.common.json_deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class SafeDoubleDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            return p.getDoubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
