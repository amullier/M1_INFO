#!/bin/sh

# javac.sh: compiles all .java files in the current directory
# exemple: ./javac.sh

# Tests if the ANTLR file is readable
ANTLR_JAR=./lib/antlr-3.5.2-complete.jar
if [ ! -r $ANTLR_JAR ]
then
  echo "error: could not read ANTLR Jar file: $ANTLR_JAR"
  echo "check if file exists and is readable"
  exit 2
fi

export CLASSPATH=$ANTLR_JAR:.:$CLASSPATH
javac -d bin src/*.java

