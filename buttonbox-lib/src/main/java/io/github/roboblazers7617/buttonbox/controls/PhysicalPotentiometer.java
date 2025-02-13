package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Address;

/**
 * A Knob controlled through Addresses by a Potentiometer.
 */
public class PhysicalPotentiometer extends Knob {
	/**
	 * Address used for position data.
	 */
	private final Address positionAddress;

	/**
	 * Creates a new PhysicalPotentiometer.
	 *
	 * @param id
	 *            The ID string for the PhysicalPotentiometer to use.
	 * @param positionAddress
	 *            The Address used for the position.
	 */
	public PhysicalPotentiometer(String id, Address positionAddress) {
		super(id);
		this.positionAddress = positionAddress;
	}

	@Override
	public void updateClient() {
		positionAddress.getFeedback().ifPresent((feedback) -> setPosition(feedback));
		super.updateClient();
	}
}
