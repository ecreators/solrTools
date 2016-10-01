package de.ecreators.solr.tools;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class CustomSplitPane extends JPanel {
    
    public CustomSplitPane(JComponent left, JComponent right) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0, 1};
        layout.rowWeights = new double[]{1};
        setLayout(layout);
        
        add(left, gbc(0, 0, GridBagConstraints.VERTICAL));
        add(right, gbc(1, 0, GridBagConstraints.BOTH));
    }
    
    private static GridBagConstraints gbc(int x, int y, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = fill;
        return gbc;
    }
}
