FROM openjdk:11-slim
LABEL maintainer=almaviva.it
EXPOSE 8080 9090
VOLUME /tmp

ARG DEPENDENCY=profile-ms-boot/target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
RUN mkdir -p /app/config

ENTRYPOINT ["java",  "-cp","app:app/lib/*", "it.almaviva.eai.pm.boot.ProfilemanagerApplication"]