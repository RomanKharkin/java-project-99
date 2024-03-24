


setup:
	gradle wrapper --gradle-version 8.4

clean:
	./gradlew clean

build:
	./gradlew clean build

run-dist:
	./build/install/bin/-h

install:
	./gradlew clean install

run-install-dist: install run-dist

run:
	./gradlew run

run-dist:
	./build/install/bin/app

stop:
	./gradlew stop

report:
	./gradlew jacocoTestReport

generate-migrations:
	./gradlew generateMigrations

.PHONY: build