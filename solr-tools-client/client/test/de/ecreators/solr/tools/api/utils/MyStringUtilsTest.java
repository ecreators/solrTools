package de.ecreators.solr.tools.api.utils;

import de.ecreators.solr.api.utils.MyStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Bjoern Frohberg, mydata GmbH
 */
public class MyStringUtilsTest {
    
    @Test
    public void formatSimple() {
        // given
        String x = "{0}";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("12"));
    }
    
    @Test
    public void formatConcat() {
        // given
        String x = "{0}{0}";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("1212"));
    }
    
    @Test
    public void formatConcatWithin() {
        // given
        String x = "{0}text{0}";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("12text12"));
    }
    
    @Test
    public void formatConcatWithin2() {
        // given
        String x = "text{0}{0}";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("text1212"));
    }
    
    @Test
    public void formatConcatWithin3() {
        // given
        String x = "text{0}{0}text";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("text1212text"));
    }
    
    @Test
    public void formatConcatWithin4() {
        // given
        String x = "{0}{0}text";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("1212text"));
    }
    
    @Test
    public void formatConcatWithin5() {
        // given
        String x = "{0}text{0}text";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("12text12text"));
    }
    
    @Test
    public void formatConcatWithin6() {
        // given
        String x = "{0}text{1}text";
        // when
        String result = MyStringUtils.format(x, 12);
        // then
        assertThat(result, equalTo("12text{1}text"));
    }
    
    @Test
    public void formatConcatWithinMultiple() {
        // given
        String x = "{0}text{1}text";
        // when
        String result = MyStringUtils.format(x, 12, -2);
        // then
        assertThat(result, equalTo("12text-2text"));
    }
    
    @Test
    public void formatConcatWithinMultipleComplex() {
        // given
        String x = "{0}text{1}text{0}s";
        // when
        String result = MyStringUtils.format(x, 12, -2);
        // then
        assertThat(result, equalTo("12text-2text12s"));
    }
    
    //////////////////
    // negative tests
    //////////////////
    
    @Test
    public void neg_formatConcatWithinMultipleComplex() {
        // given
        String x = "{0text{1}text{0}s";
        // when
        String result = MyStringUtils.format(x, 12, -2);
        // then
        assertThat(result, equalTo("{0text-2text12s"));
    }
    
    @Test
    public void neg_formatConcatWithinMultipleComplex1() {
        // given
        String x = "{}0text{1}text{0}s";
        // when
        String result = MyStringUtils.format(x, 12, -2);
        // then
        assertThat(result, equalTo("{}0text-2text12s"));
    }
    
    @Test
    public void neg_formatNULL() {
        // given
        String x = null;
        // when
        String result = MyStringUtils.format(x, 12, -2);
        // then
        assertThat(result, is(nullValue()));
    }
    
    ///////////////////
    // expected fails
    ///////////////////
    
    @Test(expected = IllegalArgumentException.class)
    public void neg_formatInvalid() {
        // given
        String x = "hallo {0}";
        
        // when
        MyStringUtils.format(x, true, "welt", -2);
        
        // illigal!
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void neg_valuesInvalid() {
        // given
        String x = "hallo {0}{1}";
        
        // when
        MyStringUtils.format(false, x, "welt");
        
        // illigal!
    }
}