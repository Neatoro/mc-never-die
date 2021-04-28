FROM alpine

RUN apk add --no-cache openjdk11 curl git

ARG MC_VERSION=1.16.5

WORKDIR /tools
RUN curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
RUN java -jar BuildTools.jar

WORKDIR /server
RUN cp /tools/spigot-${MC_VERSION}.jar /server/
RUN rm -rf /tools

CMD [ "java", "-jar", "spigot-${MC_VERSION}.jar" ]
