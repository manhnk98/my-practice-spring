package com.nkm.mypracticespring.common.json_deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class SafeBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            return p.getBooleanValue();
        } catch (Exception e) {
            return false;
        }
    }
}
