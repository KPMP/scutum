FROM alpine

RUN apk update && \
    apk upgrade
RUN apk add openjdk8

RUN apk add --no-cache tzdata
RUN cp /usr/share/zoneinfo/America/Detroit /etc/localtime
RUN echo "America/Detroit" > /etc/timezone

VOLUME /tmp
ARG DEPENDENCY=target/dependency

COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","org.kpmp.Application"]
