set terminal pngcairo enhanced color size 800,600
set output 'epidemie_ba_plot.png'
set title "Evolution de l'epidemie dans un réseau de Barabasi-Albert"
set xlabel 'Step'
set ylabel 'Infectés dans la population non immunisée (%)'
set datafile separator ','
set key right bottom

plot '../barabasi/nothing/cases.csv' using 1:2 with linespoints title 'Evolution libre', '../barabasi/alea/cases.csv' using 1:2 with linespoints title 'Immunisation aleatoire', '../barabasi/select/cases.csv' using 1:2 with linespoints title 'Immunisation selective'