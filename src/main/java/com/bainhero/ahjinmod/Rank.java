package com.bainhero.ahjinmod;

import java.util.Random;

public class Rank 
{
	private int mana;
	
	public Rank(int mana) {
		this.mana = mana;
	}
	
	public Rank() {
		Random rand = new Random();
		this.mana = rand.nextInt(1000) + 1;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public String getRank() {
		String rank = "";
		if(this.mana > 900) {
			rank = "S";
		} else if(this.mana > 800) {
			rank = "A";
		} else if(this.mana > 700) {
			rank = "B";
		} else if(this.mana > 600) {
			rank = "C";
		} else if(this.mana > 500) {
			rank = "D";
		} else {
			rank = "E";
		}
		return rank;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
}
