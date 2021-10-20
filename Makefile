SHELL=bash

BUILD=build
BIN_DIR?=.

.PHONY: build
build:
	@mkdir -p $(BUILD)/$(BIN_DIR)
	go build -o $(BUILD)/$(BIN_DIR)/dp-api-poc main.go

.PHONY: debug
debug:
	HUMAN_LOG=1 go run -race main.go

.PHONY: test
test:
	go test -race -cover ./...

.PHONY: test-component
test-component:
	@echo "no component tests to run"

.PHONY: lint
lint:
	@echo "no linter to run"

.PHONY: audit
audit:
	go list -m all | nancy sleuth