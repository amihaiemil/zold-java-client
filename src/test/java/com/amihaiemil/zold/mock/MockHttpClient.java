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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.client5.http.classic.HttpClient;

import org.junit.Assert;

/**
 * An HttpClient which asserts that some {@link HttpRequest}s
 * meet certain conditions specified by the user.
 * If successful, a response predetermined by the user is returned
 * for each request, otherwise it {@link Assert#fail() fails}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @version $Id$
 * @since 0.0.1
 */
public final class MockHttpClient implements HttpClient {

    /**
     * Assertions.
     */
    private final List<AssertRequest> assertions;

    /**
     * Ctor.
     *
     * @param assertions Given assertions.
     */
    @SuppressWarnings("unchecked")
    public MockHttpClient(final AssertRequest... assertions) {
        this.assertions = new ArrayList<>();
        Arrays.stream(assertions).forEach((assertion) -> {
            this.assertions.add(assertion);
        });
    }

    @Override
    public HttpResponse execute(final ClassicHttpRequest request) {
        return this.check(request);
    }

    @Override
    public HttpResponse execute(final ClassicHttpRequest request,
        final HttpContext context) {
        return this.check(request);
    }

    @Override
    public ClassicHttpResponse execute(final HttpHost target,
        final ClassicHttpRequest request) {
        return this.check(request);
    }

    @Override
    public HttpResponse execute(final HttpHost target,
        final ClassicHttpRequest request, final HttpContext context) {
        return this.check(request);
    }

    @Override
    public <T> T execute(final ClassicHttpRequest request,
        final HttpClientResponseHandler<? extends T> responseHandler)
        throws IOException {
        try {
            return responseHandler.handleResponse(
                this.check(request)
            );
        } catch (final HttpException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public <T> T execute(
        final ClassicHttpRequest request,
        final HttpContext context,
        final HttpClientResponseHandler<? extends T> responseHandler
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T execute(
        final HttpHost target,
        final ClassicHttpRequest request,
        final HttpClientResponseHandler<? extends T> responseHandler
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //@checkstyle ParameterNumber (8 lines)
    @Override
    public <T> T execute(
        final HttpHost target,
        final ClassicHttpRequest request,
        final HttpContext context,
        final HttpClientResponseHandler<? extends T> responseHandler
    ){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Checks all conditions against the request.
     *
     * @param request The request.
     * @return HttpResponse if all the cehcks pass.
     */
    private ClassicHttpResponse check(final ClassicHttpRequest request) {
        final Collection<Condition> conditions = this.assertions.get(0)
            .conditions();
        conditions.forEach(cond -> {
            cond.test(request);
        });
        final ClassicHttpResponse response = this.assertions.get(0).response();
        this.assertions.remove(0);
        return response;
    }
}
