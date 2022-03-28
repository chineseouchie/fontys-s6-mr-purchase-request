FROM openjdk:11
RUN mkdir /build
WORKDIR /build
COPY . .
CMD [ "./mvnw", "install"]