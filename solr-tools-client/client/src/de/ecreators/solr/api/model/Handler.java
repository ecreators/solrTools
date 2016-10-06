package de.ecreators.solr.api.model;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface Handler<T> {
    
    void invoke(T e);
}
