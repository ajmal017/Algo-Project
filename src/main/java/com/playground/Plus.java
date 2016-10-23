package com.playground;

public class Plus {

	private double left;
	private double right;

	public Plus(double left, double right) {
		this.left = left;
		this.right = right;
	}

	public double calculate() {
		return left + right;
	}

}
