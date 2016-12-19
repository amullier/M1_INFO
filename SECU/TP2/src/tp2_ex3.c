/**
 * TP2 SECU - Exercice 1
 *
 * MULLIER Antoine
 */

#include <stdlib.h>
#include <stdio.h>
#include "string.h"

#define DETAILS 0 //Passer la variable à 1 pour obtenir des détails d'affichage

//Retourne le max d'un tableau unidimensionnel pour 0<=i<26
int max_val(int * t,int num_max){
	int val_cpt[26];
	int indice[26];
	int x,y;
	int i=0,j=0;

	//initialisation des tableaux intermédiaires de calcul
	for (i = 0; i < 26; i++){
		val_cpt[i] = t[i];
		indice[i] = i;
	}

	//Tri par insertion pour avoir les valeurs triées
	for (i = 0; i < 26; i++)
	{
		x = val_cpt[i];
		y = indice[i];

		j = i;
		while( (j > 0) && (val_cpt[j - 1] > x)){
			val_cpt[j] = val_cpt[j - 1];
			indice[j] = indice[j - 1];
			j = j - 1;
		}
        val_cpt[j] = x;
		indice[j] = y;

	}	
	
	//Tri par ordre croissant donc on prend la dernière valeur
	return indice[26-num_max];
}

//Retourne le tableau des n premiers maximum du tableau bidimensionnel
char ** max_val_bi(int t[26][26], int num_max){
	int  val_cpt[676];
	int  indice[676][2];
	int x,y1,y2,j=0;

	//Initialisation des tableaux de résultat
	for (int i = 0; i < 26; i++)
	{
		for (int j = 0; j < 26; j++)
		{
			indice[26*i+j][0] = i;
			indice[26*i+j][1] = j;
			val_cpt[26*i+j] = t[i][j];
		}
	}
	

	//Tri des tableaux selon la fréquence d'apparition des diagrammes
	for (int i = 0; i < 676; ++i)
	{
		x = val_cpt[i];
		y1 = indice[i][0];
		y2 = indice[i][1];

		j = i;
		while( (j > 0) && (val_cpt[j - 1] > x)){
			val_cpt[j] = val_cpt[j - 1];
			indice[j][0] = indice[j - 1][0];
			indice[j][1] = indice[j - 1][1];
			j = j - 1;
		}
        val_cpt[j] = x;
		indice[j][0] = y1;
		indice[j][1] = y2;

	}

	//Tableau de résultat
	char ** res =  (char **)malloc(num_max*sizeof(char *));
	
	j=0; //indice sur le tableau de résultat
	
	for (int i = 675; i > 675-num_max; i--)
	{
		res[j] = (char *)malloc(2*sizeof(char));
		res[j][0] = indice[i][0];
		res[j][1] = indice[i][1];
		j++;
	}

	return res;
}

/**
 * Ajoute la permutation au tableau des permutations
 * a devient un b suite a la permutation
 */
void add_permutation(char * p[26],char a,char b){
    (*p)[a-'a'] = b - 'a';
    if(DETAILS){
    	printf("Permutation de %c en %c\n",a,b);
    }

}

/**
 * Affichage du tableau de fréquence des lettres
 */
void affichage_tab(int * t){
	for (int i = 0; i < 26; i++){
		printf("%c : %d\n", 'a'+i,t[i]);
	}
}

/**
 * Affichage du tableau de fréquence des bigrammes
 */
void affichage_bitab(int  t[26][26] ){
	for (int i = 0; i < 26; i++){
		for (int j = 0; j < 26; j++){
			printf("%c%c : %d\n", 'a'+i,'a' + j, t[i][j]);
		}
	}
}

