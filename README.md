# I) Présentation du sujet

Durant ce TP, nous avons eu l'occasion de manipuler un graphe pour deux objectifs différents : faire des mesures et étudier la propagation dans ce réseau.
Le réseau en question correspond à un réseau de collaboration scientifique en informatique.

# II) Mesures de réseau d'interaction

Pour cette partie, plusieurs indicateurs seront intéressants à étudier : le degré moyen, le coefficient de clustering, etc... Cela nous donnera des données capitales pour la suite.

## 1) Mesures de base

En ce qui concerne les mesures de base, commencons par en donner quelques unes. Dans le graphe étudié, il y a 317 080 noeuds et 1 049 866 liens, le degré moyen est de 6.62208890914917 tandis que le coefficicient de clustering est de 0.6324308280637396.
Pour mettre en relief ce fameux coefficicient de clustering, calculons à combien il serait dans le cas d'un réseau aléatoire de même taille et même degré moyen :
Ci = degré moyen / nombre de noeuds -> Ci = 0,000020885
Dans le cas d'un réseau aléatoire de même taille et de même degré moyen, le coefficicient de clustering sera égal à 0,000020885.

## 2) Connexité

Dans notre cas, une méthode permet de vérifier si le réseau est connexe : il l'est, cependant ce ne sera pas le cas pour un réseau aléatoire de même taille et degré moyen.
Pour qu'un réseau aléatoire soit connexe, il faut que p0 (la probabilité qu'un noeud ait 0 lien) soit égal ou très proche de 0. On sait que p0 = (1-p)^(N-1), ainsi si le degré moyen = 6.62208890914917, p0 = 0.0013.
Pour que le réseau soit connexe on observe qu'il faut que le degré moyen soit supérieur ou égal à 14, dans ce cas p0 = 0,000000831, ce qui est assez proche de 0 pour que dans le cadre d'un réseau de cette taille, aucun noeud ne soit de degré 0.

## 3) Distribution des degrés

Un des meilleurs indicateurs pour comprendre un graphe est la distribution des degrés, cela permet d'avoir une idée de l'organisation des liens, nous pouvons utiliser plusieurs échelles pour étudier cela, voilà la distibution des degrés brute (les valeurs sont ici normalisées) : 


![Distribution des degrés](/src/main/ressources/Mesures/exp/degree_distribution_plot.png)


Et voilà la distribution des degrés en échelle log-log : 


![Distribution des degrés en échelle logarithmique](/src/main/ressources/Mesures/exp/degree_distribution_log_plot.png)


Nous observons que la distribution des degrés en échelle log-log ne forme pas une ligne droite mais semble suivre la loi de puissance, on peut donc estimer que l'on est en présence d'un réseau sans échelle.

## 4) Distribution des distances

Un autre moyen pour comprendre les interactions dans un réseau est l'étude des distances entre les noeuds, en ce qui concerne la distance moyenne pour notre réseau de collaboration, elle est égale à 4.5771937 ce qui est relativement petit : l'information dans le réseau devrait se transmettre rapidement.
La distance moyenne est largement inférieure à 6, cependant on observe dans les valeurs de distances que certaines sont supérieures à 6, bien qu'elles soient extrêmement minoritaires, cela suffit à dire que l'hypothèse des 6 degrés de séparation n'est pas vraie dans tous les cas.
On a calculé la distance moyenne, comparons la maintenant à ln(N)/ln(k) = 6,700611819 : on peut remarquer plusieurs choses, d'abord la distance moyenne est faible, cela est signe d'un réseau fortement connecté, aussi, 4.58 a le même ordre de grandeur que 6.70 ce qui laisse penser que ce réseau est bien un réseau petit monde.

Pour visualiser la distribution des distances, voici la courbe la représentant : 

![Distribution des distances](/src/main/ressources/Mesures/exp/distance_distribution_plot.png)

