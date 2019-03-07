package com.amihaiemil.zold;

import java.util.Objects;

/**
 * Provides character sequences operations.
 * @author Ammar Atef (ammar.atef45@gmail.com)
 * @version $Id$
 * @since 0.0.3
 */
public final class CharSequences {
    /**
     * Private default constructor.
     */
    private CharSequences() {}
    /**
     * Composes a String of {@code CharSequence sequences} joined together by
     * the specified {@code delimiter}.
     *
     * @param delimiter The delimiter
     * @param sequences The character sequences
     * @return The composed string
     */
    static String join(final CharSequence delimiter,
                       final CharSequence... sequences) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(sequences);
        final StringBuilder builder = new StringBuilder();
        for (final CharSequence sequence : sequences) {
            if (builder.length() > 0) {
                builder.append(delimiter);
            }
            builder.append(sequence);
        }
        return builder.toString();
    }
}
