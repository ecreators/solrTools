package de.ecreators.solr.tools.api.utils;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public abstract class StrategyHandler<T, M> {
    
    protected final T target;
    
    public StrategyHandler(T target) {
        this.target = target;
    }
    
    public abstract void handle(M context);
}


