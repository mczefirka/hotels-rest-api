#!/bin/sh
MAVEN_PROJECTBASEDIR=$(pwd)
WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    curl -sS -o "$WRAPPER_JAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    if [ ! -f "$WRAPPER_JAR" ]; then
        echo "Failed to download Maven Wrapper."
        exit 1
    fi
fi

exec "$JAVA_HOME/bin/java" $MAVEN_OPTS -classpath "$WRAPPER_JAR" "-Dmaven.home=$MAVEN_PROJECTBASEDIR/.mvn/wrapper" "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" org.apache.maven.wrapper.MavenWrapperMain "$@"
