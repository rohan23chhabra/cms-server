.DEFAULT_GOAL := run

.PHONY: build
build:
	@echo "Installing dependencies..."
	@mvn clean install -U -s settings.xml

.PHONY: run
run:
	@echo "Launching Spring REST API Service..."
	@mvn spring-boot:run

.PHONY: test
test:
	@echo "Running server tests..."
	@mvn test

.PHONY: fmt
fmt:
	prettier --write "**/*.java"

.PHONY: install-formatter
install-formatter:
	@npm list --global prettier || npm install -g prettier prettier-plugin-java
	@echo "In an event of a permission error from npm, run sudo make install-formatter"