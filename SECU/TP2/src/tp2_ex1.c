/**
 * TP2 SECU - Exercice 1
 *
 * MULLIER Antoine
 */

#include <stdlib.h>
#include <stdio.h>

//Fonction pour trouver le max d'un tableau
int max_i(int t[26]){
	int indice = 0,max=0;
	for (int i = 0; i < 26; i++)
	{
		if(max<=t[i]){
			max = t[i];
			indice = i;
		}
	}
	
	return indice;
}

//Modulo 26 positif
char mod26(char a){
	char b;
	if(a%26>=0){
		b = a%26;
	}
	else{
		b = (a%26)+26;
	}
	return b;
}

//Exercice 1
int main(int argc, char const *argv[]){

	if(argc<2){
		printf("Erreur fichier non renseigné.\nVeuillez passer en argument le fichier à décrypter.\n");
		return -1;
	}


	//Décalage du chiffrement de César
	int decalage = 0; 
	char car_lu;
	int val;

	//Tableau de fréquence des caractères
	int frequence[26];

	//Inialisation du tableau des fréquences
	for (int i = 0; i < 26; i++){
		frequence[i] = 0;
	}

	FILE* fichier = NULL;
	fichier = fopen(argv[1], "r+");
	if(fichier==NULL){
		printf("Le fichier renseigné n'est pas accessible en lecture.\n");
		return -1;
	}

	car_lu = fgetc(fichier);

	while (car_lu != EOF){     
		//On compte la valeur dans le tableau     
		frequence[car_lu - 'a'] ++;
		car_lu = fgetc(fichier);
    }
	fclose(fichier);

	//On récupère la valeur la plus fréquente du texte
	car_lu = max_i(frequence);
	
	//Calcul du décalage
	decalage = 26 - mod26((car_lu) - ('e'-'a')); //Lettre e la plus fréquente

	//On réouvre le fichier
	fichier = fopen(argv[1], "r+");
	if(fichier==NULL){
		printf("Le fichier renseigné n'est pas accessible en lecture.\n");
		return -1;
	}
	car_lu = fgetc(fichier);
	while (car_lu != EOF){ 
		//On applique le décalage pour chaque caractère	
		val = (mod26((car_lu-'a') + decalage))+'a';
		printf("%c",val);
		car_lu = fgetc(fichier);

        }
	fclose(fichier);

	//Jules César - Shakespeare Acte I
	return 0;
}