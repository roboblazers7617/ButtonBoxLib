package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.addresses.DoubleAddress;

/**
 * A Knob controlled through Addresses by a Potentiometer.
 */
public class PhysicalPotentiometer extends Knob {
	/**
	 * Address used for position data.
	 */
	private final DoubleAddress positionAddress;

	/**
	 * Creates a new PhysicalPotentiometer.
	 *
	 * @param id
	 *            The ID string for the PhysicalPotentiometer to use.
	 * @param positionAddress
	 *            The Address used for the position.
	 */
	public PhysicalPotentiometer(String id, DoubleAddress positionAddress) {
		super(id);
		this.positionAddress = positionAddress;
	}

	@Override
	public void updateClient() {
		super.updateClient();
		positionAddress.getFeedback().ifPresent((feedback) -> setPosition(feedback));
	}
}
