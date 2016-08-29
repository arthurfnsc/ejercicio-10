FROM java:latest
VOLUME /tmp
ADD ejercicio-10-0.0.1-SNAPSHOT.jar /app.jar
ADD wait-for-it.sh /wait-for-it.sh
ADD start.sh /start.sh
RUN sh -c 'touch /app.jar'

ENTRYPOINT ["/wait-for-it.sh","postgres:5432", "--", "/start.sh"]
