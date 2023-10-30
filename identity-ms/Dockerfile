FROM openjdk:11-slim
LABEL maintainer=almaviva.it

RUN mkdir -p /app/config

RUN addgroup --system spring &&  APPGROUP=`grep "spring" /etc/group|cut -d: -f3` && adduser --system --gid $APPGROUP spring
RUN chown -R spring:spring /app

USER spring

ADD identity-ms-boot/target/identity-ms-boot.jar /app/app.jar


WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./app.jar", ">", "/dev/stdout", "2>&1"]
