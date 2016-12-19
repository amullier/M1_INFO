#!/bin/sh
rm /tests/*.xml

echo "PARTIE 3.2 : Génération d'un fichier XML à partir du fichier .ttl"
echo ""

for arg in `ls tests/*.ttl`
do
	echo "Génération de l'XML à partir du fichier $arg"
	tools/run.sh MainPart3_2 < $arg > $arg.xml
	echo "Résultat :"
	cat $arg.xml
	echo ""
	echo "Fait."
done
