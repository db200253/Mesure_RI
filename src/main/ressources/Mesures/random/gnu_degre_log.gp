set terminal pngcairo enhanced color size 800,600
set output 'degree_distribution_log_plot.png'
set title 'Distribution des degrés'
set xlabel 'Degré'
set ylabel 'Nombre de nœuds'
set datafile separator ','

set logscale xy
set yrange[1e-6:1]

lambda = 6.63379430770874
poisson(k) = lambda ** k * exp(-lambda) / gamma(k + 1)

f(x) = lc - gamma * x
fit f(x) 'degreeDistribution.csv' using (log($1)):(log($2)) via lc, gamma

c = exp(lc)
power(k) = c * k ** (-gamma)


plot 'degreeDistribution.csv' using 1:2 with points title 'Distribution des degrés (log-log)', poisson(x) title 'Loi de poisson', power(x) title 'Loi de puissance'
