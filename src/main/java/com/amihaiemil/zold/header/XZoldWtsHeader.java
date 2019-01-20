package com.amihaiemil.zold.header;

import org.apache.http.message.BasicHeader;

/**
 * Class to keep Zold API key as a HTTP header.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public final class XZoldWtsHeader extends BasicHeader {

    /**
     * Constructor.
     *
     * @param key API key
     */
    public XZoldWtsHeader(final String key) {
        super("X-Zold-Wts", key);
    }
}
