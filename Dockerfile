FROM openjdk:21-jdk

ARG JAR_FILE=build/libs/moum.jar

COPY ${JAR_FILE} moum.jar

ENTRYPOINT [ "java", "-jar", "moum.jar" ]