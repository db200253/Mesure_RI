set terminal pngcairo enhanced color size 800,600
set output 'epidemie_plot.png'
set title "Evolution de l'epidemie"
set xlabel 'Step'
set ylabel 'Infectés dans la population non immunisée (%)'
set datafile separator ','

plot '../nothing/cases.csv' using 1:2 with linespoints title 'Evolution libre', '../alea/cases.csv' using 1:2 with linespoints title 'Immunisation aleatoire', '../select/cases.csv' using 1:2 with linespoints title 'Immunisation selective'
