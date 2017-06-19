package net.vatri.ecommerce.cache;

import java.util.Collection;

public interface Cache {

    public Object getItem(String key, Class type);
    public void addItem(String key, Object item);
    public void removeItem(String key, Object obj);

    public Collection<Object> getList(String key, Class type);
    public void addItemToList(String key, Object item);
    public void removeItemFromList(String key, Object item);
}
