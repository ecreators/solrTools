package de.ecreators.solr.tools.main;

import de.ecreators.solr.tools.api.model.CancelHandler;
import de.ecreators.solr.tools.component.CustomSplitPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
        
        JNavigationPanel left = new JNavigationPanel(tools);
        JDetailsPanel right = new JDetailsPanel(tools);
        getContentPane().add(new CustomSplitPane(left, right), BorderLayout.CENTER);
        
        // Den ersten Zustand abrufen: erst jetzt sind die Listener an Tools!
        left.notifySelectedTool();
    }
    
    public void setWindowsStyle(WindowStyle windowStyle) {
        switch (windowStyle) {
            case FULLSCREEN:
                setLocation(new Point(0, 0));
                setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
                break;
        }
    }
    
    public void show(WindowStyle windowStyle) {
        pack();
        setLocationRelativeTo(null);
        setWindowsStyle(windowStyle);
        setVisible(true);
    }
    
    public enum WindowStyle {
        FULLSCREEN
    }
    
    private static class JNavigationPanel extends JScrollPane {
        
        private final SolrTools tools;
        private final JList<SolrTools.SolrTool<?>> list;
        
        public JNavigationPanel(SolrTools tools) {
            super(new JList<>(toList(tools)), VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
            setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.GRAY));
            
            this.tools = tools;
            Component scrollContent = getViewport().getComponent(0);
            //noinspection unchecked
            this.list = (JList<SolrTools.SolrTool<?>>) scrollContent;
            list.setCellRenderer(new NavigationListItemRenderer());
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()) {
                        notifySelectedTool();
                    }
                }
            });
            
            // Die Liste soll bedienbar sein über die Tastatur:
            // - focus Liste
            // - erstes Tool vorabwählen
            
            list.requestFocusInWindow();
            list.setSelectedIndex(0);
        }
        
        private static Vector<SolrTools.SolrTool<?>> toList(SolrTools tools) {
            Vector<SolrTools.SolrTool<?>> list = new Vector<>();
            for (SolrTools.SolrTool<?> tool : tools) {
                list.add(tool);
            }
            return list;
        }
    
        public void notifySelectedTool() {
            tools.setActiveDetail(list.getSelectedValue());
        }
    }
    
    private class JDetailsPanel extends JScrollPane {
        
        private final SolrTools tools;
        private final Container presenterPanel;
        
        public JDetailsPanel(SolrTools tools) {
            super(new JPanel(new BorderLayout()), VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
            setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
            this.tools = tools;
            this.presenterPanel = (Container) getViewport().getComponent(0);
            tools.getActiveDetailsEvent().addListener(this::onActiveDetailsChanged);
        }
        
        private void onActiveDetailsChanged(CancelHandler.CancelArgs<SolrTools.SolrTool<?>> e) {
            presenterPanel.removeAll();
            SolrTools.SolrTool<?> dataContext = e.getDataContext();
            if(dataContext != null) {
                JScrollPane view = dataContext.getView();
                view.setBorder(null);
                presenterPanel.add(view);
            }
            presenterPanel.setPreferredSize(null);
            presenterPanel.revalidate();
            presenterPanel.repaint();
        }
    }
}
