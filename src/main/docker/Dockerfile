FROM java:8 
VOLUME /tmp 
ADD share-demo-0.0.1-SNAPSHOT.jar share-demo.jar
RUN bash -c 'touch /share-demo.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/share-demo.jar"]
