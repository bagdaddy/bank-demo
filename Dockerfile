#FROM alpine as build
#
#ARG MAVEN_VERSION=3.8.6
#ARG USER_HOME_DIR="/root"
#ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
#
#RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
# && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
# && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
# && rm -f /tmp/apache-maven.tar.gz \
# && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#
#ENV MAVEN_HOME /usr/share/maven
#ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
#
#ENV JAVA_HOME /usr/lib/jvm/default-jvm/
#
## Define default command.
#CMD ["mvn", "--version"]
#
##COPY target/demo2.jar demo2.jar
#
##ENTRYPOINT ["java", "-jar", "/demo2.jar"]
#
#FROM openjdk:18
#FROM maven:3.8.6-openjdk-18 as builder

#Start application
#WORKDIR /var/www/html

#CMD ["bash"]
#RUN ls /var/www/html

#RUN mvn spring-boot:run


#RUN mkdir -p /build
#WORKDIR /build
#COPY pom.xml /build
#Download all required dependencies into one layer
#RUN mvn -B dependency:resolve dependency:resolve-plugins
#Copy source code
#COPY src /build/src

#CMD ["mvn", "--version"]
# Build application
#RUN mvn package

FROM openjdk:18
ADD target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]