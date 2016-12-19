#!/bin/sh
rm /tests/*.trad

echo "\nPARTIE 1 : Traduction des fichiers .ttl du répertoire tests/ \n"

for arg in `ls tests/*.ttl`
do
	echo "Traduction du fichier $arg"
	tools/run.sh MainPart1 < $arg > $arg.trad
	echo "\nRésultat :\n"
	cat $arg.trad
	echo ""
	echo "Fait.\n"
done