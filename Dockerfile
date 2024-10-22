# Use an official Java runtime as a base image
FROM openjdk:11

# Add Maintainer Info
LABEL maintainer="MAURICIO"

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your local machine to the Docker image
COPY target/serve-0.0.1-SNAPSHOT.jar /app/serve.jar

# Open port 8080 for external access
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/serve.jar"]
