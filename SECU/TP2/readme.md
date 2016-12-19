------------------------------------------------------------
 TP2 - SECU                                 Mullier Antoine
------------------------------------------------------------

Pour lancer les éxécutions automatiques des exercices faire:
        *make run1 pour l'exercice 1*
        *make run2 pour l'exercice 2*
        *make run3 pour l'exercice 3*

*make clean* supprimera les éxécutables du répertoire /bin

NB: Les résultats des appels à *make run* sont sauvegardés
    dans le répertoire /tests dans les fichiers *.plaintext

------------------------------------------------------------

## Explications pour la partie 3 : 

Pour effectuer la cryptanalyse de l'exercice 3, on a d'abord
effectuer l'analyse des différentes lettres prises
indépendamment. L'objectif était d'avoir un tableau avec la 
fréquence des différents caractères cryptés et d'en déduire 
certaines lettres : le 'e' et le 'a'.

Ce qui m'a permis de trouver deux permutations de lettres
qu'en pratique j'ai stocké dans un tableau pour
effectuer la permutuation en fin de programme avec notre
texte plus ou moins déchiffré.

Cependant, cela m'a permis de trouver que 2 lettres donc
j'ai (comme cela nous a été demandé) effectué l'analyse 
des bigrammes.

On en déduit les bigrammes les plus fréquents et donc 
quelques permutations de plus ont pu être trouvées.

Pour arriver au terme de la cryptanalyse de ce texte est de
trouver toutes les permutations de lettre le travail à la 
main c'est avéré nécessaire. 

Pour les trouver à la main, il faut commencer par faire des
suppositions sur les mots courts qui arrive le plus
fréquemment et après quelques essais d'autres mots plus
longs voient le jour.

_Exemple:_
Le mot "vaeveux" devient cheveux en ajoutant les
permutations associées

De plus, la sémantique du texte nous indique des éléments.

_Exemple:_
Le bout de texte "il n avait aucun wmpen" permet de supposer
que "wmpen" devient en fait "moyen"

Ainsi, nous avons pu établir un tableau de permutation 
transformant le chiffré en clair.

------------------------------------------------------------