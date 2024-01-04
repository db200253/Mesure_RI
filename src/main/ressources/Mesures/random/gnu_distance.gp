set terminal pngcairo enhanced color size 800,600
set output 'distance_distribution_log_plot.png'

set title "Distribution des distances dans le réseau"
set xlabel "Distances"
set ylabel "Fréquence"

set logscale y

plot 'distanceDistribution.csv' using 1:(1) smooth kdensity  title "Distribution des distances (log) dans le réseau"