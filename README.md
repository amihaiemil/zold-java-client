# zold-java-client

[![Build Status](https://travis-ci.org/amihaiemil/zold-java-client.svg?branch=master)](https://travis-ci.org/amihaiemil/zold-java-client)
[![Coverage Status](https://coveralls.io/repos/github/amihaiemil/zold-java-client/badge.svg?branch=master)](https://coveralls.io/github/amihaiemil/zold-java-client?branch=master)

[![DevOps By Rultor.com](http://www.rultor.com/b/amihaiemil/zold-java-client)](http://www.rultor.com/p/amihaiemil/zold-java-client)
[![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)

Java wrapper for Zold's RESTful API.

### Maven dependency

The library comes as a maven dependency:

```
<dependency>
    <groupId>com.amihaiemil.web</groupId>
    <artifactId>zold-java-client</artifactId>
    <version>not-yet-released</version>
</dependency>
```

**In order for it to work, you need to have an implementation of [JSON-P (JSR 374)](https://javaee.github.io/jsonp/index.html) in your classpath (it doesn't come transitively since most people are using Java EE APIs so, chances are it is already provided!).**

If you are not using Maven, you can also download the <a href="#">fat</a> jar.

### Contributing 

If you would like to contribute, just open an issue or a PR.

Make sure the maven build:

``$mvn clean install -Pcheckstyle``

passes before making a PR. [Checkstyle](http://checkstyle.sourceforge.net/) will make sure
you're following our code style and guidlines.
