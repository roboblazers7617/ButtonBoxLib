package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.addresses.DoubleAddress;

/**
 * A single color LED that sends its brightness to an Address.
 */
public class PhysicalLED extends LED {
	/**
	 * Address used to output the current value.
	 */
	private final DoubleAddress brightnessAddress;

	/**
	 * Creates a new PhysicalLED.
	 *
	 * @param id
	 *            The ID string for the LED to use.
	 * @param brightnessAddress
	 *            The address to send the brightness to.
	 */
	public PhysicalLED(String id, DoubleAddress brightnessAddress) {
		super(id);
		this.brightnessAddress = brightnessAddress;
	}

	@Override
	public void updateHardware() {
		brightnessAddress.send(getBrightness());
	}
}
