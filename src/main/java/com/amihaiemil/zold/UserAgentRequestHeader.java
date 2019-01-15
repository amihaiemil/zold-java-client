package com.amihaiemil.zold;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import org.apache.http.HttpHeaders;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.message.BasicHeader;

/**
 * User Agent Request Header Interceptor.
 * @author Boris Kuzmic for docker-java-api project
 * @since 0.0.1
 * @version 0.0.1
 */
/**
* TODO use this class when instantiating the HttpClient.
*/
final class UserAgentRequestHeader extends RequestDefaultHeaders {

    /**
     * Config properties file.
     */
    private static final String CONFIG_FILE = "config.properties";

    /**
     * Version property key.
     */
    private static final String VERSION_KEY = "build.version";

    /**
     * Ctor.
     */
    UserAgentRequestHeader() {
        super(Collections.singletonList(
            new BasicHeader(
                HttpHeaders.USER_AGENT,
                String.join(
                    " ",
                    "zold-java-client /",
                    version(),
                    "See https://github.com/amihaiemil/zold-java-client"
                )
            )
        ));
    }

    /**
     * Read current version from property file.
     * @return Build version.
     */
    private static String version() {
        final ClassLoader loader =
            Thread.currentThread().getContextClassLoader();
        final String version;
        final Properties properties = new Properties();
        try (final InputStream inputStream =
                 loader.getResourceAsStream(CONFIG_FILE)){
            properties.load(inputStream);
            version = properties.getProperty(VERSION_KEY);
        } catch (final IOException exception) {
            throw new RuntimeException(
                String.format("Missing %s file.", CONFIG_FILE)
            );
        }
        return version;
    }

}