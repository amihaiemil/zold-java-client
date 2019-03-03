/**
 * Copyright (c) 2019, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1)Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 3)Neither the name of zold-java-client nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.zold.mock;

import com.sun.grizzly.util.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ProtocolVersion;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.apache.hc.core5.http.message.StatusLine;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;

/**
 * An {@link HttpResponse} suitable for tests. Can be configured with 
 * predetermined {@link HttpStatus http status code} and JSON payload.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class Response implements HttpResponse {

    /**
     * Its backbone, holding what we need.
     */
    private ClassicHttpResponse backbone;
    
    /**
     * Ctor.
     * <p>
     * Response with no payload.
     * @param status The {@link HttpStatus http status code}
     */
    public Response(final int status) {
        this(status, "{}");
    }

    /**
     * Ctor.
     *
     * @param status The {@link HttpStatus http status code}
     * @param jsonPayload The json payload
     */
    public Response(final int status, final String jsonPayload) {
        this.backbone = new BasicClassicHttpResponse(status, "REASON");
        this.backbone.setVersion(new ProtocolVersion("HTTP", 1, 1));
        this.backbone.setEntity(
            new StringEntity(
                jsonPayload, ContentType.APPLICATION_JSON
            )
        );
        this.backbone.setHeader("Date", new Date().toString());
        this.backbone.setHeader(
            "Content-Length", String.valueOf(jsonPayload.getBytes().length)
        );
        this.backbone.setHeader("Content-Type", "application/json");
    }

    @Override
    public int getCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCode(final int code) throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getReasonPhrase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setReasonPhrase(final String reason)
        throws IllegalStateException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Locale getLocale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLocale(final Locale loc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProtocolVersion getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setVersion(final ProtocolVersion version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countHeaders(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsHeader(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Header getHeader(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Header[] getHeaders(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Header[] getHeaders() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Header getFirstHeader(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Header getLastHeader(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addHeader(final Header header) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addHeader(final String name, final Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHeader(final Header header) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHeader(final String name, final Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHeaders(final Header[] headers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeHeader(final Header header) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeHeaders(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Header> headerIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Header> headerIterator(final String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Prints itself to the channel.
     * @param channel The channel.
     * @throws IOException If an error occurs.
     */
    public void printTo(final WritableByteChannel channel) throws IOException {
        channel.write(
            ByteBuffer.wrap(
                this.toString().getBytes(Charsets.UTF8_CHARSET)
            )
        );
    }

    /**
     * This response as a string.
     * @return String representation of this {@link Response}.
     */
    @Override
    public String toString() {
        final String CRLF = "" + (char) 0x0D + (char) 0x0A;
        try {
            final StringBuilder builder = new StringBuilder()
                .append(new StatusLine(this.backbone))
                .append(CRLF)
                .append(
                   Stream.of(
                        this.backbone.getHeaders()
                    ).map(header -> header.toString())
                    .collect(Collectors.joining(CRLF))
                )
                .append(CRLF).append(CRLF)
                .append(
                    new BasicHttpClientResponseHandler().handleEntity(
                        this.backbone.getEntity()
                    )
                );
            return builder.toString();
        } catch (final IOException ex) {
            throw new IllegalStateException(
                "IOException when reading the HTTP response. " + ex
            );
        }
    }
}
