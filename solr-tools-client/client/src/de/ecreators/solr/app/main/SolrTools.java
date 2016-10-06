package de.ecreators.solr.app.main;

import de.ecreators.solr.api.model.CancelHandler;
import de.ecreators.solr.api.model.IListenerEvent;
import de.ecreators.solr.api.model.ListenerEvent;
import de.ecreators.solr.app.tools.create_collection.SolrCreateTool;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrTools implements Iterable<SolrTools.SolrTool<?>> {
    
    /**
     * Startet die Anwendung und zeigt ein Fenster.
     */
    public static void main(String[] args) {
        SolrTools application = new SolrTools();
        application.addTool("create new collection", new SolrCreateTool());
//        application.addTool("edit data-schema.xml", new SolrEditFieldsTool());
//        application.addTool("edit schema.xml", new SolrEditFieldsTool());
//        application.addTool("edit solrconfig.xml", new SolrEditConfigTool());
//        application.addTool("edit bin/solr init", new SolrCoreTool());
//        application.addTool("main in browser", new SolrWebTool());
        application.run(args);
    }
    
    ///////////////////////////////////////////////////////////
    // Hier geht's los
    ///////////////////////////////////////////////////////////
    
    private Parameters parameters;
    private final Map<String, SolrTool<?>> tools;
    private final ListenerEvent<CancelHandler<SolrTool<?>>> activeDetailsEvent;
    
    private SolrTool<?> activeDetail;
    
    public SolrTools() {
        tools = new HashMap<>();
        activeDetailsEvent = new ListenerEvent<>();
        activeDetail = null;
    }
    
    public void setActiveDetail(SolrTool<?> activeDetail) {
        this.activeDetail = activeDetail;
        activeDetailsEvent.getListeners().forEach(h -> h.invoke(new CancelHandler.CancelArgs<>(activeDetail)));
    }
    
    private void addTool(String key, SolrTool<?> tool) {
        tools.put(key, tool);
    }
    
    private void run(String[] args) {
        parameters = new Parameters(args);
        
        SolrToolWindow window = new SolrToolWindow(this);
        
        window.setPreferredSize(new Dimension(1024, 768));
        window.show(SolrToolWindow.WindowStyle.FULLSCREEN);
    }
    
    @Override
    public Iterator<SolrTool<?>> iterator() {
        return tools.values().iterator();
    }
    
    public IListenerEvent<CancelHandler<SolrTool<?>>> getActiveDetailsEvent() {
        return activeDetailsEvent;
    }
    
    public static abstract class SolrTool<TModel> {
        
        private final JPanel viewContainer;
        private final JScrollPane view;
        private final ToolModel<TModel> model;
        private final GridBagLayout layout;
        
        public SolrTool(String title, String description, ImageIcon icon, String shortText) {
            this.model = new ToolModel<>(title, description, icon, shortText);
            layout = new GridBagLayout();
            layout.columnWeights = new double[]{1};
            layout.rowWeights = new double[]{1};
            this.view = new JScrollPane(viewContainer = new JPanel(layout));
            initializeTool(layout, viewContainer);
        }
        
        protected abstract void initializeTool(GridBagLayout layout, Container containerToAddYourView);
        
        protected final GridBagLayout getLayout() {
            return layout;
        }
        
        protected final JPanel getViewContainer() {
            return viewContainer;
        }
        
        public final JScrollPane getView() {
            return view;
        }
        
        public final ToolModel<TModel> getModel() {
            return model;
        }
        
        protected final TModel getDataContextModel() {
            return model.getDataContextModel();
        }
        
        public static final class ToolModel<T> {
            
            private final String title;
            private final String description;
            private final ImageIcon icon;
            private final String shortText;
            private T dataContextModel;
    
            public ToolModel(String title, String description, ImageIcon icon, String shortText) {
                this.title = title;
                this.description = description;
                this.icon = icon;
                this.shortText = shortText;
            }
            
            public T getDataContextModel() {
                return dataContextModel;
            }
            
            public void setDataContextModel(T dataContextModel) {
                this.dataContextModel = dataContextModel;
            }
            
            public String getTitle() {
                return title;
            }
            
            public String getDescription() {
                return description;
            }
            
            public ImageIcon getIcon() {
                return icon;
            }
    
            public String getShortText() {
                return shortText;
            }
        }
    }
    
    private class Parameters {
        
        public Parameters(String[] args) {
            
        }
    }
}
