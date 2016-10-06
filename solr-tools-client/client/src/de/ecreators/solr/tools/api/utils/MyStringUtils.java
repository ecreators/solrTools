package de.ecreators.solr.tools.api.utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class MyStringUtils {
    
    protected MyStringUtils() {
    }
    
    public static String format(String text, Object... values) {
        return format(true, text, values);
    }
    
    public static String format(boolean ignoreLessUsedParameters, String text, Object... values) {
        if(text == null) {
            return null;
        }
        
        if(text.length() < 3 || !text.matches(".*[0-9{}]+.*")) {
            return text;
        }
        
        List<Object> params = new ArrayList<>();
        Map<Integer, AtomicInteger> matchReplacments = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            matchReplacments.put(i, new AtomicInteger());
        }
        StringBuilder outOfRange = new StringBuilder(String.format("No value for matching placeholder 'text' having '%s', values %s (length: %d): (", text, Arrays.toString(values), values.length));
        boolean invalidIndex = false;
        
        String mappedText = text;
        outher:
        for (int charI = 0; charI < text.length(); ) {
            String region = text.substring(charI);
            if(region.startsWith("{")) {
                for (int charE = charI + 2; charE < text.length(); charE++) {
                    String end = text.substring(charI, charE);
                    String nowChar = String.valueOf(end.charAt(end.length() - 1));
                    if(end.length() > 1 && end.endsWith("{") || !nowChar.matches("[0-9}]")) {
                        charI += end.length() - 1;
                        continue outher;
                    } else if(end.endsWith("}")) {
                        region = end;
                        break;
                    }
                }
            } else {
                charI++;
                continue;
            }
            
            String match = region;
            if(region.startsWith("{") && region.endsWith("}")) {
                if(region.length() >= 3) {
                    region = region.substring(1, region.length() - 1);
                    if(region.matches("\\d+")) {
                        int index = Integer.parseInt(region);
                        if(index < values.length) {
                            Object valueAt = values[index];
                            params.add(valueAt);
                            mappedText = mappedText.replaceFirst(Pattern.quote(match), "%s");
                            matchReplacments.get(index).incrementAndGet();
                        } else {
                            if(invalidIndex) {
                                outOfRange.append(", ");
                            }
                            outOfRange.append(String.format("no value for index %d match with '%s'", index, match));
                            invalidIndex = true;
                        }
                    }
                }
            }
            charI = charI + match.length();
        }
        
        outOfRange.append(") - Argument 'values' must contain at least more values or ignore this check using parameter 'ignoreLessUsedParameters' with true!");
        if(invalidIndex && !ignoreLessUsedParameters) {
            System.err.println(outOfRange.toString());
            throw new IllegalArgumentException(outOfRange.toString());
        }
        
        validateUsageOfParameters(matchReplacments, values);
        
        return String.format(mappedText, params.toArray());
    }
    
    private static void validateUsageOfParameters(Map<Integer, AtomicInteger> matchReplacments, Object[] values) throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder("Unused parameters: (");
        boolean invalid = false;
        for (Map.Entry<Integer, AtomicInteger> matcher : matchReplacments.entrySet()) {
            if(matcher.getValue().get() == 0) {
                if(invalid) {
                    sb.append(", ");
                }
                sb.append(String.format("p%d '%s'", matcher.getKey(), values[matcher.getKey()]));
                invalid = true;
            }
        }
        sb.append(")");
        if(invalid) {
            System.err.println(sb.toString());
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
