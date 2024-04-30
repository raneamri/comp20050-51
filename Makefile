SRC_DIR := src
BIN_DIR := bin
LIB_DIR := /Library/Java/javafx-sdk-21.0.2/lib
DOC_DIR := doc
JAR_FILE := blackbox+.jar
MANIFEST := META-INF/MANIFEST.MF

run:
	javac -cp "$(BIN_DIR):$(LIB_DIR)/*" -d "$(BIN_DIR)" $(SRC_DIR)/blackbox/*.java \
	&& java --enable-preview --module-path "$(LIB_DIR)" --add-modules javafx.controls,javafx.fxml -cp "$(BIN_DIR)" blackbox.Main

build:
	javac -cp "$(BIN_DIR):$(LIB_DIR)/*" -d "$(BIN_DIR)" $(SRC_DIR)/blackbox/*.java

doc:
	javadoc -private -sourcepath $(SRC_DIR) -d $(DOC_DIR) -classpath "$(LIB_DIR)/*" $(SRC_DIR)/*.java

cleandoc:
	rm -r $(DOC_DIR)

jar: build
	jar cmvf $(MANIFEST) $(JAR_FILE) -C $(BIN_DIR) . -C $(LIB_DIR) .

runjar:
	java -jar --module-path $(LIB_DIR) --add-modules javafx.controls,javafx.fxml blackbox+.jar

cleanjar:
	rm $(JAR_FILE)