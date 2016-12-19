---------------------------------------
 Mullier Antoine                   TP7
---------------------------------------

# AH
Q1.
Le protocole AH permet d'avoir une authentification des données
transmises. Il garantit aussi l'intégrité des données.
Ainsi sur deux sites A et B on peut utiliser le
protocole AH si les informations ne sont pas sensibles.

Q2.
On voit que les paquets sont bien passés par le tunnel
car on a un "Authentification Header" indiqué clairement
dans la trame.

Q3.
+----+----+--------------------+
+ IP | AH |        DATA        +
+----+----+--------------------+


# ESP
Q1.
Le protocole ESP permet d'ajouter une surcouche de chiffrement
au protocole AH. On peut donc imaginer une communication de 
messages secrets entre Alice et Bob à travers deux réseaux différents.

Q2.
On voit que les paquets sont bien passés par le tunnel
car on a un "Authentification Header" indiqué clairement
dans la trame. De plus, l'ajout du protocole ESP est indiqué
dans la fin de la trame par "Encapsulating Security Payload"

Q3.
+----+----+--------------+-----+
+ IP | AH |     DATA     | ESP +
+----+----+--------------+-----+

# Questions supplémentaires :

Q1.
Le protocole ESP utilise du chiffrement pour permettre d'avoir
la confidentialité des messages. AH permet seulement d'avoir 
l'authetification.

Q2.
Pour AH les informations importantes pour un espion seront les données transmises.
Car avec AH on ne garantit pas l'intégrité des données.

Pour ESP, les données du paquet ont chiffrées cependant l'entete du paquet 
n'est pas chiffré et contient des informations potentiellement intéréssantes.

Q3.
AH permet de garantir l'authenticité des données donc avec une attaque type
Man-int-the-middle on pourra certes récupérer les données transmises 
mais on ne pourra pas se faire passer pour la victime de l'attaque.

Le protocole ESP garantit la confidentialité des données. Cependant sans le 
protocole AH il ne peut garantir l'authenticité des données. L'attaque
MIM est donc une attaque susceptible de fonctionner pour se faire passer 
pour quelqu'un d'autre.

Q4.
Il faut que lorsque le décryptage des données chiffrées actuellement ne soit
plus des données critiques quand les technologiques de décryptage 
permettront une telle avancée.
En ce qui concerne les paquets ESP, l'attaquant devrait stocker tous les
paquets chiffrés transmis pour pouvoir les déchiffrer dans plusieurs 
années.
Il faut s'assurer de prévoir les avancées en cryptograpgie en utilisant 
les dernières technologies les plus sûres pour chiffrer ses données.
Par exemple, prévoir l'éventuelle arrivée de l'ordinateur quantique en allongeant
la taille des clés.