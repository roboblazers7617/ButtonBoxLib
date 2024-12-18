package io.github.roboblazers7617.buttonbox;

public interface Address {
	/**
	 * Sends data to the address.
	 *
	 * @param data
	 *                Double between 0 and 1 to send.
	 */
	public void send(double data);

	/**
	 * Gets feedback from the address.
	 *
	 * @return
	 *         Double between 0 and 1 containing control feedback.
	 */
	public double getFeedback();
}
