##########################################################################################
## Step 1 : Build application
##########################################################################################
FROM maven:3.6.3-jdk-8 as maven_build
#
LABEL maintainer="Padmalaya Singhai"

# Copy source code
COPY src /home/app/src
COPY pom.xml /home/app

# Copy settings.xml file
#COPY settings-docker.xml /home/app/settings.xml

# Build app
#RUN mvn -f /home/app/pom.xml -s /home/app/settings.xml clean package
RUN mvn -f home/app/pom.xml clean package


##########################################################################################
## Step 2 : Build application image
##########################################################################################
FROM adoptopenjdk/openjdk8:alpine-jre

#
LABEL maintainer="Padmalaya Singhai"

# For storing the version in the image
ADD VERSION .

# Copy jar file from build step
COPY --from=maven_build /home/app/target/car-charging-session-*.jar car-charging-session.jar

# Expose ports
EXPOSE 9000

# Entry point
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=default", "/car-charging-session.jar"]
