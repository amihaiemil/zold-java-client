package com.amihaiemil.zold.request;

import com.amihaiemil.zold.exception.ZoldException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.util.Arrays;

/**
 * Extract Job Id from HttpResponse.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 */

public final class ZoldJobId {

    /**
     * Extract job walletId from HttpResponse.
     *
     * @param response HttpResponse.
     * @return Job walletId as String
     * @throws ZoldException exception.
     */
    public String jobId(final HttpResponse response)
            throws ZoldException {
        String header = this.extractXZoldJobHeader(response).toString();
        return header.substring(header.indexOf(": ") + 2);
    }

    /**
     * Extract job walletId from HttpResponse.
     *
     * @param response HttpResponse
     * @return Header
     * @throws ZoldException exception
     */
    private Header extractXZoldJobHeader(final HttpResponse response)
            throws ZoldException {
        Header header = response.getFirstHeader("X-Zold-Job");
        if (header == null) {
            throw new ZoldException("No X-Zold-Job header found among "
                    + Arrays.toString(response.getAllHeaders()));
        }

        return header;
    }
}
