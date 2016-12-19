------------------------------------------------------------
 TP3 - SECU                                 Mullier Antoine
------------------------------------------------------------

Explications de l'exercice 2:

 - La complexité du crible d'érathosthène

Premièrement, la boucle "for x in l" contient au plus n
éléments

De plus, le théorème des nombres premiers nous indique que
le i-ème nombre premier est approchant de n*ln(n)

Donc la boucle while fera n * (n * ln(n)) tours

Donc le programme principal aura une complexité de :

	O(n³ * ln(n)) c'est à dire O(n³)
