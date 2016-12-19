# TP3 SECU - Exercice 1
#
# Mullier Antoine

import random   #Pour gerer les nombres aleatoires
import os       #Pour initialiser le random

#Initialisation de la graine pour le random
random.seed(os.urandom(128))

#Algorithme d'euclide etendu renvoyant l'inverse modulaire et le pgcd
def euclide_etendu(a, b):
    lamb = 1
    mu = 0
    s = 0
    t = 1
    while (b > 0):
        q = a/b
        r = a % b
        a = b
        b = r
        tmp = s
        s = lamb - q*s
        lamb = tmp
        tmp = t
        t = mu - q*t
        mu = tmp
    return lamb,a

#Test de l'algorithme d'euclide etendu
# a , b = euclide_etendu(5,24)
# print "a=" + str(a) + " b=" + str(b)


#QUESTION 1 : Definition de la puissance modulaire
def puissance_modulaire(a, n, m):
    result = 1
    while n>0:
        if n&1>0:
            result = (result*a)%m
        n >>= 1
        a = (a*a)%m
    return result

# Tests pour la puissance modulaire
# print puissance_modulaire(51611,321,19)
# print "Valeur attendue : 1"

# print puissance_modulaire(1614,216,981)
# print "Valeur attendue : 873"

# print puissance_modulaire(879,154,98)
# print "Valeur attendue : 67"

# print "\n\n"


# Definition de la puissance (exponentiation rapide)
def puissance(a, n):
    result = 1
    while n>0:
        if n&1>0:
            result = (result*a)
        n >>= 1
        a = (a*a)
    return result


# QUESTION 2 : Test de primalite de fermat
def test_primalite_fermat(a,p):
    return puissance_modulaire(a, p-1, p)==1

# Tests pour le test de primalite de fermat
# print test_primalite_fermat(16,3)
# print "Valeur attendue : True"

# print test_primalite_fermat(18,3)
# print "Valeur attendue : False"

# print test_primalite_fermat(27,11)
# print "Valeur attendue : True"

# print "\n\n"


# QUESTION 3 : Test de primalite PGP
def test_primalite_pgp(p):
    for a in [2,3,5,7]:
        if(test_primalite_fermat(a,p)==False):
            return False
    return True

# Tests pour le test de primalite PGP
# print test_primalite_pgp(11)
# print "Valeur attendue : True"

# print test_primalite_pgp(13)
# print "Valeur attendue : True"

# print test_primalite_pgp(127)
# print "Valeur attendue : True"

# print test_primalite_pgp(126)
# print "Valeur attendue : False"

# print "\n\n"

# QUESTION 4 : Fonction qui rend un nombre aleatoire de n bits
def aleatoire_avec_nbits(b):
    nb_alea = random.randint(0,puissance(2,b)) 
    while test_primalite_pgp(nb_alea)==False:
        nb_alea = random.randint(0,puissance(2,b)) 
    return nb_alea

# Tests pour le generateur de nombre aleatoire
# print aleatoire_avec_nbits(5)


# QUESTION 5 : Generateur aleatoire de cle RSA
def generateur_aleatoire(b):

    # On choisit deux nombres premiers
    p = aleatoire_avec_nbits(b)
    q = aleatoire_avec_nbits(b)
    
    # On s'assure qu'ils soient differents
    while q == p:
        q = aleatoire_avec_nbits(b)
       
    # On calcule le produit de p et q
    N = p*q

    # Theoreme d'Euler
    phi_n = (p-1)*(q-1)

    # On prend un e aletoire de b bits
    e = aleatoire_avec_nbits(b)
    pgcd , d = euclide_etendu(e,phi_n)

    #On s'assure que les conditions soient remplies
    while ((e<2) or (e >= phi_n) or (pgcd!=1)):
        e = aleatoire_avec_nbits(b)
        d,pgcd = euclide_etendu(e,phi_n)
        d = d%phi_n
        pass

    #Affichage de phi_n et (N - phi_n)
    # print   "\nphi_n=\n" + str(phi_n)
    # print   "\nN- phi_n=\n" + str(N-phi_n)
    # print "\n"

    #On retourne toutes les valeurs trouvees
    return (e,d,N,p,q)


# Fonction de chiffrements & dechiffrement RSA
def chiffrementRSA(m,e,N):
    return puissance_modulaire(m,e,N)

def dechiffrementRSA(c,d,N):
    return puissance_modulaire(c,d,N)


# Tests pour le generateur de cles

# On genere les cles
(e,d,N,p,q) = generateur_aleatoire(512)

# Affichage des cles
print  "e=\n" + str(e)
print  "d=\n" + str(d)

#On chiffre le message m=10
m = 10
print "\nRSA sur le message : m=" + str(m)
c = chiffrementRSA(m,e,N)
print "chiffre: \n" + str(c) + "\n"

# On dechiffre le chiffre c
print "dechiffre:" + str(dechiffrementRSA(c,d,N))



