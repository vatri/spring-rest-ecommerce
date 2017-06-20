package net.vatri.ecommerce.cache;

public interface ObjectMapper {
    public String writeValueAsString(Object obj);
    public Object readValue(String json, Class type);
}
