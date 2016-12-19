CC=gcc
CFLAGS=-Wall

all : build bin/tp2_ex1.o bin/tp2_ex2.o bin/tp2_ex3.o 
	

bin/tp2_ex1.o : src/tp2_ex1.c
	$(CC) $(CFLAGS) src/tp2_ex1.c -o bin/tp2_ex1.o

bin/tp2_ex2.o : src/tp2_ex2.c
	$(CC) $(CFLAGS) src/tp2_ex2.c -o bin/tp2_ex2.o

bin/tp2_ex3.o : src/tp2_ex3.c
	$(CC) $(CFLAGS) src/tp2_ex3.c -o bin/tp2_ex3.o

run1 : bin/tp2_ex1.o tests/exercice1.txt
	echo "\nTP2 EX1 \t\t\t Mullier Antoine\n"
	./bin/tp2_ex1.o tests/exercice1.txt > tests/exercice1.plaintext
	cat tests/exercice1.plaintext

run2 : bin/tp2_ex2.o tests/exercice1.txt
	echo "\nTP2 EX1 \t\t\t Mullier Antoine\n"
	./bin/tp2_ex2.o tests/exercice2.txt > tests/exercice2.plaintext
	cat tests/exercice2.plaintext

run3 : bin/tp2_ex3.o tests/exercice3.txt
	echo "\nTP2 EX1 \t\t\t Mullier Antoine\n"
	./bin/tp2_ex3.o tests/exercice3.txt > tests/exercice3.plaintext
	cat tests/exercice3.plaintext

build :
	  mkdir -p bin

clean : 
	rm -f bin/*
	rm -f tests/*plaintext

doc : 
	cat readme.md