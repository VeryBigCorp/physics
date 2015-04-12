package org.verybigcorp.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Fraction {
	public static Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);
	public static Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);
	MathContext mc = new MathContext(10, RoundingMode.HALF_EVEN);
	private BigInteger numerator, denominator;
	private boolean mixed;
	public Fraction(BigInteger numerator, BigInteger denominator){
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public Fraction(BigDecimal num, BigDecimal den){
		int scale = java.lang.Math.max(num.precision(), den.precision());
		BigDecimal multiplicand = Math.pow10(scale);
		Fraction f = new Fraction(num.multiply(multiplicand).toBigInteger(), den.multiply(multiplicand).toBigInteger()).simplify();
		setNumerator(f.getNumerator());
		setDenominator(f.getDenominator());

		System.out.println("frac" + this);
	}
	
	
	public Fraction(){
		numerator = BigInteger.ZERO;
		denominator = BigInteger.ONE;
	}
	
	public Fraction add(Fraction b){
		Fraction res = new Fraction();
		res.setNumerator(getNumerator().multiply(b.getDenominator()).add(b.getNumerator().multiply(getDenominator())));
		res.setDenominator(getDenominator().multiply(b.getDenominator()));
		return res;
	}
	
	public Fraction subtract(Fraction b){
		return this.add(b.negate());
	}
	
	public Fraction add(BigInteger b){
		return this.add(new Fraction(b, BigInteger.ONE));
	}
	
	public Fraction multiply(Fraction b){
		Fraction res = new Fraction();
		res.setNumerator(b.getNumerator().multiply(getNumerator()));
		res.setDenominator(getDenominator().multiply(b.getDenominator()));
		return res;
	}
	
	public Fraction multiply(BigInteger b){
		return new Fraction(getNumerator().multiply(b), getDenominator());
	}
	
	public Fraction divide(Fraction b){
		return this.multiply(b.reciprocal());
	}
	
	public Fraction divide(BigInteger b){
		return Fraction.fromFrac(this).multiply(new Fraction(BigInteger.ONE, b));
	}
	
	public BigInteger getNumerator(){
		return numerator;
	}
	
	public void setNumerator(BigInteger num){
		numerator = num;
	}
	
	public BigInteger getDenominator(){
		return denominator;
	}
	
	public void setDenominator(BigInteger den){
		denominator = den;
	}
	
	public Fraction simplify(){
		BigInteger gcf = gcf(numerator, denominator);
		Fraction s = new Fraction(numerator.divide(gcf), denominator.divide(gcf));
		s.multiply(BigInteger.valueOf(s.numerator.signum() * s.denominator.signum()));
		return s;
	}
	
	public BigDecimal evaluate(){
		return new BigDecimal(numerator).divide(new BigDecimal(denominator), mc).stripTrailingZeros();
	}
	
	public Fraction reciprocal(){
		return new Fraction(getDenominator(), getNumerator());
	}
	
	public Fraction negate(){
		Fraction n = this;
		n.numerator = n.numerator.negate();
		return n;
	}
	
	public String toString(){
		String s = "";
		if(mixed){
			BigInteger b = evaluate().toBigInteger();
			BigDecimal rem = evaluate().remainder(BigDecimal.ONE);
			s = b + " " + Fraction.fromDec(rem);
		} else {
			s =  numerator.compareTo(BigInteger.valueOf(0)) == 0 ? "0" : numerator + (denominator.compareTo(BigInteger.ONE) == 0 ? "" : "/"+denominator);
		}
		return s;
	}
	
	public static Fraction fromFrac(Fraction a){
		return new Fraction(a.getNumerator(), a.getDenominator());
	}
	
	public Fraction setMixed(boolean mixed){
		this.mixed = mixed;
		return this;
	}
	
	public static BigInteger gcf(BigInteger b, BigInteger c){
		BigInteger factor = BigInteger.ONE;
		BigInteger a = b.compareTo(c) == 1? c : b;
		BigInteger inc = Math.mod(a, BigInteger.valueOf(3)).add(Math.mod(a, BigInteger.valueOf(2))).compareTo(BigInteger.ZERO) != 0 ? BigInteger.valueOf(2) : BigInteger.ONE;
		for(BigInteger i = BigInteger.ONE; i.compareTo(a) <= 0; i = i.add(inc)){
			if(Math.mod(b, i).compareTo(BigInteger.ZERO) + Math.mod(c, i).compareTo(BigInteger.ZERO) == 0){ // common factor
				if(i.compareTo(factor) == 1)
					factor = i;
			}
		}
		return factor;
	}
	
	public BigInteger gcf(){
		return gcf(numerator, denominator);
	}
	
	public static Fraction fromDec(BigDecimal b){
		int scale = b.precision();
		b = b.movePointRight(b.precision());
		return new Fraction(b.toBigInteger(), Math.pow10(scale+1).toBigInteger());
	}
	
	public boolean divisibleBy(BigInteger divisor){
		return getNumerator().mod(divisor).compareTo(BigInteger.ZERO) == 0;
	}
	
	public Fraction mod(Fraction b){
		if(this.greaterThan(b)){
			return this.subtract(b.multiply(this.divide(b).floor())).simplify();
		}
		return this;
	}
	
	public Fraction pow(Fraction b){ // (a/b)^(c/d) = ((a/b)^(1/d))^(c)
		if(b.getNumerator().compareTo(BigInteger.ONE) == 0 && b.getDenominator().compareTo(BigInteger.ZERO) != 0){ // (a/b)^(1/d)
			// Newton's Method
			System.out.println("newton's doe ("+this+")^("+b+")");
			BigDecimal oneOver = new BigDecimal(b.getDenominator());
			System.out.println("One over = " + oneOver);
			Fraction pow = Fraction.ONE;
			for(int i = 0; i < 10; i++){
				Fraction f = Math.pow(pow, Fraction.ONE.multiply(oneOver.toBigInteger()));
				f = f.subtract(this);
				f = f.divide(pow);
				f = f.multiply(oneOver.toBigInteger());
				pow = pow.subtract(f);
				System.out.println(pow.evaluate());
			}
			return pow;
		} else if(b.getDenominator().compareTo(BigInteger.ONE) == 0){ // (a/b)^c
			BigInteger gcf = Fraction.gcf(getNumerator(), getDenominator());
			Fraction pow = new Fraction(Math.pow(getNumerator(), b.evaluate().toBigInteger()), Math.pow(getDenominator(), b.evaluate().toBigInteger()));
			//System.out.println(b.evaluate().toBigInteger());
			for(BigInteger i = BigInteger.ZERO; i.compareTo(b.evaluate().toBigInteger().multiply(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)){
				pow.setNumerator(pow.getNumerator().divide(gcf));
				pow.setDenominator(pow.getDenominator().divide(gcf));
			}
			return pow;
		} else {
			System.out.println(this+ "^("+new Fraction(b.getNumerator(), BigInteger.ONE)+")^("+new Fraction(BigInteger.ONE, b.getDenominator())+")");
			return pow(new Fraction(b.getNumerator(), BigInteger.ONE)).pow(new Fraction(BigInteger.ONE, b.getDenominator()));
		}
	}
	
	public int compareTo(Fraction b){
		return evaluate().compareTo(b.evaluate());
	}
	
	public boolean greaterThan(Fraction b){
		return compareTo(b) == 1;
	}
	
	public boolean lessThan(Fraction b){
		return compareTo(b) == -1;
	}
	
	public boolean equals(Fraction b){
		return compareTo(b) == 0;
	}
	
	public BigInteger floor(){
		BigDecimal ev = evaluate();
		int signum = ev.signum();
		ev = ev.abs().subtract(ev.remainder(BigDecimal.ONE));
		if(signum == -1)
			ev = ev.negate();
		return ev.toBigInteger();
	}
	
	public int sign(){
		return numerator.signum() * denominator.signum();
	}
	
	public Fraction abs(){
		if(sign() == -1)
			return this.negate();
		return this;
	}
}
