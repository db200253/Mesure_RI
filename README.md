# I) Présentation du sujet

Durant ce TP, nous avons eu l'occasion de manipuler un graphe pour deux objectifs différents : faire des mesures et étudier la propagation dans ce réseau.
Le réseau en question correspond à un réseau de collaboration scientifique en informatique.

# II) Mesures de réseau d'interaction

Pour cette partie, plusieurs indicateurs seront intéressants à étudier : le degré moyen, le coefficient de clustering, etc... Cela nous donnera des données capitales pour la suite.

## 1) Mesures de base

En ce qui concerne les mesures de base, commencons par en donner quelques unes. Dans le graphe étudié, il y a 317 080 noeuds et 1 049 866 liens, le degré moyen est de 6.62208890914917 tandis que le coefficicient de clustering est de 0.6324308280637396.
Pour mettre en relief ce fameux coefficicient de clustering, calculons à combien il serait dans le cas d'un réseau aléatoire de même taille et même degré moyen :
Ci = <k>/N -> Ci = degré moyen / nombre de noeuds -> Ci = 0,000020885
Dans le cas d'un réseau aléatoire de même taille et de même degré moyen, le coefficicient de clustering sera égal à 0,000020885.

Le réseau est connexe
