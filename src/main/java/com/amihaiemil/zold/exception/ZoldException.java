package com.amihaiemil.zold.exception;

/**
 * Class for Zold specific exceptions.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com).
 */

public class ZoldException extends Exception {

    /**
     * Constructor.
     *
     * @param message Exception message.
     */
    public ZoldException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message Exception message.
     * @param cause Exception cause.
     */
    public ZoldException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
