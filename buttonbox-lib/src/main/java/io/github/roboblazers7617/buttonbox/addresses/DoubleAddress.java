package io.github.roboblazers7617.buttonbox.addresses;

import java.util.List;
import java.util.Optional;

import io.github.roboblazers7617.buttonbox.Address;

/**
 * A channel through which doubles can be sent and recieved to and from ButtonBox hardware.
 */
public interface DoubleAddress extends Address<Double> {
	/**
	 * Sends data to the address.
	 *
	 * @param data
	 *            Double between 0 and 1 to send.
	 */
	public void send(Double data);

	/**
	 * Gets feedback from the address.
	 *
	 * @return
	 *         Double between 0 and 1 containing control feedback.
	 *         Empty if no feedback has been recieved.
	 */
	public Optional<Double> getFeedback();

	/**
	 * Gets the changes to the feedback from the address since the last call to this method.
	 *
	 * @return
	 *         Doubles between 0 and 1 containing control feedback changes, ordered from least to most recent.
	 *         Empty if no feedback has been recieved.
	 */
	public List<Double> getFeedbackQueue();
}
