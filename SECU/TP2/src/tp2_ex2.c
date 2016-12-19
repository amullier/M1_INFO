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
char mod26(int a){
	char b;
	if((a%26)>=0){
		b = a%26;
	}
	else{
		b = (a%26)+26;
	}
	return b;
}

//Inverse modulaire
int inverse_modulaire(int alpha, int gamma){
	int lambda=1, mu=0, q,r, s=0, t=1, tmp;
	while(gamma > 0){
		q = (int)alpha/gamma; //division euclidienne
		r = alpha % gamma;
		alpha = gamma;
		gamma = r;
		tmp = s;
		s = lambda - q*s;
		lambda = tmp;
		tmp = t;
		t = mu - q*t;
		mu = tmp;
	}
	return lambda;
}

//Exercice 2
int main(int argc, char const *argv[]){

	if(argc<2){
		printf("Erreur fichier non renseigné.\nVeuillez passer en argument le fichier à décrypter.\n");
		return -1;
	}

	char car_lu,car_max,car_decrypt;
	int a,b;

	//Tableau de fréquence des caractères
	int frequence[26];

	//Inialisation du tableau des fréquences
	for (int i = 0; i < 26; i++){
		frequence[i] = 0;
	}	

	//Tableau des valeurs possibles pour le coef a
	int val_possibles[12] = {1,3,5,7,9,11,15,17,19,21,23,25};

	//Lecture du fichier
	FILE* fichier = NULL;
	fichier = fopen(argv[1], "r+");
	if(fichier==NULL){
		printf("Le fichier renseigné n'est pas accessible en lecture.\n");
		return -1;
	}
	car_lu = fgetc(fichier);

	while (car_lu != EOF){     
		//On ajoute la valeur dans le tableau des fréquences
		frequence[car_lu - 'a'] ++;
		car_lu = fgetc(fichier);
    }
	fclose(fichier);

	//On récupère la valeur la plus fréquente du texte
	car_max = max_i(frequence);

	//Boucle pour tester le coef a parmi toutes les valeurs possibles
	for(int i=1;i<2;i++){	//Réglé sur la solution mettre int i=0 et i<12 pour tester toutes les solutions
		
		//On récupère les coefficients avec la valeurs de a choisie
		a = val_possibles[i];
		b = (car_max) - a * ('e'-'a');
	

		//On décrypte le fichier avec les coefs calculés
		fichier = fopen(argv[1], "r+");
		if(fichier==NULL){
			printf("Le fichier renseigné n'est pas accessible en lecture.\n");
			return -1;
		}
		car_lu = fgetc(fichier);

		//Parcours du fichier pour compter les lettres
		while (car_lu != EOF){     
			car_decrypt = mod26(inverse_modulaire(a,26)*((car_lu - 'a')- b));
			printf("%c",car_decrypt+'a'); //Affichage déchiffré
			car_lu = fgetc(fichier); //lecture du chiffré
		}
		printf("\n\n");
		fclose(fichier);		

	}

	//Le dormeur du Val - Arthur Rimbaud
	return 0;
}