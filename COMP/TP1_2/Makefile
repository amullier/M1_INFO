ANTLR_JAR=./lib/antlr-3.5.2-complete.jar


all: Partie1 Partie2 Partie3_1 Partie3_2

bin: 
	mkdir -p bin

####################################################################################################
#                                            PARTIE 1                                              #
####################################################################################################

Partie1 : bin bin/TP1Part1Lexer.class bin/TP1Part1Parser.class  bin/MainPart1.class 
	# - - - - - - - - - - - - Compilation de la Partie 1 terminée - - - - - - - - - - - - #

bin/MainPart1.class: src/MainPart1.java src/TP1Part1Lexer.java  src/TP1Part1Parser.java
	javac -d bin -cp $(ANTLR_JAR) src/MainPart1.java src/TP1Part1Lexer.java  src/TP1Part1Parser.java

bin/TP1Part1Lexer.class: src/TP1Part1Lexer.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part1Lexer.java

bin/TP1Part1Parser.class: src/TP1Part1Parser.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part1Parser.java

src/TP1Part1Lexer.java src/TP1Part1Parser.java : src/TP1Part1.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/TP1Part1.g


####################################################################################################
#                                            PARTIE 2                                              #
####################################################################################################

Partie2 : bin bin/TP1Part2Lexer.class bin/TP1Part2Parser.class bin/MainPart2.class 
	# - - - - - - - - - - - - Compilation de la Partie 2 terminée - - - - - - - - - - - - #

bin/MainPart2.class: src/MainPart2.java src/TP1Part2Lexer.java  src/TP1Part2Parser.java src/Arpenteur.java
	javac -d bin -cp $(ANTLR_JAR) src/MainPart2.java src/TP1Part2Lexer.java src/TP1Part2Parser.java src/Arpenteur.java

bin/TP1Part2Lexer.class: src/TP1Part2Lexer.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part2Lexer.java

bin/TP1Part2Parser.class: src/TP1Part2Parser.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part2Parser.java

bin/Arpenteur.class: src/Arpenteur.java
	#Arpenteur généré
	javac -d bin -cp $(ANTLR_JAR) src/Arpenteur.java 

src/TP1Part2Lexer.java src/TP1Part2Parser.java : src/TP1Part2.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/TP1Part2.g

src/Arpenteur.java : src/Arpenteur.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/Arpenteur.g

####################################################################################################
#                                            PARTIE 3.1                                            #
####################################################################################################

Partie3_1 : bin bin/TP1Part3_1Lexer.class bin/TP1Part3_1Parser.class bin/MainPart3_1.class 
	# - - - - - - - - - - - - Compilation de la Partie 3.1 terminée - - - - - - - - - - - - #

bin/MainPart3_1.class: src/MainPart3_1.java src/TP1Part3_1Lexer.java  src/TP1Part3_1Parser.java src/ArpenteurVersion.java
	javac -d bin -cp $(ANTLR_JAR) src/MainPart3_1.java src/TP1Part3_1Lexer.java src/TP1Part3_1Parser.java src/ArpenteurVersion.java

bin/TP1Part3_1Lexer.class: src/TP1Part3_1Lexer.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part3_1Lexer.java

bin/TP1Part3_1Parser.class: src/TP1Part3_1Parser.java
	javac -d bin -cp $(ANTLR_JAR) src/TP1Part3_1Parser.java

bin/ArpenteurVersion.class: src/ArpenteurVersion.java
	#Arpenteur généré
	javac -d bin -cp $(ANTLR_JAR) src/ArpenteurVersion.java 

src/TP1Part3_1Lexer.java src/TP1Part3_1Parser.java : src/TP1Part3_1.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/TP1Part3_1.g

src/ArpenteurVersion.java : src/ArpenteurVersion.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/ArpenteurVersion.g


####################################################################################################
#                                            PARTIE 3.2                                              #
####################################################################################################

Partie3_2 : bin bin/TP1Part2Lexer.class bin/TP1Part2Parser.class bin/MainPart3_2.class 
	# - - - - - - - - - - - - Compilation de la Partie 3.2 terminée - - - - - - - - - - - - #

bin/MainPart3_2.class: src/MainPart3_2.java src/TP1Part2Lexer.java  src/TP1Part2Parser.java src/ArpenteurXML.java
	javac -d bin -cp $(ANTLR_JAR) src/MainPart3_2.java src/TP1Part2Lexer.java src/TP1Part2Parser.java src/ArpenteurXML.java

bin/ArpenteurXML.class: src/ArpenteurXML.java
	javac -d bin -cp $(ANTLR_JAR) src/ArpenteurXML.java 

src/ArpenteurXML.java : src/ArpenteurXML.g
	java -classpath $(ANTLR_JAR) org.antlr.Tool src/ArpenteurXML.g


####################################################################################################
#                               LANCEMENT DES PROGRAMMES PRINCIPAUX                                #
####################################################################################################

run1: Partie1
	tools/run_tests_part1.sh

run2: Partie2
	tools/run_tests_part2.sh

run3_1:Partie3_1
	tools/run_tests_part3_1.sh

run3_2: Partie3_2
	tools/run_tests_part3_2.sh

clean : 
	rm -rf bin
	mkdir -p bin
	rm -f src/*Lexer*
	rm -f src/*Parser*
	rm -f src/Arpenteur*.java
	rm -f *tokens
	rm -f tests/*.ast
	rm -f tests/*.trad
	rm -f tests/*.xml
	rm -f tests/*.trad2

doc : 
	pandoc readme.md | lynx -stdin