Avec cette échelle linéaire, on observe que le pic global se situe quasiment au niveau de la distance moyenne, de plus on distingue une forme de cloche caractéristique (si l'on fait abstraction de certains pics locaux) d'une loi normale de moyenne égale à la distance moyenne et d'écart type égal à (degré moyen - distance moyenne). On peut donc émettre l'hypothèse que les distances suivent cette loi normale.

## 5) Graphe random

Dans le graphe généré aléatoirement, il y a 317 087 noeuds et 1 049 017 liens, le degré moyen est de 6.6165876388549805 tandis que le coefficicient de clustering est de 2.2024673567699695E-5.
Le réseau n'est pas connexe (on a vu qu'il fallait un degré moyen égal à 14 ou plus pour que ce soit le cas).

En ce qui concerne la distribution des degrés, on a la courbe suivante : 

![Distribution des degrés](/src/main/ressources/Mesures/random/degree_distribution_plot.png)


Et voilà la distribution des degrés en échelle log-log : 


![Distribution des degrés en échelle logarithmique](/src/main/ressources/Mesures/random/degree_distribution_log_plot.png)

La distance moyenne est de 4.6141562, tandis que voilà la distribution des distances : 

![Distribution des distances](/src/main/ressources/Mesures/random/distance_distribution_plot.png)

## 6) Graphe de Barabasi-Albert

Dans le graphe généré avec la méthode d'attachement préférentiel, il y a 317 082 noeuds et 1 109 902 liens, le degré moyen est de 7.000725269317627 tandis que le coefficicient de clustering est de 4.418844952432335E-4.
Le réseau est connexe, la méthode de génération du graphe s'en assure.

En ce qui concerne la distribution des degrés, on a la courbe suivante : 

![Distribution des degrés](/src/main/ressources/Mesures/barabasi/degree_distribution_plot.png)


Et voilà la distribution des degrés en échelle log-log : 


![Distribution des degrés en échelle logarithmique](/src/main/ressources/Mesures/barabasi/degree_distribution_log_plot.png)


La distance moyenne est de 3.9236772, tandis que la distribution des distances est la suivante : 


![Distribution des distances](/src/main/ressources/Mesures/barabasi/distance_distribution_plot.png)

## 7) Comparaisons avec la théorie et le graphe experimental

En ce qui concerne le graphe aléatoire, on remarque d'abord que le coefficient de clustering est bien égal à celui calculé au tout début (0.00002) et que le réseau n'est pas connexe comme prévu. 
Ensuite, on voit que le graphe généré aléatoirement a une distribution des degrés qui suit une loi de poisson à l'échelle log log, cela nous confirme l'aléatoire et le manque de réalisme dû à l'absence d'individus fortement connecté aux autres : tous les individus ont sensiblement le même nombre de connexions et sont à une distance comprise entre 3 et 5 d'un autre individu (ce qui confirme l'hypothèse des 6 points de séparation).

Le graphe généré avec la méthode d'attachement préférentiel a lui une distribution des degrés beaucoup plus proche de notre réseau expérimental, elle n'est pas identique mais ressemblante, ce qui est logique puisque la méthode assure la construction d'un graphe autour d'individus fortement connectés pour représenter au mieux la formation de communautés. En ce qui concerne la distribution des distances, on peut aussi observer une ressemblance majeure avec le graphe experimental puisque l'on observe une loi normale de moyenne égale à la distance moyenne, cependant l'écart type lui, n'est pas égal à (degré moyen - distance moyenne).

De toute ces observations on en conclut que malgré quelques différences, le graphe généré avec la méthode d'attachement préférentiel est le plus proche de la réalité de notre réseau de collaboration, notamment en termes de création de communautés avec des individus fortement connectés, le graphe généré aléatoirement lui n'est pas pertinent pour étudier le comportement d'une population.

# III/ Propagation

Pour continuer l'étude de notre réseau de collaboration, nous allons simuler la propagation d'un virus dans les différents réseaux et sous différentes conditions, cela nous permettra de mieux comprendre l'architecture de nos réseaux et imaginer des solutions concrètes pour répondre à une information.

