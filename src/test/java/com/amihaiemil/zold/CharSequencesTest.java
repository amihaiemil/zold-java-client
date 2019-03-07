package com.amihaiemil.zold;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for CharSequences class.
 */
@RunWith(JUnit4.class)
public class CharSequencesTest {
    /**
     * Test if join works as String.join would.
     */
    @Test
    public final void joinsAsStringJoin() {
        final String expected = String.join(
                " ",
                "zold-java-client /",
                "0.0.2",
                "See https://github.com/amihaiemil/zold-java-client"
        );

        final String actual = CharSequences.join(
                " ",
                "zold-java-client /",
                "0.0.2",
                "See https://github.com/amihaiemil/zold-java-client"
        );

        Assert.assertThat(actual, Matchers.equalTo(expected));
    }
}
