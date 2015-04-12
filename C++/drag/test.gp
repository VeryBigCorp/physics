#!/usr/bin/env gnuplot
set terminal epslatex "enhanced"
set output "exampleEPSLATEX.tex"
set label 1 "$\phi(x) = \frac{1}{\sqrt{2 \pi}} e^{-\frac{1}{2} x^2}$" at 1.2, 0.25
set label 2 "$\Phi(x) = \int_{-\infty}^x \phi(t) dt$" at 1.2, 0.8
set key top left Left
unset xtics
set xtics (' $\pi/2$' -pi/2, '$0$' 0, '$\pi/2$' pi/2 )
plot [-3:3] exp(-0.5*x**2)/sqrt(2*pi) title '$\phi(x)$', norm(x) title '$\Phi(x)$'
