FROM alpine

RUN echo "https://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories
RUN apk update && apk add --no-cache openjdk16 curl

WORKDIR /server
RUN curl -o paper.jar https://papermc.io/api/v2/projects/paper/versions/1.16.5/builds/675/downloads/paper-1.16.5-675.jar
WORKDIR /server/data

CMD java -Xmx2G -Xmx4G -jar ../paper.jar
