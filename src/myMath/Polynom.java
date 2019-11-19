package myMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import javax.swing.Spring;

import myMath.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	private ArrayList<Monom>  _Polynom;

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		;
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	/*
	 * convert 
	 */
	public Polynom(String s) {
		int begin=0,counter=0;
		Monom temp;
		for(int i=0; i<s.length(); i++)
		{

			if(s.charAt(i)=='+'||s.charAt(i)=='-')//searching for +/-
			{
				temp=new Monom(s.substring(begin, i++));
				_Polynom.add(temp);
				begin= i;
			}
		}
		Monom_Comperator cmpByPower=new Monom_Comperator();
		this._Polynom.sort(cmpByPower);

	}
	@Override
	public double f(double x) {
		double ans=0;
		Iterator<Monom> Iter=this.iteretor();
		while (Iter.hasNext())
		{
			ans+=Iter.next().f(x);
		}
		return ans;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> Iter=p1.iteretor();
		while (Iter.hasNext())
		{
			this.add(Iter.next());
		}
	}

	@Override
	public void add(Monom m1) 
	{
		boolean isAdd=false;
		Iterator<Monom> iter=this.iteretor();
		while(iter.hasNext())
		{
			Monom temp= iter.next();
			if(temp.get_power()==m1.get_power())
			{
				temp.add(m1);
				isAdd=true;
			}
		}
		if(isAdd==false)
			_Polynom.add(m1);
		Monom_Comperator cmpByPower=new Monom_Comperator();
		this._Polynom.sort(cmpByPower);
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> iter=p1.iteretor();
		while(iter.hasNext())
		{
			Monom a=iter.next();
			Monom temp = new Monom(a.get_coefficient()*-1,a.get_power());
			this.add(temp);
		}
		Monom_Comperator cmpByPower=new Monom_Comperator();
		this._Polynom.sort(cmpByPower);

	}

	@Override
	public void multiply(Polynom_able p1) { 

		Iterator<Monom> iter1=this.iteretor();
		Iterator<Monom> iter2=p1.iteretor();
		Polynom poly= new Polynom();
		Monom temp1, temp2;
		while(iter1.hasNext())
		{
			temp1= iter1.next();
			while(iter2.hasNext())
			{
				temp2 = iter2.next();
				temp1.multipy(temp2);
				poly.add(temp1);
			}
		}
		this._Polynom=((Polynom) poly.copy())._Polynom;
	}

	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> iter1=this.iteretor();
		Iterator<Monom> iter2=p1.iteretor();
		Monom temp1, temp2;
		while(iter1.hasNext()&&iter2.hasNext())
		{
			temp1=iter1.next();
			temp2=iter2.next();
			if(temp1.get_coefficient()!=temp2.get_coefficient()||temp1.get_power()!=temp2.get_power())
				return false;
		}
		return true;
	}

	@Override
	public boolean isZero() {
		Iterator<Monom> iter=this.iteretor();
		Monom temp;
		while(iter.hasNext())
		{
			temp=iter.next();
			if(temp.get_coefficient()!=0)
				return false;
		}

		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		double mid=(x0+x1)/2 , interval=x1-x0;
		if(x0>x1)
		{
			throw new RuntimeException("ERR: x0 need to be smaller then x1");
		}
		if ((f(x0)) * (f(x1)) >= 0) 
		{ 
			throw new RuntimeException("ERR: The function does not cut the x-axis");
		}
		else
			while(interval>=eps)
			{
				mid=(x0+x1)/2;
				if(f(mid)==0.0)
					return mid;
				else

					if(f(x0)*f(mid)>=0)
						x0=mid;
					else
						x1=mid;		
			}
		return mid;
	}

	@Override
	public Polynom_able copy() {
		Polynom_able temp = new Polynom();
		Monom mon;
		Iterator<Monom> iter=this.iteretor();
		while(iter.hasNext())
		{
			mon=iter.next();
			temp.add(mon);
		}
		return temp;
	}

	@Override
	public Polynom_able derivative() {
		Iterator<Monom> iter=this.iteretor();
		Polynom_able temp = new Polynom();
		while(iter.hasNext())
		{
			iter.next().derivative();
		}
		return temp;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double sum =0.0;
		if((f(x0)*f(x1))>=0)
		{
			throw new RuntimeException("error צריך להיות הפוך");
		}
		if(x1<=x0)
			return 0;
		for(double i=x0; i<x1; i+=eps)
		{
			sum+=(f(i)+f(i+eps))/2*eps;//squre area formula
		}
		return sum;
	}

	@Override
	public Iterator<Monom> iteretor() {
		return this._Polynom.iterator();
	}
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> iter=this.iteretor();
		Polynom poly=new Polynom();
		Monom temp;
		while(iter.hasNext())
		{
			temp=iter.next();
			temp.multipy(m1);
			poly.add(temp);
		}
		this._Polynom=((Polynom) poly.copy())._Polynom;
	}
	
	public String toString()
	{
		String str="";
		Iterator<Monom> It=this.iteretor();
		while (It.hasNext())
		{
			Monom temp=new Monom (It.next());
			if(It.hasNext()&&temp.get_coefficient()!=0)
			{
				str+=temp.toString()+" + " ;
			}
			else if(!It.hasNext()&&temp.get_coefficient()!=0)
			{
				str+=temp.toString();
			}
			else if(!It.hasNext()&&temp.get_coefficient()==0)
			{
				str=str.substring(0,str.length()-3);
			}
		}
		return str;
	}

}
