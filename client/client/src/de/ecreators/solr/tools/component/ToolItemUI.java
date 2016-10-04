package de.ecreators.solr.tools.component;

import de.ecreators.solr.tools.api.utils.UIHelper;
import de.ecreators.solr.tools.main.SolrTools;

import javax.swing.*;
import java.awt.*;

import static de.ecreators.solr.tools.component.CustomSplitPane.gbc;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class ToolItemUI extends JPanel {
    
    private final SolrTools.SolrTool<?> model;
    private final ListSelectionHandler listSelectionHandler;
    
    public ToolItemUI(SolrTools.SolrTool<?> model, boolean selected, Color background, Font font) {
        
        GridBagLayout gbLayout = new GridBagLayout();
        gbLayout.columnWeights = new double[]{0, 1};
        setLayout(gbLayout);
        
        JLabel leftIconLabel = new JLabel();
        add(leftIconLabel, gbc(0, 0, GridBagConstraints.VERTICAL));
        
        JPanel rightTextPanel = new JPanel();
        rightTextPanel.setOpaque(false);
        add(rightTextPanel, gbc(1, 0, GridBagConstraints.HORIZONTAL));
        
        this.model = model;
        BoxLayout layout = new BoxLayout(rightTextPanel, BoxLayout.Y_AXIS);
        rightTextPanel.setLayout(layout);
        
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // content
        setBackground(background);
        
        MultilineLabel titleLabel = new MultilineLabel(model.getModel().getTitle(), 8);
        rightTextPanel.add(UIHelper.gap(titleLabel, new Insets(0, 0, 0, 5)));
        
        MultilineLabel shortTextLabel = new MultilineLabel(model.getModel().getShortText(), 8);
        rightTextPanel.add(UIHelper.gap(shortTextLabel, new Insets(0, 0, 0, 0)));
        
        titleLabel.setFont(font);
        setPreferredSize(null);
        
        listSelectionHandler = new ListSelectionHandler(leftIconLabel);
        listSelectionHandler.handle(selected);
    }
    
}