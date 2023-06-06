# FROM adoptopenjdk:11 
# VOLUME /tmp

# ARG JAR_FILE=build/libs/*.jar
# COPY ${JAR_FILE} app.jar

# RUN java -version 2>&1

# RUN echo "Java version:" && java -version 2>&1

# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]



FROM openjdk:11
WORKDIR /app
COPY . /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

RUN ./gradlew build
# EXPOSE 8080
CMD ["java", "-jar", "app.jar"] 
