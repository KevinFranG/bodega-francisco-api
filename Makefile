SERVICES ?=


build:
	mvn clean package -DskipTests

up:
	docker compose up --build $(SERVICES)

down:
	docker compose down -v

run:
	make build
	make up

restart:
	down build up