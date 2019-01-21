# zold-java-client

[![Managed by Zerocrat](https://www.0crat.com/badge/GFCHY7NQG.svg)](https://www.0crat.com/p/GFCHY7NQG)

[![Build Status](https://travis-ci.org/amihaiemil/zold-java-client.svg?branch=master)](https://travis-ci.org/amihaiemil/zold-java-client)
[![Coverage Status](https://coveralls.io/repos/github/amihaiemil/zold-java-client/badge.svg?branch=master)](https://coveralls.io/github/amihaiemil/zold-java-client?branch=master)

[![Donate via Zerocracy](https://www.0crat.com/contrib-badge/GFCHY7NQG.svg)](https://www.0crat.com/contrib/GFCHY7NQG)
[![DevOps By Rultor.com](http://www.rultor.com/b/amihaiemil/zold-java-client)](http://www.rultor.com/p/amihaiemil/zold-java-client)
[![We recommend IntelliJ IDEA](http://amihaiemil.github.io/images/intellij-idea-recommend.svg)](https://www.jetbrains.com/idea/)

Java wrapper for Zold's RESTful API.

### Maven dependency

The library comes as a maven dependency:

```
<dependency>
    <groupId>com.amihaiemil.web</groupId>
    <artifactId>zold-java-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

**In order for it to work, you need to have an implementation of [JSON-P (JSR 374)](https://javaee.github.io/jsonp/index.html) in your classpath (it doesn't come transitively since most people are using Java EE APIs so, chances are it is already provided!).**

If you are not using Maven, you can also download the <a href="https://oss.sonatype.org/service/local/repositories/releases/content/com/amihaiemil/web/zold-java-client/0.0.1/zold-java-client-0.0.1-jar-with-dependencies.jar">fat</a> jar.

### Contributing 

If you would like to contribute, just open an issue or a PR.

Make sure the maven build:

``$mvn clean install -Pcheckstyle``

passes before making a PR. [Checkstyle](http://checkstyle.sourceforge.net/) will make sure
you're following our code style and guidlines.

This project is managed by [Zerocracy](http://www.zerocracy.com/), see the 
[Policy](http://www.zerocracy.com/policy.html) for more details.

Note that we do not have Zerocracy QAs, yet we still try to adhere to the [QA
 rules](http://www.zerocracy.com/policy.html#42) as much as possible (we won't block any PRs for cosmetic stuff such 
 as commit messages, though).