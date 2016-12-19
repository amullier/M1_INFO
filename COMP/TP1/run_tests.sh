#!/bin/sh
for arg in `ls tests/*.ttl`
do
	echo "Traduction du fichier $arg"
	tools/run.sh Main < $arg > $arg.trad
	echo "Fait.\n"
done