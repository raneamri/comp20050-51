SRC_DIR := src
BIN_DIR := bin
LIB_DIR := /Library/Java/javafx-sdk-21.0.2/lib

run:
	javac -cp "$(BIN_DIR):$(LIB_DIR)/*" -d "$(BIN_DIR)" $(SRC_DIR)/*.java \
	&& java --module-path "$(LIB_DIR)" --add-modules javafx.controls,javafx.fxml -cp "$(BIN_DIR)" Main

build:
	javac -cp "$(BIN_DIR):$(LIB_DIR)/*" -d "$(BIN_DIR)" $(SRC_DIR)/*.java
