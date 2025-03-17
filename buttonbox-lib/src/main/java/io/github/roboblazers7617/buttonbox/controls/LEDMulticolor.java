package io.github.roboblazers7617.buttonbox.controls;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import io.github.roboblazers7617.buttonbox.Color;

/**
 * A multicolor indicator LED.
 */
public class LEDMulticolor extends LED {
	/**
	 * Used to communicate the color of the LED.
	 */
	private StringTopic colorTopic;
	/**
	 * Publisher for {@link #colorTopic}.
	 */
	private StringPublisher colorPub;
	/**
	 * Subscriber for {@link #colorTopic}.
	 */
	private StringSubscriber colorSub;

	/**
	 * Stores the current color of the LED.
	 */
	private Color color;

	/**
	 * Creates a new LED.
	 *
	 * @param id
	 *            The ID string for the LED to use.
	 */
	public LEDMulticolor(String id) {
		super(id);
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		super.setupNetworkTables(table);

		colorTopic = table.getStringTopic("Color");
		colorPub = colorTopic.publish();
		colorSub = colorTopic.subscribe("#000000");
	}

	@Override
	public void updateClient() {
		super.updateClient();

		for (String colorString : colorSub.readQueueValues()) {
			color.setFromString(colorString);
		}
	}

	/**
	 * Gets the color of the LED.
	 *
	 * @return
	 *         The current color of the LED.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the LED. Published to NetworkTables immediately.
	 *
	 * @param color
	 *            The color to set.
	 */
	public void setColor(Color color) {
		colorPub.set(color.toHexString());
		this.color = color;
	}

	/**
	 * Sets the color of the LED from a WPILib {@link edu.wpi.first.wpilibj.util.Color Color}. Published
	 * to NetworkTables immediately.
	 *
	 * @param color
	 *            The color to set.
	 */
	public void setColor(edu.wpi.first.wpilibj.util.Color color) {
		setColor(new Color(color));
	}
}
