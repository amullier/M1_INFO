#include "rc4.h"

/*********************************************************************
					LISTE POUR GERER LES PROBA SUR LAMBDA

 *********************************************************************/

//Allocation mémoire d'une nouvelle cellule
count_list * new_count_list(uint8_t key){
	count_list * new_value = (count_list *)malloc(sizeof(count_list));
	new_value->key = key;
	new_value->cpt = 1;
	new_value->next = NULL;

	return new_value;
}

//Désallocation mémoire d'une liste
void free_count_list(count_list ** head){
	count_list * p = *head;
	if(p!=NULL){
		(*head) = p;
		p = (*head)->next;
		free(*head);
	}
}

//Ajout d'une valeur dans la liste (si déjà présent +1 au compteur)
void add_count_list(count_list ** head,uint8_t key){
	count_list * p1,*p2;
	if((*head)==NULL){
		(*head) = new_count_list(key);
	}
	else{
		p1 = (*head);
		p2 = (*head)->next;
		while((p2!=NULL)&&(p1->key!=key)){
			p1 = p2;
			p2 = p1->next;
		}

		if(p1->key==key){
			(p1->cpt) = (p1->cpt) +1;
		}
		else{
			(p1->next) = new_count_list(key);
		}

	}
}

//Affichage d'un élément
void display_elmt_count_list(count_list * e){
	if(e!=NULL){
		// printf("key : %02x \t\t occurence : %d\n",e->key,e->cpt);
		printf("%02x,%d\n",e->key,e->cpt);
	}
	else{
		printf("NULL\n");
	}
}

//Affichage de la liste
void display_count_list(count_list * head){
	while(head != NULL){
		display_elmt_count_list(head);
		head = head->next;
	}
}

//On récupère la valeur maximale
count_list * get_max_count_list(count_list * head){
	count_list * max = head;
	while(head != NULL){
		if((max->cpt)<(head->cpt)){
			max = head;
		}
		head = head->next;
	}
	return max;
}

//Affichage de la valeur maximale de la liste
void display_max_count_list( count_list * head){
	//display_elmt_count_list(get_max_count_list(head));
	count_list * p = get_max_count_list(head);
	float cpt =  ((float)(p->cpt))/NB_BOUCLES;
	cpt *= 100;
	printf("%02x : %.2f%%\n", p->key,cpt);
}

//Retourne la valeur du max de la liste
uint8_t max_value_count_list(count_list * head){
	count_list * max = get_max_count_list(head);
	if(max==NULL){
		return 0;
	}
	else{
		return (max->key);
	}
}
