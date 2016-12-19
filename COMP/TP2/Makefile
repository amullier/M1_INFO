# -----------------------------------------------------------------------------
# CONFIGURATION
# Vérifiez bien les valeurs suivantes avant de procéder.
# -----------------------------------------------------------------------------

ANTLR_JAR=./lib/antlr-3.5.2-complete.jar
SRC_DIR=./src
BIN_DIR=./bin

# -----------------------------------------------------------------------------
# SCRIPT
# Cette partie n'a normalement pas besoin d'être modifiée.
# -----------------------------------------------------------------------------

CLASSPATH=$(SRC_DIR):$(ANTLR_JAR)
SRCS=$(wildcard $(SRC_DIR)/*.java)
OBJS=$(SRCS:.java=.class)

.PHONY: all clean clean_test

all: $(SRC_DIR)/VSLTreeParser.java $(SRC_DIR)/VSLParser.java $(SRC_DIR)/VSLLexer.java $(OBJS)

$(SRC_DIR)/%.class: $(SRC_DIR)/%.java
	javac -cp $(CLASSPATH) $(SRCS)

$(SRC_DIR)/VSLTreeParser.java: $(SRC_DIR)/VSLTreeParser.g $(SRC_DIR)/VSLParser.java VSLParser.tokens
	java -cp $(CLASSPATH) org.antlr.Tool $(SRC_DIR)/VSLTreeParser.g

$(SRC_DIR)/VSLParser.java: $(SRC_DIR)/VSLParser.g $(SRC_DIR)/VSLLexer.java VSLLexer.tokens
	java -cp $(CLASSPATH) org.antlr.Tool $(SRC_DIR)/VSLParser.g

%.tokens: $(SRC_DIR)/%.g
	java -cp $(CLASSPATH) org.antlr.Tool $^

$(SRC_DIR)/VSLLexer.java: $(SRC_DIR)/VSLLexer.g
	java -cp $(CLASSPATH) org.antlr.Tool $^

clean:
	rm -f $(SRC_DIR)/*.class $(SRC_DIR)/VSLTreeParser.java $(SRC_DIR)/VSLParser.java $(SRC_DIR)/VSLLexer.java *.tokens tmp.vsl_code

clean_test:
	rm -f tests/testlevel*/*.mips tests/testlevel*/*.mips.o tests/testlevel*/*.mips.s tests/testlevel*/*.vsm.o tests/testlevel*/*.vsm.s tests/testlevel*/*.mips_res tests/testlevel*/*.vsm_res tests/testlevel*/*.comp_res
	rm -f nachos/test/*

# -----------------------------------------------------------------------------
# TESTS DU BINOME
# -----------------------------------------------------------------------------

test_expressions : all
	java -cp $(CLASSPATH) VslComp tests/testperso/test_expressions1.vsl
	java -cp $(CLASSPATH) VslComp tests/testperso/test_expressions2.vsl
	java -cp $(CLASSPATH) VslComp tests/testlevel1/level1expr.vsl -debug

test_affectations : all
	java -cp $(CLASSPATH) VslComp tests/testperso/test_affectations.vsl
	java -cp $(CLASSPATH) VslComp tests/testlevel2/level2assign.vsl -debug

test_if : all
	java -cp $(CLASSPATH) VslComp tests/testperso/test_if1.vsl

test_while : all
    java -cp $(CLASSPATH) VslComp tests/testperso/test_while1.vsl

test_tab : all
	java -cp $(CLASSPATH) VslComp tests/testlevel3/level3tab2.vsl
	java -cp $(CLASSPATH) VslComp tests/testperso/test_tab1.vsl

test_fonction : all
	java -cp $(CLASSPATH) VslComp tests/testperso/test_fibo.vsl -debug

compilation_level1 : all
	for arg in `ls tests/testlevel1/*.vsl` ; do \
		echo "Compilation de : $$arg" ; \
		java -cp $(CLASSPATH) VslComp $$arg ; \
	done

compilation_level2 : all
	for arg in `ls tests/testlevel2/*.vsl` ; do \
		echo "Compilation de : $$arg" ; \
		java -cp $(CLASSPATH) VslComp $$arg ; \
	done

compilation_level3 : all
	for arg in `ls tests/testlevel3/*.vsl` ; do \
		echo "Compilation de : $$arg" ; \
		java -cp $(CLASSPATH) VslComp $$arg ; \
	done

compilation_level4 : all
	for arg in `ls tests/testlevel4/*.vsl` ; do \
		echo "Compilation de : $$arg" ; \
		java -cp $(CLASSPATH) VslComp $$arg ; \
	done