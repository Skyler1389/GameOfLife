package main;

public class UpdateThread extends Thread {

	UpdateHandler handler;
	int startIndex;
	int endIndex;
	
	UpdateThread(UpdateHandler handler, int startIndex, int endIndex) {
		
		this.handler = handler;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		
	}
	
	public void run() {
		handler.update(startIndex, endIndex);
	}
}