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

//
//public class KafkaJsonDeserializer extends StdDeserializer<CustomObject> implements Deserializer{
//
//    private Class<CustomObject> type;
//    public KafkaJsonDeserializer() {
//        this(null);
//    }
//
//    public KafkaJsonDeserializer(Class<CustomObject> vc) {
//        super(vc);
//    }
//
//    @Override
//    public CustomObject deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
//        CustomObject car = new CustomObject();
//        ObjectCodec codec = parser.getCodec();
//        JsonNode node = codec.readTree(parser);
//
//        // try catch block
//        JsonNode name = node.get("Name");
//        String color = name.asText();
//        car.setName(color);
//        JsonNode Text = node.get("Text");
//        String text = Text.asText();
//        car.setText(text);
//        JsonNode count = node.get("Count");
//        String ctr = count.asText();
//        car.setCount(Integer.parseInt(ctr));
//        return car;
//    }
//
//    @Override
//    public void configure(Map map, boolean b) {
//
//    }
//
//    @Override
//    public Object deserialize(String s, byte[] bytes) {
//        ObjectMapper mapper = new ObjectMapper();
//        CustomObject obj = null;
//        try {
//            obj = mapper.readValue(bytes, type);
//        } catch (Exception e) {
//
//           // logger.error(e.getMessage());
//        }
//        return obj;
//    }
//
//    @Override
//    public void close() {
//
//    }
//}