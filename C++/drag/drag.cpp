#include "../lib/Vector.cpp"
#include "../lib/gnuplot_i.hpp"
#include <sstream>
#include <string>
#include <stdlib.h>
#include <thread>
#include <vector>
#include <system_error>
#include <mutex>

/*
#include <stdarg.h>  // For va_start, etc.
#include <memory>    // For std::unique_ptr
*/

#define DT 	.0005
#define C_d	.1
#define A 	1 		// Cross-sectional area (m^2)
#define m	100		// mass (kg)
#define PI	3.14159265358979

using std::cout;
using std::endl;

double p(double h){ // Air density at height h
	return 1.23*pow(1-6.5e-3*h/293.15, 2.5);
}

int max(int a, int b){
	if(a > b) return a;
	return b;
}

int min(int a, int b){
	if(a < b) return a;
	return b;
}

static std::mutex barrier;
bool threads_initialized = false;
void calc_drag(double V, double angle, double Cd, int n, std::vector<std::vector<double> > &x_p, std::vector<std::vector<double> > &y_p, std::vector<std::vector<double> > &vx_p, std::vector<std::vector<double> > &vy_p, std::vector<std::vector<double> > &t, std::vector<double> &ranges){
	//cout << "Thread C_d = " << arg->C_d << " instantiated.\n" << endl;
	Vector x(0,500);		// Position vector
	Vector v(V,angle, true);	// Velocity vector
	Vector a(0,0);			// Acceleration vector
	//return;
	Vector g(0,-9.81);		// Gravity
	Vector cpy;
	int i = 0;
	double coeff = .5*p(v.y)*Cd*A/m;
	while(!threads_initialized);
	do {			
		a = cpy.set(g) - v*coeff*v.len();	// Calculate net force

		v += cpy.set(a)*DT;			// Integrate a
		x += cpy.set(v)*DT;			// Integrate v
		coeff = .5*p(v.y)*Cd*A/m;		// Air density correction
		if(i++ % 10 == 0){
			//cout << "Pushing t = " << &t << "; x_p = " << &x_p << "y_p = " << &y_p << "vx_p = " << &vx_p << "; vy_p = " << &vy_p;
			std::lock_guard<std::mutex> block_threads_until_finish_this_job(barrier);
			t[n].push_back(i*DT);
			x_p[n].push_back(x.x);
			y_p[n].push_back(x.y);
			vx_p[n].push_back(v.x);
			vy_p[n].push_back(v.y);
		}
	} while(x.y > 0);
	// Tangent line range approximation
	//std::lock_guard<std::mutex> block_threads_until_finish_this_job(barrier);
	ranges.push_back(x.x);
	//ranges.push_back(-y_p[n][y_p[n].size()-2]*(x_p[n][x_p.size()-1]-x_p[n][x_p.size()-2])/(y_p[n][y_p.size()-1]-y_p[n][y_p.size()-2]) + x_p[n][x_p[n].size()-2]);
	cout << "Thread finished. " <<  endl;
	return;
}

template <typename T>
std::string to_string(T const& value) {
    std::stringstream sstr;
    sstr << value;
    return sstr.str();
}

int main(){
	std::vector<std::vector<double> > x_p, y_p;

	std::vector<std::vector<double> > vx_p, vy_p;

	std::vector<std::vector<double> > t;

	std::vector<std::thread> _threads;

	std::vector<double> ranges;
	//std::vector<drag_info_t> _structs;

	try {
		Gnuplot plot("Position");
		plot.set_style("lines");
		Vector cpy;
		double theta = 0;
		double V = 30;
		int i = 0;
		for(int n = 0;theta < PI/2; theta += .001, n++){
			x_p.push_back(std::vector<double>());
			y_p.push_back(std::vector<double>());
			vx_p.push_back(std::vector<double>());
			vy_p.push_back(std::vector<double>());
			t.push_back(std::vector<double>());
			_threads.push_back(std::thread(calc_drag,V,theta,C_d,n,std::ref(x_p),std::ref(y_p),std::ref(vx_p),std::ref(vy_p),std::ref(t), std::ref(ranges)));
			cout << "Creating thread " << &_threads[n] << endl;
		}
		threads_initialized = true;

		int n = 0;
		for(auto &th : _threads){
			//cout << "joining thread " << th << endl;
			th.join();
			cout << "Joined." << endl;
		}

		double maxRange = 0;
		int index = 0;
		for(double &range : ranges){
			if(range > maxRange){
				maxRange = range;
				index = i;
			}
			i++;
		}


		plot.savetops("dragPosition_density");

		for(int n = max(0,index-3); n < min(x_p.size()-1, index + 3); n++){
				plot.plot_xy(x_p[n], y_p[n], "θ = "+to_string(.001*n));
		}
		
		plot.set_style("lines");
		plot.set_title("Range of varying drag coefficients");
		plot.set_xlabel("x");
		plot.set_ylabel("y");
		plot.replot();


		/*plot.reset_all();
		plot.set_title("Velocities of varying drag coefficients");
		plot.set_xlabel("t");
		plot.set_ylabel("Vy");
		plot.set_style("lines");
		for(int n = max(0,index-x_p.size()-3); n < max(x_p.size()-1, index + 3); n++){
			if(i % 100 == 0)
				plot.plot_xy(t[n], vy_p[n], "θ = "+to_string(.1*i));
		}
		plot.savetops("dragVelocities_correction");
		plot.replot();*/
		cout << "Maximum range: " << maxRange << " m, θ = " << .001*index << endl;
	} catch(GnuplotException ex){
		cout << ex.what() << endl;	
	} catch(const std::system_error& e){
		cout << "Caught error code: " << e.code() << " meaning " << e.what() << endl;	
	}

	
	return 0;	
}
