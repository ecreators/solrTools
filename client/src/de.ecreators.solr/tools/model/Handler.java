package de.ecreators.solr.tools.model;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface Handler<T> {
    
    void invoke(T e);
}
