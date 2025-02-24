<h1>Project description:</h1>

<h2>What it will be:</h2>

<h3>Languages: </h3>
- java
- go

<h3>Infrastructure:</h3> 
- Kubernetes
- Kafka
- gRPC
- Zipkin
- LGTM (Loki Grafana Tempo Prometheus)

<h3>Databases:</h3>
- Postgresql
- Mongodb

<h2>Project description:</h2>
<h3>Initially it is about to have next microservices:</h3>
- a. Customer
- b. Product
- c. Order
- d. Payment
- e. Notification
<h3>Support services:</h3>
- Config Server
- Eureka Server
  (these 2 will be removed when k8s will be onboarded)


<h3>Service ports:</h3>
- 8888: Config server
- 8761: Eureka server
- 8090: Customer service