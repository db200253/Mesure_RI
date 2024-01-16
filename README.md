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

Un des meilleurs indicateurs pour comprendre un graphe est la distribution des degrés, cela permet d'avoir une idée de l'organisation des liens, nous pouvons utiliser plusieurs échelles pour étudier cela, voilà la distibution des degrés brute : 
![]("src/main/ressources/exp/degree_distribution_plot.png")