//Exercice 3
int main(int argc, char const *argv[]){

	if(argc<2){
		printf("Erreur fichier non renseigné.\nVeuillez passer en argument le fichier à décrypter.\n");
		return -1;
	}

	FILE* fichier = NULL;
	char car_lu ;
	int car_bigr1,car_bigr2;

	char lettres_freq[10] = {'e','a','s','i','t','u','r','n','l','o'};

	char bigrammes_freq[10][2] = {{'e','s'},{'d','e'},{'l','e'},{'e','n'},{'r','e'},{'n','t'},{'o','n'},{'e','r'},{'t','e'},{'e','l'}};

	char ** tab_tmp = NULL;

	/**
	 * Tableau de permutations résultant de notre cryptanalyse sur le texte
	 *
	 * T[i] = j     ===>     i va être permuté avec j
	 */
	char * permutation = (char *)malloc(26*sizeof(char));

	//Tableau de fréquence des lettres
	int frequence[26];

	/**
	 * 	Tableau de fréquences des bigrammes
	 *  T[i][j] = fréquence du bigramme ij
	 */
	int frequence_bigrammes[26][26];

	//Initialisation des valeurs des tableaux
	for (int i = 0; i < 26; ++i)
	{
		frequence[i] = 0;
		for (int j = 0; j < 26; j++)
		{
			frequence_bigrammes[i][j]=0;
		}
	}


	//Ouverture du fichier exercice3
	fichier = fopen(argv[1], "r+");
	if(fichier==NULL){
		printf("Le fichier renseigné n'est pas accessible en lecture.\n");
		return -1;
	}
	
	//Lecture du fichier caractère par caractère
	car_lu = fgetc(fichier);
	while (car_lu != EOF){ 
		//Ajout de la lettre dans le tableau de fréquences des lettres
		if((car_lu!=' ')&&(car_lu!='\n')){
			frequence[car_lu - 'a']	++;

			if(car_bigr1==0){
				car_bigr1 = car_lu - 'a';
			}
			else{
				//Ajout du bigramme dans le tableau des bigrammes
				car_bigr2 = car_lu - 'a';
				frequence_bigrammes[car_bigr1][car_bigr2] ++;

				car_bigr1 = car_bigr2;
				car_bigr2 = 0;
			}
		}
		//Si on rencontre un espace on remet à zéro le bigramme qu'on a put (commencer à ) lire
		else{
			car_bigr1=0;
			car_bigr2=0;
		}		

		car_lu = fgetc(fichier);
    }
    fclose(fichier);

    //Initialisation du tableau des permutations à l'identité
    for (int i = 0; i < 26; i++){
    	permutation[i] = i;
    }

    //affichage_tab(frequence); //Affichage de la table des fréquences de lettres

    //Pour la première lettre (par fréquence) on associe la lettre la plus fréquente en français
    if(DETAILS){
    	printf("Analyse de la fréquence des lettres simples:\n");
    }
    for (int i = 0; i < 1; i++)
    {
   		add_permutation(&permutation,max_val(frequence,i+1) + 'a',lettres_freq[i]);
    }

    //Analyse des bigrammes

    //Affichage des 10 premiers bigrammes 
    tab_tmp = max_val_bi(frequence_bigrammes,10);
    if(DETAILS){
    	printf("\nAnalyse de la fréquence des bigrammes:\n");
    	for (int i = 0; i < 10; i++)
	    {
	    	printf("Bigramme n°%d \t:    %c%c (%c%c en français)\n",i+1,tab_tmp[i][0]+'a',tab_tmp[i][1]+'a', bigrammes_freq[i][0],bigrammes_freq[i][1] );
	    }
    }
    
    
    /**
     * Grace a la cryptanalyse "à la main" on en déduit les permutations suivantes
     */
    add_permutation(&permutation,'a','h');
    add_permutation(&permutation,'b','t');
    add_permutation(&permutation,'d','f');
    add_permutation(&permutation,'c','a');
    add_permutation(&permutation,'e','p');
    add_permutation(&permutation,'f','i');
    add_permutation(&permutation,'g','x');
    add_permutation(&permutation,'h','w');
    add_permutation(&permutation,'i','b');
    add_permutation(&permutation,'j','l');
    add_permutation(&permutation,'k','q');
    add_permutation(&permutation,'l','z');
    add_permutation(&permutation,'m','o');
    add_permutation(&permutation,'n','d');
    add_permutation(&permutation,'o','u');
    add_permutation(&permutation,'p','y');
    add_permutation(&permutation,'q','r');
    add_permutation(&permutation,'r','g');
    add_permutation(&permutation,'s','n');
    add_permutation(&permutation,'t','k');
    //La permutation de u-> e a déjà été effectuée
    add_permutation(&permutation,'v','c');
    add_permutation(&permutation,'w','m');
    add_permutation(&permutation,'x','v');
    add_permutation(&permutation,'y','j');
    add_permutation(&permutation,'z','s');



    //Affichage de la permutation
    if(DETAILS){
	    printf("Etat de la permuation:\n");
	    for (int i = 0; i < 26; i++)
	    {
	    	printf("%c -> %c\n",i + 'a',permutation[i] + 'a');
	    }
	}

    //On applique la permutation trouvée au fichier
	fichier = fopen(argv[1], "r+");
	if(fichier==NULL){
		printf("Le fichier renseigné n'est pas accessible en lecture.\n");
		return -1;
	}
	car_lu = fgetc(fichier);

	while (car_lu != EOF){ 
		//Ajout de la lettre dans le tableau de fréquences des lettres
		if((car_lu!=' ')&&(car_lu!='\n')){
			printf("%c",permutation[car_lu - 'a'] + 'a');
		}	
		else{
			printf("%c",car_lu);
		}	

		car_lu = fgetc(fichier);
	}
	fclose(fichier);	  

	//1984 - Première Partie - Chapitre I - George Orwell
	return 0;
}