#!/bin/sh
rm /tests/*.trad2

echo "\nPARTIE 3.1 :\n"

for arg in `ls tests/*.ttl2`
do
	echo "Génération des Noeuds anonymes du fichier $arg"
	tools/run.sh MainPart3_1 < $arg > $arg.trad2
	echo "\nRésultat :\n"
	cat $arg.trad2
	echo ""
	echo "Fait.\n"
done