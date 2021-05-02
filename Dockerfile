FROM alpine

RUN echo "https://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories
RUN apk update && apk add --no-cache openjdk16 curl git

ARG MC_VERSION=1.16.5
ENV MC_VERSION=${MC_VERSION}

WORKDIR /tools
RUN curl -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
RUN java -jar BuildTools.jar

WORKDIR /server
RUN cp /tools/spigot-${MC_VERSION}.jar /server
WORKDIR /server/data
RUN rm -rf /tools

CMD java -Xmx2G -Xmx4G -jar ../spigot-$MC_VERSION.jar
