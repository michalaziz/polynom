
package myMath;

import java.util.Comparator;

import javax.management.RuntimeErrorException;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/*
	 * this method represent the monom class
	 * convert string to monom devided to coeffiecient and power    
	 */
	public Monom(String s)
	{
		String str1="", str2;
		double coef;
		int  pow;

		if(s==null)
			throw new RuntimeException("empty");
		if(!s.contains("x"))
		{
			this.set_power(0);
			coef=Double.parseDouble(s);
			this.set_coefficient(coef);
		}
		else {
			if(s.charAt(0)=='-'&&s.charAt(1)=='x')
				this.set_coefficient(-1);
			else if(s.charAt(0)=='x')
				this.set_coefficient(1);
			else {
			str1=s.substring(0,s.indexOf('x'));
			coef=Double.parseDouble(str1);
			this.set_coefficient(coef);
			if(!s.contains("^"))
				this.set_power(1);
			else
			{
				str2=s.substring(s.indexOf('^')+1);
				pow=Integer.parseInt(str2);	
			}
		}}
	}

	/*
	 * add monom m to this monom only if the powers are equals
	 */
	public void add(Monom m) {
		double coef;
		if(this._power!=m._power)
			throw new ArithmeticException("differrent powers");
		else
		{
			if(this._coefficient+ m._coefficient!=0)
			 m.set_coefficient(this._coefficient+ m._coefficient);
		}
	}

	/*
	 * multiply monom d with this monom
	 */
	public void multipy(Monom d) {
		d._coefficient=this._coefficient*d._coefficient;
		d._power=this._power+d._power;
	}

	/*
	 * print the monom
	 */
	public String toString() {
		String ans = "";
		if(this._coefficient==0)
			return "0";
		if(this._power==0)
			ans=Double.toString(this._coefficient);
		else
			ans=Double.toString(this._coefficient) + "x^"+Integer.toString(this._power);
		return ans;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;


}
