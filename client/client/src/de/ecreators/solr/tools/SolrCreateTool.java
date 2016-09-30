package de.ecreators.solr.tools;

import javax.swing.*;
import java.awt.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrCreateTool extends SolrTools.SolrTool<Object> {
    
    public SolrCreateTool() {
        super("Neue Collection", "Erstellt oder löscht eine Collection, die solr indizieren und durchsuchen kann", null);
    }
    
    @Override
    protected void initializeTool(GridBagLayout layout, Container containerToAddYourView) {
        // create de.ecreators.solr.tools.model and ui and add ui to container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        containerToAddYourView.add(new SolrCreateUI(), gbc);
    }
    
    private static final class SolrCreateUI extends JPanel {
        
        public SolrCreateUI() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            add(new JLabel("Test 1"));
            add(new JLabel("Test 2"));
        }
    }
}
