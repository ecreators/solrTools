import model.CancelHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrToolWindow extends JFrame {
    
    public SolrToolWindow(SolrTools tools) {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // TODO add tools as Collection on left
        // TODO each clickable to show its content on right
        // TODO add toolbar to exit, start, restart stop solr application
        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JNavigationPanel(tools), new JDetailsPanel(tools)),
                             BorderLayout.CENTER);
    }
    
    private static class JNavigationPanel extends JScrollPane {
        
        private final SolrTools tools;
        private final JList<SolrTools.SolrTool<?>> list;
        
        public JNavigationPanel(SolrTools tools) {
            super(new JList<SolrTools.SolrTool<?>>(toList(tools)), VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
            this.tools = tools;
            Component scrollContent = getViewport().getComponent(0);
            //noinspection unchecked
            this.list = (JList<SolrTools.SolrTool<?>>) scrollContent;
            list.setCellRenderer(new ListCellRenderer<SolrTools.SolrTool<?>>() {
                
                private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
                
                @Override
                public Component getListCellRendererComponent(JList<? extends SolrTools.SolrTool<?>> list, SolrTools.SolrTool<?> value, int index, boolean isSelected, boolean cellHasFocus) {
                    return defaultRenderer.getListCellRendererComponent(list, value.getModel().getTitle(), index, isSelected, cellHasFocus);
                }
            });
        }
        
        private static Vector<SolrTools.SolrTool<?>> toList(SolrTools tools) {
            Vector<SolrTools.SolrTool<?>> list = new Vector<>();
            for (SolrTools.SolrTool<?> tool : tools) {
                list.add(tool);
            }
            return list;
        }
    }
    
    private class JDetailsPanel extends JScrollPane {
        
        private final SolrTools tools;
        private final Container presenterPanel;
        
        public JDetailsPanel(SolrTools tools) {
            super(new JPanel(new BorderLayout()), VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
            this.tools = tools;
            this.presenterPanel = (Container) getViewport().getComponent(0);
            tools.getActiveDetailsEvent().addListener(this::onActiveDetailsChanged);
        }
        
        private void onActiveDetailsChanged(CancelHandler.CancelArgs<SolrTools.SolrTool<?>> e) {
            presenterPanel.removeAll();
            presenterPanel.add(e.getDataContext().getView());
            presenterPanel.setPreferredSize(null);
            presenterPanel.revalidate();
            presenterPanel.invalidate();
        }
    }
}
