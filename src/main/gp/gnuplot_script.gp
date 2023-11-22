set terminal pngcairo enhanced color size 800,600
set output 'degree_distribution_plot.png'
set title 'Distribution des degrés'
set xlabel 'Degré'
set ylabel 'Nombre de nœuds'
set datafile separator ','

plot '../ressources/degreeDistribution.csv' using 1:2 with linespoints title 'Distribution des degrés'