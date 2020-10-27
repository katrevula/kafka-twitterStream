package edu.nwmissouri.dv.twitterStreaming;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaJsonSerializer implements Serializer<CustomObject> {
    @Override
    public void configure(Map<String, ?> s, boolean arg1) {
    }
    @Override
    public byte[] serialize(String arg0, CustomObject arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
    @Override
    public void close() {
    }
}
//
//public class KafkaJsonSerializer extends StdSerializer<CustomObject> implements Serializer {
//
//    public KafkaJsonSerializer() {
//        this(null);
//    }
//
//    public KafkaJsonSerializer(Class<CustomObject> t) {
//        super(t);
//    }
//
//    @Override
//    public void serialize(
//            CustomObject co, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeStringField("Name", co.getName());
//        jsonGenerator.writeStringField("Text", co.getText());
//        jsonGenerator.writeStringField("Count", String.valueOf(co.getCount()));
//        jsonGenerator.writeEndObject();
//    }
//
//    @Override
//    public void configure(Map map, boolean b) {
//
//    }
//
//    @Override
//    public byte[] serialize(String s, Object o) {
//        byte[] retVal = null;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            retVal = objectMapper.writeValueAsBytes(o);
//        } catch (Exception e) {
//           // logger.error(e.getMessage());
//        }
//        return retVal;
//    }
//
//    @Override
//    public void close() {
//
//    }
//}