package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.addresses.DoubleAddress;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that reads the data published by a {@link io.github.roboblazers7617.buttonbox.controls.TestControl} and outputs it to an {@link io.github.roboblazers7617.buttonbox.Address}.
 */
public class PhysicalTestControl extends TestControl {
	/**
	 * Address used to output the current value.
	 */
	private final DoubleAddress address;

	/**
	 * Creates a new PhysicalTestControl.
	 *
	 * @param id
	 *            The ID string for the PhysicalTestControl to use.
	 * @param address
	 *            The address to send messages to.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public PhysicalTestControl(String id, DoubleAddress address) {
		super(id);
		this.address = address;
	}

	@Override
	public void updateHardware() {
		address.send(getValue());
	}
}
