#!/bin/sh
echo "\nTP1 Compilation\n"
echo "Suppression des fichiers précédents"
rm -f bin/*
rm *.tokens
echo "Fait.\n"
echo "Compilation de la grammaire"
./tools/g2java.sh src/TP1.g
echo "Fait.\n"
echo "Compilation des fichiers .java du répertoire ./src"
./tools/javac.sh
echo "Fait.\n"
