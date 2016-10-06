package de.ecreators.solr.app.tools.create_collection;

import javax.swing.*;

/**
 * Die Details des CreateTools (rechts)
 */
final class SolrCreateUI extends JPanel {
    
    public SolrCreateUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(new JLabel("Test 1"));
        add(new JLabel("Test 2"));
    }
}
