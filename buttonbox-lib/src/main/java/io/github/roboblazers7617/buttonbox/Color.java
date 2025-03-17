package io.github.roboblazers7617.buttonbox;

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
	 * Gets this color as a hex string.
	 *
	 * @return
	 *         Hex string in format #RRGGBB.
	 */
	public String toHexString() {
		return String.format("#%02X%02X%02X", (int) (red * 255), (int) (green * 255), (int) (blue * 255));
	}
}
