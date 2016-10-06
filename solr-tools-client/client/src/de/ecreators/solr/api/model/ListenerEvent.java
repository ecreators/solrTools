package de.ecreators.solr.api.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class ListenerEvent<T> implements IListenerEvent<T> {
    
    private final Collection<T> listeners;
    private ListenerEvent<Handler<T>> listenersAddedEvent;
    private ListenerEvent<Handler<T>> listenersRemovedEvent;
    private final Collection<ListenerEvent<T>> delegates;
    private boolean enabled;
    
    public ListenerEvent() {
        listeners = new HashSet<T>();
        delegates = new HashSet<>();
        enabled = true;
    }
    
    public Collection<T> getListeners() {
        if(!enabled) {
            return Collections.emptySet();
        }
        return new HashSet<>(listeners);
    }
    
    @Override
    public void addDelegate(ListenerEvent<T> event) {
        if(delegates.add(event)) {
            if(event.listenersAddedEvent == null) {
                event.listenersAddedEvent = new ListenerEvent<>();
            }
            event.listenersAddedEvent.addListener(this::addListener);
            event.listenersRemovedEvent.addListener(this::removeListener);
        }
    }
    
    @Override
    public void removeDelegate(ListenerEvent<T> event) {
        if(delegates.remove(event)) {
            if(event.listenersRemovedEvent == null) {
                event.listenersRemovedEvent = new ListenerEvent<>();
            }
            event.listenersAddedEvent.removeListener(this::addListener);
            event.listenersRemovedEvent.removeListener(this::removeListener);
        }
    }
    
    @Override
    public void addListener(T l) {
        if(listeners.add(l)) {
            if(listenersAddedEvent == null) {
                listenersAddedEvent = new ListenerEvent<>();
            }
            listenersAddedEvent.getListeners().forEach(h -> h.invoke(l));
        }
    }
    
    @Override
    public void removeListener(T l) {
        if(listeners.remove(l)) {
            if(listenersRemovedEvent == null) {
                listenersRemovedEvent = new ListenerEvent<>();
            }
            listenersRemovedEvent.getListeners().forEach(h -> h.invoke(l));
        }
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public void clear() {
        boolean oldEnabled = enabled;
        enabled = true;
        getListeners().forEach(this::removeListener);
        enabled = oldEnabled;
    }
}