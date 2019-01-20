package com.amihaiemil.zold.request;

import com.amihaiemil.zold.client.ZoldClient;
import com.amihaiemil.zold.header.UserAgentRequestHeader;
import com.amihaiemil.zold.header.XZoldWtsHeader;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;

/**
 * Abstract HTTP request for Zold network.
 *
 * @author Oleksandr Sankin (sankin.alex@gmail.com)
 *
 * @param <I> HttpRequestBase.
 */

abstract class AbstractZoldRequest<I extends HttpRequestBase> {

    /**
     * ZoldClient zoldClient.
     */
    private final ZoldClient zoldClient;

    /**
     * Constructor.
     *
     * @param zoldClient ZoldClient zoldClient.
     */
    AbstractZoldRequest(final ZoldClient zoldClient) {
        this.zoldClient = zoldClient;
    }

    /**
     * Prepare HTTP headers.
     *
     * @param request Request.
     */
    void prepareHeaders(final I request) {
        request.setHeaders(new Header[] {
            new BasicHeader(HttpHeaders.CONTENT_TYPE,
                    ContentType.APPLICATION_FORM_URLENCODED.getMimeType()),
            new UserAgentRequestHeader(),
            new XZoldWtsHeader(this.zoldClient.key()),
        });
    }

    /**
     * Prepare request config.
     *
     * @param request Request.
     */
    void prepareRequestConfig(final I request) {
        request.setConfig(RequestConfig.custom()
                .setRedirectsEnabled(false)
                .build());
    }

    /**
     * Build HTTP request.
     *
     * @return BaseHttpRequest.
     */
    abstract I request();

}
