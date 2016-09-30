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
        // create model and ui and add ui to container
    }
}
