package de.ecreators.solr.tools.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class ToolItemUI extends JPanel {
    
    public ToolItemUI(JLabel content) {
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(null);
        content.setBorder(null);
        setBackground(content.getBackground());
        
        add(gap(content, new Insets(0, 0, 0, 5)));
        add(gap(new JLabel("Test"), new Insets(0, 0, 0, 0)));
    }
    
    private JComponent gap(JComponent view, Insets margin) {
        view.setBorder(BorderFactory.createEmptyBorder(margin.top, margin.left, margin.bottom, margin.right));
        view.setPreferredSize(null);
        return view;
    }
}
