

Swagger-ui:
http://localhost:8080/customer/swagger-ui/

Swagger-json:
http:/localhost:8080/customer/v2/api-docs

sudo ./gradlew bootBuildImage
docker run -p8080:8080 docker.io/library/microservice-customer:0.0.1-SNAPSHOT
