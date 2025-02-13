package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Address;

/**
 * A Joystick controlled through Addresses.
 */
public class PhysicalJoystick extends Joystick {
	/**
	 * Address used for X axis data.
	 */
	private final Address xAddress;
	/**
	 * Address used for Y axis data.
	 */
	private final Address yAddress;
	/**
	 * Address used for button data.
	 */
	private final Address buttonAddress;

	/**
	 * Creates a new PhysicalJoystick.
	 *
	 * @param id
	 *            The ID string for the PhysicalJoystick to use.
	 * @param xAddress
	 *            The Address used for the X axis.
	 * @param yAddress
	 *            The Address used for the Y axis.
	 * @param buttonAddress
	 *            The Address used for the Button.
	 */
	public PhysicalJoystick(String id, Address xAddress, Address yAddress, Address buttonAddress) {
		super(id);
		this.xAddress = xAddress;
		this.yAddress = yAddress;
		this.buttonAddress = buttonAddress;
	}

	@Override
	public void updateClient() {
		xAddress.getFeedback().ifPresent((feedback) -> setX(feedback));
		yAddress.getFeedback().ifPresent((feedback) -> setY(feedback));
		buttonAddress.getFeedback().ifPresent((feedback) -> setButton(feedback != 0));
		super.updateClient();
	}
}
