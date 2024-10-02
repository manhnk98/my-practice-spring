package com.nkm.mypracticespring.common.json_deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class SafeIntDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) {
        try {
            return p.getIntValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
