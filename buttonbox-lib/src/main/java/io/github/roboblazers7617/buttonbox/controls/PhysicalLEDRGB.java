package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Color;
import io.github.roboblazers7617.buttonbox.addresses.DoubleAddress;

/**
 * An RGB LED that sends its RGB components to Addresses.
 */
public class PhysicalLEDRGB extends LEDMulticolor {
	/**
	 * Address used to output the red value.
	 */
	private final DoubleAddress redAddress;
	/**
	 * Address used to output the green value.
	 */
	private final DoubleAddress greenAddress;
	/**
	 * Address used to output the blue value.
	 */
	private final DoubleAddress blueAddress;

	/**
	 * Creates a new PhysicalLEDRGB.
	 *
	 * @param id
	 *            The ID string for the LED to use.
	 * @param redAddress
	 *            The address to send the red component to.
	 * @param greenAddress
	 *            The address to send the green component to.
	 * @param blueAddress
	 *            The address to send the blue component to.
	 */
	public PhysicalLEDRGB(String id, DoubleAddress redAddress, DoubleAddress greenAddress, DoubleAddress blueAddress) {
		super(id);
		this.redAddress = redAddress;
		this.greenAddress = greenAddress;
		this.blueAddress = blueAddress;
	}

	@Override
	public void updateHardware() {
		Color color = getColor();

		redAddress.send(color.getRed());
		greenAddress.send(color.getGreen());
		blueAddress.send(color.getBlue());
	}
}
