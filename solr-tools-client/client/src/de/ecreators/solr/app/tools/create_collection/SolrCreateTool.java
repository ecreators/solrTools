package de.ecreators.solr.app.tools.create_collection;

import de.ecreators.solr.api.utils.MyStringUtils;
import de.ecreators.solr.app.main.SolrTools;

import java.awt.*;

/**
 * Der Controller für Create
 *
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrCreateTool extends SolrTools.SolrTool<Object> {
    
    public SolrCreateTool() {
        super("Collections", "Erstellt oder löscht eine Collection, die solr indizieren und durchsuchen kann",
              null,
              MyStringUtils.format(true, "Verwalten von{0}Collections", System.lineSeparator()));
    
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
        // create de.ecreators.solr.tools.api.model and ui and add ui to container
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        containerToAddYourView.add(new SolrCreateUI(), gbc);
    }
    
}
