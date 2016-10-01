package de.ecreators.solr.tools.create_collection;

import de.ecreators.solr.tools.SolrTools;

import java.awt.*;

/**
 * Der Controller für Create
 *
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrCreateTool extends SolrTools.SolrTool<Object> {
    
    public SolrCreateTool() {
        super("Neue Collection", "Erstellt oder löscht eine Collection, die solr indizieren und durchsuchen kann", null);
    
        /* THEMA:
         * Anlegen einer neuen Kollection mit Namen
         *
         * Solr muss entpackt sein
         * Java muss 1.8 sein (für die Sitzung reicht das aus)
         * Solr muss gestartet sein
         */
    }
    
    @Override
    protected void initializeTool(GridBagLayout layout, Container containerToAddYourView) {
        // create de.ecreators.solr.tools.model and ui and add ui to container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        containerToAddYourView.add(new SolrCreateUI(), gbc);
    }
    
}
