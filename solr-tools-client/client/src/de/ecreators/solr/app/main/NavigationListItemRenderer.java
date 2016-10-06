package de.ecreators.solr.app.main;

import de.ecreators.solr.component.ToolItemUI;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class NavigationListItemRenderer implements ListCellRenderer<SolrTools.SolrTool<?>> {
    
    private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    
    @Override
    public Component getListCellRendererComponent(JList<? extends SolrTools.SolrTool<?>> list, SolrTools.SolrTool<?> value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel ui = (JLabel) defaultRenderer.getListCellRendererComponent(list, value.getModel().getTitle(), index, isSelected, cellHasFocus);
        return new ToolItemUI(value, isSelected, ui.getBackground(), ui.getFont());
    }
}
