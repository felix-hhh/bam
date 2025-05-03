
FROM openjdk:25-jdk

COPY build/libs/app-1.0.0.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

CMD ["--spring.profiles.active=dev"]

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

EXPOSE 10000