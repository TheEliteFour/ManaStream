/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.EnergyStream;

/**
 *
 * @author Xeology
 */
public class Potency {

    private int potency;

    public Potency(double magic) {
	if (magic <= 32 && magic >= 0) {
	    potency = 1;
	}
	if (magic <= 128 && magic >= 33) {
	    potency = 2;
	}
	if (magic <= 512 && magic >= 129) {
	    potency = 3;
	}
	if (magic >= 513) {
	    potency = 4;
	}
    }

    public Potency(int potency) {
	if (potency > 4) {
	    potency = 4;
	}
	this.potency = potency;
    }

    public int getPotency() {
	return potency;
    }

    public String getPotencyName() {
	switch (potency) {
	    case 1:
		return "Primal Stream";
	    case 2:
		return "Potent Stream";
	    case 3:
		return "Pure Stream";
	    case 4:
		return "Chaotic Stream";
	}
	return "Stream";
    }
}
