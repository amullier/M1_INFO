#!/bin/sh

#test argument
if [ -z "$1" ]
then
    echo "Usage : ./run_test_nachos.sh"
    echo "\n Voici les fichiers du répertoire nachos/test : "
    ls -lah test/*.s
	exit
fi


echo "Compilation du fichier $1.s en fichier executable:"
./asm2bin.sh $1

echo ""
echo "Execution du programme $1 avec nachos:"
./exec.sh  $1


