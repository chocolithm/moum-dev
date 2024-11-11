FROM openjdk:21-jdk

ARG JAR_FILE=build/libs/moum.jar

COPY ${JAR_FILE} moum.jar

ENTRYPOINT [ "java", "-Dspring.profiles.active=dev", "-jar", "moum.jar" ]
# ENTRYPOINT [ "java", "-Dspring.profiles.active=prod", "-jar", "moum.jar" ]