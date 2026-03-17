build:
	cd api-gateway && ./mvnw clean package -DskipTests
	cd product-service && ./mvnw clean package -DskipTests
	cd order-service && ./mvnw clean package -DskipTests

up:
	docker compose up --build

down:
	docker compose down

restart:
	down build up