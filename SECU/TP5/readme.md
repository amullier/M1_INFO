# TP4 SECU 
#### Antoine Mullier
---------------------------------------------------------

### Exercice 1
On a booté sur un live CD (Kali linux) afin d'avoir un accès administrateur pour obtenir le contrôle sur les fichiers habituellement protégés (shadow et passwd).
On a ensuite utilisé _John the ripper_ pour décrypter le mot de passe de la session **root** à partir de shadow et passwd 
**Résulat : 123456789**

*John the ripper* utilise son dictionnaire en priorité et si cela ne suffit pas il passe en brute force.
En choisissant un mot de passe peu commun avec des caractères spéciaux, majuscules, minuscules (par exemple : "C/45+m!@~aJ") *John the ripper* devrait mettre beaucoup de temps à le décrypter car il ne devrait pas être dans son dictionnaire. 

En estimant que *John the ripper* prenne 2 ans à le décrypter on conviendra que changer son mot de passe tous les ans est sûr.

### Exercice 2
#### Numeric SQL injection:

La requete était de la forme :
<pre><code>SELECT * FROM users WHERE login=101</code></pre>

Comme on voulait obtenir l'affichage pour tous les différents logins il fallait que la condition du **WHERE** soit vraie tout le temps.

On a donc changer la requête comme ceci :
<pre><code>SELECT * FROM users WHERE login=101 OR 1=1</code></pre>

Et on a bien **"login=101 OR 1=1"** qui rend toujours vrai.

#### String SQL injection:

La requête était de la forme :
<pre><code>SELECT * FROM users WHERE login=101 AND password='mon_password'</code></pre>

Mais password='mon_password' était faux puisqu'on ne connaissait pas le password. En rajoutant, une condition toujours vraie on s'assure d'avoir une requête qui rende un résultat.

<pre><code>SELECT * FROM users WHERE login=101 AND password='mon_password' OR 1=1'</code></pre>

#### Authentification flaws

On nous demandait le nombre TAC2 alors qu'on connaissait seulement
TAC1 dans le formulaire on avait une variable nous donnant le nombre
du champ demandé. 
En le passant à 1 on pouvait remettre le champ que l'on connaissait déjà.


#### XSS attack
Nous avons éditer le profil de Tom en ajoutant un petit script JS 
affichant une pop-up avec la fonction alert().

En insérant :
<pre><code>&#x3C;script&#x3E;alert(&#x22;Alerte !&#x22;);&#x3C;/script&#x3E;</code></pre> 
On remarque que lors de l'affichage écran du profil (quelque soit l'utilisateur) la pop-up apparaît.

#### Insecure communication
Le mot de passe affiché dans le champ HTML correspond au code source:
<pre><code>&#x3C;input value=&#x22;sniffy&#x22; type=&#x22;password&#x22;&#x3E;&#x3C;/input&#x3E;</code></pre>

On a donc le mot de passe en clair **"sniffy"**
