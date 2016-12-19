#include "rc4.h"

void swap(uint8_t *a, uint8_t *b){
	//swap value of a and b
	uint8_t tmp = *a;
	*a = *b;
	*b = tmp;
}

uint8_t array_search(uint8_t S[256], uint8_t val){
	//search value "val" in the array "S"
	uint8_t i;
	for(i=0;i<256;i++){
		if(S[i]==val){
			return i;
		}
	}
	printf("Valeur non trouvée\n");
	return 0;
}

void ivGen(uint8_t iv[16]){
	//Generate random iv
	
	int byte_count = 16;
	uint8_t data[16];
	FILE *fp;
	fp = fopen("/dev/urandom", "r");
	fread(&data, 1, byte_count, fp);
	fclose(fp);
	
	
	for(int i = 0; i < 16; i++){
		iv[i] = data[i];
	}
}

void ivGen_FixByte01(uint8_t * iv, uint8_t byte0, uint8_t byte1){
	//Generate random iv but with iv[0] = byte0 and iv[1] = byte1
	
	int byte_count = 16;
	uint8_t data[16];
	FILE *fp;
	fp = fopen("/dev/urandom", "r");
	fread(&data, 1, byte_count, fp);
	fclose(fp);
	
	
	for(int i = 0; i < 16; i++){
		iv[i] = data[i];
	}
	iv[0] = byte0;
	iv[1] = byte1;
}

void initialisation(uint8_t K[256], uint8_t S[256]){
	//Initialize S with K
	uint8_t j=0;
	int i;
	for(i=0;i<256;i++){
		S[i]=i;
	}
	for(i=0;i<256;i++){
		j = j+S[i] + K[i%256];
		swap(&(S[i]),&(S[j]));
	}
}

void RC4(uint8_t *message, uint32_t len_message, uint8_t key[16], uint8_t iv[16], uint8_t *out){
	//Encrypt "message" of length "len_message" using "key" and "iv", and put it in "out"
	int i=0;
	int j=0;

	uint8_t K[256];
	uint8_t S[256];
	uint8_t index,z;

	//K[i] = iv[i] sur les 16 premiers
	for(i=0;i<16;i++){
		K[i] = iv[i];
	}

	for(i=16;i<256;i++){
		K[i] = key[i%16];
	}

	initialisation(K,S);
	
	for(int cpt=0;cpt<len_message;cpt++){
		i = (i+1)%256;
		j = (j+S[i])%256;
		swap(&(S[i]),&(S[j]));
		
		index = S[i] + S[j];
		z = S[index];
		out[cpt] = z ^ message[cpt];
	}

}


int main(){
	uint8_t message[16] = {0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x88, 0x99, 0xaa, 0xbb, 0xcc, 0xdd, 0xee, 0xff};
	uint8_t out[16];
	uint32_t len_message = 16;
	
	uint8_t key[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
	uint8_t iv[16] = {0x15, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f};
	


	printf("\n\nTP1 SECU : RC4\t\t\t\t\tAntoine Mullier\n\n");

	//Affichage de la clé originale
	printf("Key:\t\t");
	for(int i = 0; i < 16; i++){
		printf("%02x ",key[i]);
	}
	printf("\n\n");

	int l = 16,cpt;
	int i =0,j=0,lambda=0,verif=1;
	uint8_t S[256];
	uint8_t K[256];
	uint8_t z0=0;

	//Boucle pour parcourir obtenir les key[l] avec 0<=l<16
	for (l=16; l < 16+LONGUEUR_CLE; l++){	 

		//liste de résultat initialiser à la liste
		count_list * stats_list = NULL;

		for (cpt = 0; cpt < NB_BOUCLES; cpt++){

			//Génération d'un message
			ivGen(message);

			//Génération d'un iv avec iv[0]=l et iv[1]=255
			ivGen_FixByte01(iv,l,255);

			//Chiffrement du message
			RC4(message, len_message, key, iv, out);

			//On initialise la permutation S à l'identité
			for(i=0;i<256;i++){
				S[i]=i;
			}

			//On initialise les premières valeurs de K avec l'iv (les autres valeurs sont connues)
			for (i = 0; i < len_message; i++){
				K[i] = iv[i];
			}			

			//On calcule l'état de S après l-1 tours
			j=0;
			for(i=0;i<l;i++){
				j = (j+S[i] + K[i])%256;
				swap(&(S[i]),&(S[j]));
			}

			//On fait le XOR du couple clair chiffré pour avoir le premier octet de la suite chiffrante
			z0 = message[0]^out[0];

			//On récupère la valeur de lambda
			lambda = (array_search(S,z0) - j - S[l])%256;	

			//On ajoute la valeur à la liste de résultats
			add_count_list(&stats_list,lambda);
		}

		//On récupère la valeur la plus fréquente du calcul précédent
		K[l] = max_value_count_list(stats_list);

		//Affichage de l'octet trouvé avec le pourcentage d'apparition
		//display_max_count_list(stats_list);

		//Désallocation mémoire de la liste
		free_count_list(&stats_list);
	}
	//Affichage de la clé
	printf("Key2:\t\t");
	for(i = 16; i < 16+LONGUEUR_CLE; i++){
		printf("%02x ",K[i]);
		if(K[i]!=key[i-LONGUEUR_CLE]){
			verif = 0;
		}
	}
	printf("\n\n");

	if(verif){
		printf("La clé originale a été retrouvée.\n\n");
	}
	return 0;
}
