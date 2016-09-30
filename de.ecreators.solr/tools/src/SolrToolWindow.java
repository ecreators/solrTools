import javax.swing.*;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class SolrToolWindow extends JFrame {
    
    public SolrToolWindow(SolrTools tools) {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // TODO add tools as Collection on left
        // TODO each clickable to show its content on right
        // TODO add toolbar to exit, start, restart stop solr application
    }
}
