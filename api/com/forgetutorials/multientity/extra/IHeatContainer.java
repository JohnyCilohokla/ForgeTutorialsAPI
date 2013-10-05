package com.forgetutorials.multientity.extra;

public interface IHeatContainer {
	/**
	 * 
	 * @return heat level
	 */
	public double getHeat();

	/**
	 * @param heat
	 * @return amount added
	 */
	public double addHeat(double heat);

	/**
	 * @param heat
	 * @return amount taken
	 */
	public double takeHeat(double heat);
}
