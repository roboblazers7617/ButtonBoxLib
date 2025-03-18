package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.math.MathUtil;

/**
 * Class that represents an RGB color. This is designed to be higher-performance than Java and
 * WPILib's Color classes, since the same object can be reused multiple times.
 */
public class Color {
	/**
	 * The red element [0-1].
	 */
	private double red;
	/**
	 * The green element [0-1].
	 */
	private double green;
	/**
	 * The blue element [0-1].
	 */
	private double blue;

	/**
	 * Creates a new Color with provided values.
	 *
	 * @param red
	 *            The red element [0-1].
	 * @param green
	 *            The green element [0-1].
	 * @param blue
	 *            The blue element [0-1].
	 */
	public Color(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Creates a new Color from a WPILib {@link edu.wpi.first.wpilibj.util.Color Color}.
	 *
	 * @param color
	 *            The color to pull from.
	 */
	public Color(edu.wpi.first.wpilibj.util.Color color) {
		this(color.red, color.green, color.blue);
	}

	/**
	 * Creates a new Color with default values.
	 */
	public Color() {
		this(0.0, 0.0, 0.0);
	}

	/**
	 * Gets the red element of this color [0-1].
	 *
	 * @return
	 *         The red element of this color [0-1].
	 */
	public double getRed() {
		return red;
	}

	/**
	 * Gets the green element of this color [0-1].
	 *
	 * @return
	 *         The green element of this color [0-1].
	 */
	public double getGreen() {
		return green;
	}

	/**
	 * Gets the blue element of this color [0-1].
	 *
	 * @return
	 *         The blue element of this color [0-1].
	 */
	public double getBlue() {
		return blue;
	}

	/**
	 * Copies this Color.
	 *
	 * @return
	 *         A copy of this Color.
	 */
	public Color copy() {
		return new Color(red, green, blue);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}

		return other instanceof Color color && Double.compare(color.red, red) == 0 && Double.compare(color.green, green) == 0 && Double.compare(color.blue, blue) == 0;
	}

	/**
	 * Sets the color from a hex string.
	 *
	 * @param hexString
	 *            Hex string in format #RRGGBB.
	 * @return
	 *         The modified object.
	 */
	public Color setFromString(String hexString) {
		this.red = Integer.valueOf(hexString.substring(1, 3), 16) / 255.0;
		this.green = Integer.valueOf(hexString.substring(3, 5), 16) / 255.0;
		this.blue = Integer.valueOf(hexString.substring(5, 7), 16) / 255.0;

		return this;
	}

	/**
	 * Scales the brightness of the color. Just multiplies the color elements by the scalar instead of
	 * doing fancy HSV stuff, so the colors might not look perfect.
	 *
	 * @apiNote
	 *          Color elements are clamped to [0-1].
	 * @param brightness
	 *            The scalar to set. Usually [0-1], but setting it larger will make the color brighter.
	 * @return
	 *         The modified object.
	 */
	public Color scaleBrightness(double brightness) {
		red = MathUtil.clamp(red * brightness, 0, 1);
		green = MathUtil.clamp(green * brightness, 0, 1);
		blue = MathUtil.clamp(blue * brightness, 0, 1);

		return this;
	}

	/**
	 * Gets this color as a hex string.
	 *
	 * @return
	 *         Hex string in format #RRGGBB.
	 */
	public String toHexString() {
		return String.format("#%02X%02X%02X", (int) (red * 255), (int) (green * 255), (int) (blue * 255));
	}
}
