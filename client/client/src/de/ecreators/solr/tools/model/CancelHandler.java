package de.ecreators.solr.tools.model;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public interface CancelHandler<T> extends Handler<CancelHandler.CancelArgs<T>> {
    
    class CancelArgs<T> {
    
        private final T dataContext;
        private boolean cancelled;
        private boolean handelled;
    
        public CancelArgs(T dataContext) {
            this.dataContext = dataContext;
        }
    
        public T getDataContext() {
            return dataContext;
        }
        
        public void handled() {
            handelled = true;
        }
        
        public void cancel() {
            cancelled = true;
        }
    
        public boolean isCancelled() {
            return cancelled;
        }
    
        public boolean isHandelled() {
            return handelled;
        }
    
        public void setHandelled(boolean handelled) {
            this.handelled = handelled;
        }
    }
}
