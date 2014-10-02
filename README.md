[![Build Status](https://travis-ci.org/skosmalla/application-configuration-spring-maven.svg?branch=master)](https://travis-ci.org/skosmalla/application-configuration-spring-maven)

application-configuration-spring-maven
========================

This sample application demonstrates several possibilities how to configure a web application based on the Spring Framework. In one case the configuration artifacts are wrapped in the deployment artifact. In the another case the configuration are loaded during the runtime and they are independent of the deployment artifact. It is based on the wicket-quickstart Maven archetype. The result of the archetype is adjusted by querying a database, showing the query result and Spring Framework is integrated.

## Deployment Configuration
The database connection (see [wicket-module/src/main/resources/META-INF/spring/demo-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/demo-context.xml)) is configurable by two mechanism. 

One mechanism is the classic Spring Property Placeholder mechanism (see [wicket-module/src/main/resources/META-INF/spring/property-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/property-context.xml)). It requires that the configuration artifacts (here normal `properties` files) are wrapped in the deployment artifact.

The second mechanism is a combination of Spring Property Placeholder mechanism and JNDI (see [wicket-module/src/main/resources/META-INF/spring/jndi-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/jndi-context.xml)). It requires that the configuration artifacts are independent from the deployment artifact and the configuration element are loaded during runtime. 

### Configuration Artifacts Wrapped in the Deployment Artifact
`assembly-sample-war` demonstrates how configuration artifacts can be wrapped in a deployment artifact with Maven utilities. It requires that the classic Spring Property Placeholder is configured in the application context. Therefore the context loader listener creates an application context with [classpath*:META-INF/spring/demo-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/demo-context.xml) and [classpath*:META-INF/spring/property-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/property-context.xml) in the deployment descriptor (see [assembly-sample-war/src/main/webapps/WEB-INF/web.xml] (https://github.com/skosmalla/application-configuration-spring-maven/blob/master/assembly-sample-war/src/main/webapp/WEB-INF/web.xml)).

The next step is including the configuration artifact (here `properties` files) in the deployment artifact. Therefore `assembly-sample-war` uses _Maven War Plugin_ in combination with _Maven Assembly Plugin_. _Maven War Plugin_ creates the deployment artifact (here `war`file) without the configuration artifact. Then _Maven Assembly Plugin_ creates from this `war` artifact different new `war` artifacts with configuration for different target environment.Assembly descriptors (here they are in the module `assembly-descriptor`) describe which configuration artifacts should be wrapped in to a new deployment artifact. _Maven Assemby Plugin_ refers to these descriptors.

For example, the assembly descriptor `TEST` defines to take all `properties` files from the classpath `src/main/config/TEST` and put them to the path `WEB-INF/classes` in a new `war` file. You can refer to this new artifact with the same Maven GAV, but with an additional qualifier (here the qualifier is `TEST`). The complete Maven GAV  looks like that
```xml
    <groupId>de.kosmalla.sandra.wicket.spring.maven.demo</groupId>
    <artifactId>assembly-sample-war</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <qualifier>TEST</qualifier>
    <type>war</type>
```

### Configuration Artifacts Loaded During Runtime Over JNDI
`jndi-sample-war` shows how a deployment artifact can load its configuration during the runtime. It requires that the Spring Property Placeholder is configured to load the properties over a JNDI lookup. Therefore the context loader listener creates an application context with [classpath*:META-INF/spring/demo-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/demo-context.xml) and [classpath*:META-INF/spring/jndi-context.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/wicket-module/src/main/resources/META-INF/spring/jndi-context.xml) in the deployment descriptor (see [jndi-sample-war/src/main/webapps/WEB-INF/web.xml] (https://github.com/skosmalla/application-configuration-spring-maven/blob/master/jndi-sample-war/src/main/webapp/WEB-INF/web.xml)).

The configuration elements are defined in a JNDI context file (see [jndi-sample-war/context-config/jndi-sample-war.xml](https://github.com/skosmalla/application-configuration-spring-maven/blob/master/jndi-sample-war/context-config/jndi-sample-war.xml)). This XML file has to be the same name like the war file. The next important issue is that the attribute `docBase` has an absolute path the war file as value. The JNDI context file has to put into the Tomcat before Tomcat starts. The location of the JNDI context file in Tomcat is `$CATALINE_HOME/conf/Cataline/localhost`. Important is that the war file is not deployed on `$CATALINA_HOME/webapps`. In the default configuration of Tomcat this location is for hot deployments and then the JNDI context file is ignored. 

