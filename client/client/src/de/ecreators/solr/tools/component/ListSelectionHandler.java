package de.ecreators.solr.tools.component;

import de.ecreators.solr.tools.api.utils.StrategyHandler;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class ListSelectionHandler extends StrategyHandler<JLabel, Boolean> {
    
    private final ImageIcon icon;
    
    public ListSelectionHandler(JLabel target) {
        super(target);
        icon = new ImageIcon(getClass().getResource("/arrow_48px.png"));
        Dimension size = new Dimension(25, 20);
        target.setPreferredSize(size);
        target.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    }
    
    @Override
    public void handle(Boolean context) {
        if(context) {
            target.setIcon(icon);
        } else {
            target.setIcon(null);
        }
    }
}