## 1) Quelques indicateurs

Dans notre scénario, le taux de propagation est égal à la probabilité de transmission (1/7) / taux de guérison (2/30), ainsi il est environ de 2.
Le seuil épidémique est lui égal au degré moyen k / la moyenne du carré des degrés dans le réseau k2, dans notre scénario se = 0.045, le seuil épidémique étant inférieur au taux de propagation, la maladie progresse.
Dans le cas d'un réseau aléatoire de même degré moyen, le seuil épidémique serait égal à 1/k+1 = 0,1311963.

## 2) Simulations

Nous avons determiné 3 manières de propager le virus, dans la première rien n'est fait pour empêcher la propagation, dans la deuxième, 50% de la population est convaincue de s'immuniser (immunisation aléatoire) et dans la troisième, 50% de la population convaint un voisin choisi aléatoirement de s'immuniser (immunisation séléctive).
Nous avons effectué la simulation plusieurs fois pour avoir le comportement typique, voici les résultats : 

![Epidémie](/src/main/ressources/Propagation/Epidemies/epidemie_plot.png)

Une conclusion saute aux yeux : l'immunisation séléctive est bien plus efficace, ce qui est logique, en effet on a vu plus tôt que le réseau était organisé en communauté, de cette manière, on est sûr que les noeuds ayant beaucoup de connexion ont beaucoup plus de probabilité d'être choisis pour être immunisés, cela renforce l'immunité de groupe puisque le virus ne peut pas circuler par ces individus.

## 3) Degrés moyens

Dans le cadre d'une évolution libre, le degré moyen est de 6.62, pour une immunisation aléatoire, le degré moyen est de 3.31 et enfin en cas d'immunisation séléctive, le degré moyen est de 2.97.
La différence entre immunisations aleatoires et séléctives provient du fait que dans le cas aléatoire, le choix du node immunisé est totalement aléatoire donc quasiment uniforme dans le graphe. Alors que dans le cas séléctif on choisit un voisin, les nodes ayant un degré superieur ont plus de chance d'être immunisés comme dit précédemment.

## 4) Seuils épidémiques

D'abord, on rappelle que dans le cas d'une évolution libre, le seuil épidémique était de 0.045, dans nos 2 situations d'immunisation, les seuils épidémiques passent respectivement à 0.073 pour le cas aléatoire et 0.204 pour le cas séléctif.

## 5) Simulations dans d'autres réseaux

Reprenons les simulations en utilisant les méthodes de génération de graphes précédemment étudiées, en ce qui concerne les résultats bruts, les voici : 

Pour un graphe généré aléatoirement : 

![Epidémie](/src/main/ressources/Propagation/Epidemies/epidemie_alea_plot.png)

Pour un graphe généré avec la méthode d'attachement préférentiel : 

![Epidémie](/src/main/ressources/Propagation/Epidemies/epidemie_ba_plot.png)

Nous pouvons remarquer que dans le cadre de l'aléatoire, l'immunisation séléctive ne marche pas du tout, cela est logique puisque pour rappel, les individus ont sensiblement le même nombre de connexion donc les individus "charnières" évoqués précédemment n'existe pas ici. Comme dit précédemment, ce genre de graphes n'est de toute façon pas pertinent si l'on souhaite représenter une population puisqu'il ne crée pas de communautés comme on peut l'observer dans la réalité.

En ce qui concerne la méthode d'attachement préférentiel, on peut voir que la propagation est sensiblement similaire au cas experimental, les deux immunisations fonctionnent avec la séléctive meilleure. Le point de différence se trouve dans les valeurs, dans le cas experimental, les immunisations sont meilleures puisque la proportion d'infectés non immunisés sont inférieures, cela est probablement lié à la distance moyenne plus petite dans ce graphe, ce qui permet une propagation plus rapide et plus efficace.
