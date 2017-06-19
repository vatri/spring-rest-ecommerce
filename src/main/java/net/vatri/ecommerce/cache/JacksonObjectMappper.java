package net.vatri.ecommerce.cache;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMappper implements ObjectMapper{

    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public JacksonObjectMappper(com.fasterxml.jackson.databind.ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public String writeValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e){
            return "";
        }
    }

    @Override
    public Object readValue(String json, Class type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e){
            return null;
        }
    }
}
