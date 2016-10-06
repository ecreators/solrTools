package de.ecreators.solr.component;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class MultilineLabel extends JTextArea {
    public MultilineLabel(String text, int columns) {
        super(text, countRows(text), columns);
        setBackground(null);
        setOpaque(false);
        setBorder(null);
    }
    
    private static int countRows(String shortText) {
        if(shortText == null) {
            return 1;
        }
        
        Matcher matcher = Pattern.compile(format("(%s)", System.lineSeparator()), Pattern.LITERAL).matcher(shortText);
        if(matcher.find()) {
            return matcher.groupCount() + 1;
        }
        return 1;
    }
}
