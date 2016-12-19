#include <stdio.h>
#include <stdint.h>
#include <fcntl.h>
#include <stdlib.h>

#define LONGUEUR_CLE 	16
#define NB_BOUCLES 		800 //Nombre de boucles pour obtenir lamba avec suffisamment de précision

//Structure de données permettant de compteur le nombre d'occurence d'une valeur
typedef struct count_list{
	uint8_t key;
	int cpt;
	struct count_list * next;
} count_list;


//Ajout d'une valeur dans la liste de données si déjà présent +1 au compteur
void add_count_list(count_list ** ,uint8_t);

//Retourne la valeur la plus fréquente dans la liste pointée par head
uint8_t max_value_count_list(count_list *);

//Affiche la liste en entier par doublet (key,cpt)
void display_count_list(count_list *);

//Affiche la valeur la plus fréquente dans la liste pointée par head
void display_max_count_list( count_list *);

//Désallocation mémoire de la liste
void free_count_list(count_list ** head);