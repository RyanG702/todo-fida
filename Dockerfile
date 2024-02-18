# Start your image with a Java base image
FROM openjdk:17

ARG JAR_FILE=target/*.jar

# Copy the app package and package-lock.json file
COPY ./target/todo-list-0.0.1-SNAPSHOT.jar app.jar

# Start the app using serve command
CMD [ "java", "-jar", "app.jar" ]