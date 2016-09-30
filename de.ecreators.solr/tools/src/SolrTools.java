import model.CancelHandler;
import model.IListenerEvent;
import model.ListenerEvent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrTools implements Iterable<SolrTools.SolrTool<?>> {
    
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
    
    public static void main(String[] args) {
        SolrTools application = new SolrTools();
        application.addTool("create new collection", new SolrCreateTool());
//        application.addTool("edit data-schema.xml", new SolrEditFieldsTool());
//        application.addTool("edit schema.xml", new SolrEditFieldsTool());
//        application.addTool("edit solrconfig.xml", new SolrEditConfigTool());
//        application.addTool("edit bin/solr init", new SolrCoreTool());
//        application.addTool("view in browser", new SolrWebTool());
        application.run(args);
    }
    
    private void addTool(String key, SolrTool<?> tool) {
        tools.put(key, tool);
    }
    
    private void run(String[] args) {
        parameters = new Parameters(args);
        
        JFrame window = new SolrToolWindow(this);
        
        showCentered(window);
    }
    
    private void showCentered(JFrame window) {
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
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
        
        public SolrTool(String title, String description, ImageIcon icon) {
            this.model = new ToolModel<>(title, description, icon);
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
            private T dataContextModel;
            
            public ToolModel(String title, String description, ImageIcon icon) {
                this.title = title;
                this.description = description;
                this.icon = icon;
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
        }
    }
    
    private class Parameters {
        
        public Parameters(String[] args) {
            
        }
    }
}
