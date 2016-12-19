# TP4 SECU 
#### Antoine Mullier
---------------------------------------------------------

### Exercice 1
On considère un fichier "message" dans lequel est stocké la 
donnée à traiter

#### 1.
**SHA-1 :**
<pre><code>openssl  dgst -sha1 message > message.sha1</code></pre>

**RIPE-MD160 :**
<pre><code>openssl  dgst -ripemd160 message > message.ripemd160</code></pre>

**MD5 :**
<pre><code>openssl  dgst -md5 message > message.md5</code></pre>

#### 2.
**MD5SUM :**
<pre><code>md5sum message > message.md5sum</code></pre>

####  3.
**BLOWFISH :**
<pre><code>openssl enc -bf -in message -out message.blowfish</pre></code>

**3DES :**
<pre><code>openssl enc -des-ede3-cfb -in message -out message.3des</code></pre>

**CAST5 :**
<pre><code>openssl enc -cast5-cfb -in message -out message.cast5</code></pre>

#### 4.
Hachage du message original avec SHA-256:
<pre><code>openssl dgst -sha256 message >  message.sha256</code></pre>

Déchiffrage BLOWFISH :
<pre><code>openssl enc -d -bf -in message.blowfish -out message_bf.dec</code></pre>

Déchiffrage 3DES : 
<pre><code>openssl enc -d -des-ede3-cfb -in message.3des -out message_3des.dec</code></pre>

Déchiffrage CAST5 :
<pre><code>openssl enc -d -cast5-cfb -in message.cast5 -out message_cast5.dec</code></pre>

_Remarque:_ En comparant les hachés avec la commande 5.1 (SHA-256) on
retrouve les mêmes messages

#### 5.
On considère l'image image.jpg téléchargée sur internet

Chiffrement avec **AES** :
<pre><code>openssl enc -aes-256-cfb -in image.jpg -out image.aes</code></pre>

#### 6.
Génération de clé RSA de taille 2048 :
<pre><code>openssl genrsa -out rsa.out 2048</code></pre>

On récupère la clé publique:
<pre><code>openssl rsa -in rsa.out -pubout -out rsa.pub</code></pre>

#### 7.
On stocke la clé publique de l'autre binôme dans le fichier
rsa2.pub

#### 8.
<pre><code>echo "cle_utilisee" | openssl rsautl -encrypt -inkey -pubin rsa2.pub -out key.rsacrypt</code></pre>

#### 9.
On déchiffre le message reçu avec notre clé privée:
<pre><code>openssl rsautl -decrypt -inkey rsa.out -in key.rsacrypt -out key</code></pre>

Une fois la clé AES déchiffrée on déchiffre :
<pre><code>openssl enc -d -aes-256-cfb -in image.aes -out image_aes.jpg</code></pre>

On saisira la clé déchiffrée quand openssl le demandera

============================================================

### Exercice 2

#### 1. 
On a 3 certificats présent pour https://www.google.fr

#### 2. 
GeoTrust Global CA

#### 3. 
Pas de suite cryptographique trouvée avec Firefox

#### 4. 
SHA-256

#### 5. 
ANSI X9.62 elliptic curve prime256v1 (aussi appelé secp256r1, NIST P-256)

#### 6. 
Pas d'autres algorithmes trouvés

#### 7.1 
On a 3 certificats   

#### 7.2 
VeriSign Universal Root Certification Authority

#### 7.3 
Pas de suite cryptographique trouvée avec Firefox

#### 7.4 
SHA-256

#### 7.5 
Chiffrement PKCS #1 RSA

#### 7.6
Pas d'autres algorithmes trouvés

#### 8.1 
3 certificats

#### 8.2 
VeriSign Class 3 Public Primary Certification Authority - G5

#### 8.3 
Pas de suite cryptographique trouvée avec Firefox

#### 8.4 
SHA-256

#### 8.5 
Chiffrement PKCS #1 RSA

#### 8.6
Pas d'autres algorithmes trouvés

#### 9.
<pre><code>openssl speed aes-256-cbc</code></pre>
<table>
	<thead>
	<tr>
		<td>Type</td>
		<td>14 bytes</td>
		<td>64 bytes</td>
		<td>256 bytes</td>
		<td>1024 bytes</td>
		<td> 8192 bytes</td>
	</tr>
	</thead>
	<tr>
		<td>aes-256 cbc</td>
		<td>73368.55k</td>
		<td>79033.94k</td>
		<td>81155.24k</td>
		<td>81636.01k</td>
		<td>81813.50k</td>
	</tr>
</table>

<pre><code>openssl speed sha256</code></pre>
<table>
	<thead>
	<tr>
		<td>Type</td>
		<td>14 bytes</td>
		<td>64 bytes</td>
		<td>256 bytes</td>
		<td>1024 bytes</td>
		<td> 8192 bytes</td>
	</tr>
	</thead>
	<tr>
		<td>sha256</td>
		<td>55368.03k</td>
		<td>124113.24k</td>
		<td>237461.85k</td>
		<td>300979.20k</td>
		<td>331390.98k</td>
	</tr>
</table>

------------------------------------------------------------

<pre><code>openssl speed ecdsap256</code></pre>

<table>
	<thead>
	<tr>
		<td>Type</td>
		<td>sign</td>
		<td>verify</td>
		<td>sign/s</td>
		<td>verify/s</td>
	</tr>
	</thead>
	<tr>
		<td>256 bit ecdsa (nistp256) </td>
		<td>0.0001s</td>
		<td>0.0001s</td>
		<td>19261.4</td>
		<td>7703.3</td>
	</tr>
</table>

<pre><code>openssl speed rsa</code></pre>
<table>
	<thead>
	<tr>
		<td>Type</td>
		<td>sign</td>
		<td>verify</td>
		<td>sign/s</td>
		<td>verify/s</td>
	</tr>
	</thead>
	<tr>
		<td>rsa  512 bits</td>
		<td>0.000061s</td>
		<td>0.000005s</td>
		<td>16419.9</td>
		<td>221256.1</td>
	</tr>
	<tr>
		<td>rsa  1024 bits</td>
		<td>0.000175s</td>
		<td>0.000011s</td>
		<td>5718.6</td>
		<td>89222.5</td>
	</tr>
	<tr>
		<td>rsa  2048 bits</td>
		<td>0.000805s</td>
		<td>0.000035s</td>
		<td>1242.4</td>
		<td>28471.2</td>
	</tr>
	<tr>         
		<td>rsa  4096 bits</td>
		<td>0.008985s</td>
		<td>0.000132s</td>
		<td>111.3</td>
		<td>7565.2</td>
	</tr>
</table>

### Exercice 3:

#### 1.
Génération des clés:
	<pre><code>openssl genrsa -out Keys.pem 2048</code></pre>
Vérification:
  	<pre><code>openssl rsa -in rsa.out -text -noout</code></pre>

#### 2.
Exportation de la clé publique dans PKey.pem
  	<pre><code>openssl rsa -in Keys.pem -pubout -out PKey.pem</code></pre>

#### 11.
Il faut utiliser sa clé privée : **private/privkey.pem**

#### 13. 
Le fichier  **serial** contient un entier qui s'incrémente en fonction du nombre de génération de certificats
