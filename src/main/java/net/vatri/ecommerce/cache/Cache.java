package net.vatri.ecommerce.cache;

import java.util.Collection;

public interface Cache {

    public Object getItem(String key, Class type);
    public Object setItem(String key, Object item);
    public void removeItem(String key);

    public Collection<Object> getList(String key, Class type);
    public Collection<Object> addItemToList(String key, Object item);
    public Collection<Object> removeItemFromList(String key, Object item);
}
