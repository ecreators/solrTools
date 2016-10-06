package de.ecreators.solr.tools.api.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public final class UIHelper {
    
    protected UIHelper() {
    }
    
    public static <T extends JComponent> T gap(T view, Insets margin) {
        view.setBorder(BorderFactory.createEmptyBorder(margin.top, margin.left, margin.bottom, margin.right));
        view.setPreferredSize(null);
        return view;
    }
}
