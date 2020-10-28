package edu.nwmissouri.dv.twitterStreaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class KafkaJsonDeserializer implements Deserializer<CustomObject> {
    @Override
    public void configure(Map<String, ?> s, boolean arg1) {
    }

    @Override
    public CustomObject deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        CustomObject customObject = null;
        try {
            customObject = mapper.readValue(bytes, CustomObject.class);
        } catch (Exception e) {

        }
        return customObject;
    }

    @Override
    public void close() {

    }
}