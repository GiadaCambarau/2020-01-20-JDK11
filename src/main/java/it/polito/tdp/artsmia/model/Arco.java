package it.polito.tdp.artsmia.model;

import java.util.Objects;

public class Arco  implements Comparable<Arco>{
	private Artists a1;
	private Artists a2;
	private int peso;
	
	public Arco(Artists a1, Artists a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	
	public Artists getA1() {
		return a1;
	}
	public void setA1(Artists a1) {
		this.a1 = a1;
	}
	public Artists getA2() {
		return a2;
	}
	public void setA2(Artists a2) {
		this.a2 = a2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(a1, a2, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(a1, other.a1) && Objects.equals(a2, other.a2) && peso == other.peso;
	}

	@Override
	public int compareTo(Arco o) {
		
		return o.peso-this.peso;
	}

	@Override
	public String toString() {
		return  a1 + " <----> " + a2 + "    " + peso ;
	}
	
	

}
