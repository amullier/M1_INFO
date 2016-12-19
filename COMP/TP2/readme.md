# Compilateur VSL+
#### Mullier Antoine et Sadok Romain
-------------------------------------------------------
La compilation du projet est gérée par le **Makefile** à l'aide des commandes suivantes : 

 - **make :** Compilation intégrale du projet
 - **make test_expressions**
 - **make test_affectations**
 - **make test_if**
 - **make test_while**
 - **make test_tab**
 - **make test_fonction :** Test de notre programme couvrant toutes les fonctionnalités implémentées (*suite de fibonacci*)
 - **make compilation_levelN : ** Remplacer N par un entier de 1 à 4 lancera la compilation des fichiers de tests fournis en fonction de la complexité des programmes VSL d'entrée.
 - **make clean :** Efface les .class et autres fichiers dû à la compilation du projet
 - **make clean_tests:** Efface les résultats de compilation du projet
 
*Remarque : Les paramètres de fonction de type tableau ne sont pas implémentés*

Pour l'exécution il suffit de se positionner sur le repertoire nachos et d'exécuter la commande **run_test_nachos.sh [fichier_test]**  (sans extension)
