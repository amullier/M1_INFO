#!/bin/sh
rm /tests/*.ast

echo "\nPARTIE 2 : Génération des AST pour les fichiers .ttl du répertoire tests/ \n"

for arg in `ls tests/*.ttl`
do
	echo "Génération de l'AST du fichier $arg"
	tools/run.sh MainPart2 < $arg > $arg.ast
	echo "\nRésultat :\n"
	cat $arg.ast
	echo ""
	echo "Fait.\n"
done