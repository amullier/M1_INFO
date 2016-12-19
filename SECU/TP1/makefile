CC = gcc
CFLAGS = -Wall -g
 
all : bin/list.o bin/rc4.o
	gcc -o bin/rc4 bin/list.o bin/rc4.o


bin/list.o : src/list.c
	gcc $(CFLAGS) -I./include -c src/list.c -o bin/list.o


bin/rc4.o : src/rc4.c bin/list.o
	gcc $(CFLAGS) -I./include -c src/rc4.c -o bin/rc4.o

run : all
	bin/rc4

clean : 
	rm -f bin/*
