package gus.game5.core.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilPolynom {

	public static double[] findRoots(double[] coef) {
		if(coef.length==0) return null;
		if(coef.length==1) return findRoots0(coef);
		if(coef.length==2) return findRoots1(coef);
		if(coef.length==3) return findRoots2(coef);
		if(coef.length==4) return findRoots3(coef);
		if(coef.length==5) return findRoots4(coef);
		
		return null;
	}
	
	// résolution de a = 0 ...
	public static double[] findRoots0(double[] coef) {
		if(coef[0]==0) return null; //infinité de racines
		return new double[] {};
	}
	
	// résolution de ax+b = 0
	public static double[] findRoots1(double[] coef) {
		if(coef[1]==0) return findRoots0(coef);
		return new double[] {-coef[0]/coef[1]};
	}
	
	// résolution de ax2 + bx + c = 0
	public static double[] findRoots2(double[] coef) {
		if(coef[2]==0) return findRoots1(coef);
		
		double a = coef[2];
		double b = coef[1];
		double c = coef[0];
		
		double discriminant = b*b - 4*a*c;
		if(discriminant<0) return new double[] {};
		if(discriminant==0) {
			double x0 = -b / (2*a);
			return new double[] {x0};
		}
		double discriminant2 = Math.sqrt(discriminant);
		double x1 = (-b + discriminant2) / (2*a);
		double x2 = (-b - discriminant2) / (2*a);
		return new double[] {x1,x2};
	}

	// résolution de ax3 + bx2 + cx + d = 0 (dans certains cas)
	public static double[] findRoots3(double[] coef) {
		if(coef[3]==0) return findRoots2(coef);
		
		if(coef[0]==0) {
			//cas : ax3 + bx2 + cx = 0
			double[] r = findRoots2(new double[] {coef[1], coef[2], coef[3]});
			if(r.length==0) return new double[] {0};
			return new double[] {r[0], r[0], 0};
		}
		// on ne sait pas...
		return null;
	}
	
	// résolution de ax4 + bx3 + cx2 + dx + e = 0 (dans certains cas)
	public static double[] findRoots4(double[] coef) {
		if(coef[4]==0) return findRoots3(coef);

		if(coef[1]==0 && coef[3]==0) {
			//cas : ax4 + cx2 + e = 0
			//on résout aX2 + cX + e = 0 puis x2 = racine
			double[] rr = findRoots2(new double[] {coef[0], coef[2], coef[4]});
			Set<Double> set = new HashSet<>();
			for(double r : rr) {
				if(r==0) set.add(0.0);
				else if(r>0) {
					double r2 = Math.sqrt(r);
					set.add(r2);
					set.add(-r2);
				}
			}
			return UtilArrayDouble.collectDouble2(set);
		}
		
		return null;
	}
	
	/*
	 * SUM POLYNOMS
	 */
	
	public static double[] sum(List<double[]> coefList) {
		int nb = coefList.size();
		int max = UtilList.collectMaxInt(coefList, a->a.length);
		double[] coefSum = new double[max];
		for(int i=0;i<max;i++) {
			coefSum[i] = 0;
			for(int j=0;j<nb;j++) {
				double[] coef = coefList.get(i);
				if(coef.length>i) coefSum[i] += coef[i]; 
			}
		}
		return coefSum;
	}
	
	/*
	 * MULTIPLY BY FACTOR
	 */
	
	public static double[] multiply(double[] coef, double factor) {
		double[] newCoef = new double[coef.length];
		for(int i=0;i<coef.length;i++) {
			newCoef[i] = coef[i] * factor;
		}
		return newCoef;
	}
}
