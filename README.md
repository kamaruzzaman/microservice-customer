This is a demo project to demonstrate the Microservice Architecture development using Java and Spring Boot project. 
It also demonstrates packaging the jar file into OCI container image, tagging and publishing the container image into 
the Azure Container Registry (ACR), and finally deploy the container image into the Azure Kubernetes Service (AKS) using 
the tool eksctl. 

The application is a headless Backend application developed using the Spring Boot framework. As a data store, MongoDB Atlas 
for AWS is used. Also Amazon is used as the public cloud provider. 

It is a simple CRUD application to model the Customer handling of an E-commerce shop.

Together with the Order Microservice (https://github.com/kamaruzzaman/microservice-order), it demonstrates the 
Microservice communication using REST API.

* Run the application locally:
./gradlew bootRun 

The Swagger UI is available at:
http://localhost:8080/customer/swagger-ui/

The Swagger JSON file is available at:
http://localhost:8080/customer/v2/api-docs

The backend is available at:
http://localhost:8080/customer/

* Create the OCI compliant Docker Image:
sudo ./gradlew bootBuildImage
  
* Run the Docker Image:
docker run -p8080:8080 docker.io/library/microservice-customer:1.0.0-SNAPSHOT
