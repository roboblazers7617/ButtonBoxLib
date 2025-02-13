package io.github.roboblazers7617.buttonbox.controls;

import edu.wpi.first.math.MathUtil;
import io.github.roboblazers7617.buttonbox.Address;

/**
 * A Knob controlled through Addresses by an Encoder that outputs relative
 * values.
 */
public class PhysicalEncoder extends Knob {
	/**
	 * Address used for position data.
	 */
	private final Address positionAddress;
	/**
	 * Loop mode of the encoder.
	 */
	private LoopMode loopMode = LoopMode.CLAMP;
	/**
	 * Scaling of the encoder.
	 */
	private double scale = 1.0;

	/**
	 * Determines if and how out-of-range encoder values should be looped.
	 */
	public enum LoopMode {
		/** Clamp out of range values to the valid range. */
		CLAMP,
		/** Loop values. */
		WRAP,
	}

	/**
	 * Creates a new PhysicalPotentiometer.
	 *
	 * @param id
	 *            The ID string for the PhysicalPotentiometer to use.
	 * @param positionAddress
	 *            The Address used for the position.
	 *            A value of 0.5 is the center, values above that are movement in the positive
	 *            direction, and values below that are movement in the negative direction.
	 */
	public PhysicalEncoder(String id, Address positionAddress) {
		super(id);
		this.positionAddress = positionAddress;
	}

	@Override
	public void updateClient() {
		// Get movement since the last update and add it all to the position
		positionAddress.getFeedbackQueue()
				.forEach((feedback) -> {
					double newPosition = getPosition() + ((feedback - 0.5) * 2.0 * scale);
					switch (loopMode) {
						case CLAMP:
							setPosition(MathUtil.clamp(newPosition, 0.0, 1.0));
							break;

						case WRAP:
							setPosition(MathUtil.inputModulus(newPosition, 0.0, 1.0));
							break;
					}
				});
		super.updateClient();
	}

	/**
	 * Sets the loop mode of the encoder.
	 *
	 * @param mode
	 *            Mode to set.
	 * @return
	 *         The modified object for method chaining.
	 */
	public PhysicalEncoder setLoopMode(LoopMode mode) {
		loopMode = mode;
		return this;
	}

	/**
	 * Sets the scaling of the encoder values.
	 *
	 * @param scale
	 *            Scale to set.
	 * @return
	 *         The modified object for method chaining.
	 */
	public PhysicalEncoder setScale(double scale) {
		this.scale = scale;
		return this;
	}
}
