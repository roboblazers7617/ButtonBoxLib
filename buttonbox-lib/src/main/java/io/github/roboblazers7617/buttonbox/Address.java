package io.github.roboblazers7617.buttonbox;

import java.util.List;
import java.util.Optional;

/**
 * A channel through which data can be sent and recieved to and from ButtonBox hardware.
 */
public interface Address<T> {
	/**
	 * Sends data to the address.
	 *
	 * @param data
	 *            Object to send.
	 */
	public void send(T data);

	/**
	 * Gets feedback from the address.
	 *
	 * @return
	 *         Objects containing control feedback.
	 *         Empty if no feedback has been recieved.
	 */
	public Optional<T> getFeedback();

	/**
	 * Gets the changes to the feedback from the address since the last call to this method.
	 *
	 * @return
	 *         Objects containing control feedback changes, ordered from least to most recent.
	 *         Empty if no feedback has been recieved.
	 */
	public List<T> getFeedbackQueue();
}
