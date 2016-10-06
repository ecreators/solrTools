package de.ecreators.solr.tools.api.model;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface IListenerEvent<T> {
    
    void addDelegate(ListenerEvent<T> event);
    
    void removeDelegate(ListenerEvent<T> event);
    
    void addListener(T l);
    
    void removeListener(T l);
    
    void setEnabled(boolean enabled);
    
    void clear();
}
