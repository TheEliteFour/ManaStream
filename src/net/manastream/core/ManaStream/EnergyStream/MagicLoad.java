/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manastream.core.ManaStream.EnergyStream;

/**
 *
 * @author Xeology
 */
public class MagicLoad {

    @Override
    public MagicLoad clone() {
	MagicLoad clone = new MagicLoad(magic);
	clone.setPotency(potency);
	return clone;

    }
    private Potency potency = null;
    private double magic = 0;

    public MagicLoad(double magic) {
	this.magic = magic;
	potency = new Potency(magic);
    }

    public MagicLoad(double magic, int pot) {
	this.magic = magic;
	potency = new Potency(pot);
    }

    public MagicLoad() {
	magic = 0;
	potency = new Potency(0);
    }

    public void setPotency(Potency potency) {
	this.potency = potency;
    }

    public void setPotency(int potency) {
	this.potency = new Potency(potency);
    }

    public Potency getPotencyObject() {
	return potency;
    }

    public double getMagic() {
	return magic;
    }

    public void setMagic(double magic) {
	magic = magic;
    }
}
