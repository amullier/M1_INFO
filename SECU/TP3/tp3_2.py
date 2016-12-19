# TP3 SECU - Exercice 2
#
# Mullier Antoine

import time
import random
import os
import math

#Crible d'erathosthene
def erathosthene(n):
	#On initialise la liste a [2] pour pouvoir traiter que les nombres impairs
	l = [2]	

	#Le premier nombre a traiter est donc 3
	num = 3

	# Booleen de verification pour savoir si num n'est pas divisible par un 
	# membre de la liste l
	b = True
	while len(l)<n:
		for x in l:
			if(num%x==0): # Si le nombre est divisible par un des membres
				b= False  # On ne l'ajoute pas dans la liste
				break
		if(b):			  # Si b alors num n'est pas divisible par un des membres de l
			l.append(num)

		num = num + 2	  # De 2 en 2 pour eviter les nombres pairs divisibles par 2
		b = True          # Remise a vrai du boolean pour le prochain num

	return l

#On recupere toutes les fonctions necessaires test_primalite_pgp
def puissance_modulaire(a, n, m):
    result = 1
    while n>0:
        if n&1>0:
            result = (result*a)%m
        n >>= 1
        a = (a*a)%m
    return result

def test_primalite_fermat(a,p):
    return puissance_modulaire(a, p-1, p)==1

def test_primalite_pgp(p):
    for a in [2,3,5,7]:
        if(test_primalite_fermat(a,p)==False):
            return False
    return True


def premiers(n):
	l = [2,3,5,7] #On initialise la liste aux premiers nombres premiers
	num = 9;
	while len(l)<n:
		if(test_primalite_pgp(num)):
			l.append(num)
		
		num = num + 2

	return l

#Affichage des differents algorithmes :

n = 100

debut = time.time()
l1 = erathosthene(n)
fin = time.time()

# print "Temps :" + str(fin - debut)

debut = time.time()
l2 = premiers(n)
fin = time.time()

# print "Temps :" + str(fin - debut)
print "\n"
print l1
print "\n"
print l2
# print l1==l2