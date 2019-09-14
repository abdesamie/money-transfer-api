package com.revolut.transfer;

public class ShutDownThread extends Thread {

	@Override
	public void run() {
		System.out.println("Progrm exit ! release ressources");

	}
}
