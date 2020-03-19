FROM clojure as builder
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY clojure-raw-rest-api/ ./
RUN lein deps
RUN lein test
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar

FROM openjdk:8-jre-alpine

COPY --from=builder /usr/src/app/app-standalone.jar ./
ENTRYPOINT ["java", "-jar", "app-standalone.jar"]

EXPOSE 8081