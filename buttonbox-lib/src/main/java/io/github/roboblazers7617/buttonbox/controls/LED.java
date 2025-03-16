package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;

/**
 * A single color indicator LED.
 */
public class LED extends Control {
	/**
	 * Used to communicate the brightness of the LED.
	 */
	private DoubleTopic brightnessTopic;
	/**
	 * Publisher for {@link #brightnessTopic}.
	 */
	private DoublePublisher brightnessPub;
	/**
	 * Subscriber for {@link #brightnessTopic}.
	 */
	private DoubleSubscriber brightnessSub;

	/**
	 * Stores the current brightness of the LED [0-1].
	 */
	private double brightness;

	/**
	 * Simulation device used to simulate the LED.
	 */
	private SimDevice simDevice;
	/**
	 * Simulation object for the brightness.
	 */
	private SimDouble brightnessSim;

	/**
	 * Creates a new LED.
	 *
	 * @param id
	 *            The ID string for the LED to use.
	 */
	public LED(String id) {
		super(id);
	}

	@Override
	public void setupSimulation() {
		simDevice = SimDevice.create(getId());
		brightnessSim = simDevice.createDouble("Brightness", SimDevice.Direction.kOutput, brightness);
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		brightnessTopic = table.getDoubleTopic("Brightness");
		brightnessPub = brightnessTopic.publish();
		brightnessSub = brightnessTopic.subscribe(0.0);
	}

	@Override
	public void updateServer() {
		brightnessSim.set(brightness);
	}

	@Override
	public void updateClient() {
		brightness = brightnessSub.get();
	}

	/**
	 * Gets the brightness of the LED.
	 *
	 * @return
	 *         The current brightness of the LED [0-1].
	 */
	public double getBrightness() {
		return brightness;
	}

	/**
	 * Sets the brightness of the LED. Published to NetworkTables immediately.
	 *
	 * @param brightness
	 *            The brightness to set [0-1].
	 */
	public void setBrightness(double brightness) {
		brightnessPub.set(brightness);
		this.brightness = brightness;
	}
}
