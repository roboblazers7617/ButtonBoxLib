package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;

import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;

public class Joystick extends Control {
	/**
	 * Stores the current X axis value.
	 */
	private double xAxis;
	/**
	 * Stores the current Y axis value.
	 */
	private double yAxis;
	/**
	 * Stores whether the button is pressed or not.
	 */
	private boolean buttonState;

	/**
	 * Used to communicate the physical X position.
	 */
	private DoubleTopic xTopic;
	/**
	 * Publisher for {@link xTopic}.
	 */
	private DoublePublisher xPub;
	/**
	 * Subscriber for {@link xTopic}.
	 */
	private DoubleSubscriber xSub;

	/**
	 * Used to communicate the physical Y position.
	 */
	private DoubleTopic yTopic;
	/**
	 * Publisher for {@link yTopic}.
	 */
	private DoublePublisher yPub;
	/**
	 * Subscriber for {@link yTopic}.
	 */
	private DoubleSubscriber ySub;

	/**
	 * Used to communicate the physical X position.
	 */
	private BooleanTopic buttonTopic;
	/**
	 * Publisher for {@link buttonTopic}.
	 */
	private BooleanPublisher buttonPub;
	/**
	 * Subscriber for {@link buttonTopic}.
	 */
	private BooleanSubscriber buttonSub;

	/**
	 * Simulation device to use for simulation.
	 */
	private SimDevice simDevice;
	/**
	 * Simulation object for the X axis.
	 */
	private SimDouble xSim;
	/**
	 * Simulation object for the Y axis.
	 */
	private SimDouble ySim;
	/**
	 * Simulation object for the button.
	 */
	private SimBoolean buttonSim;

	/**
	 * Create a new Joystick.
	 *
	 * @param id
	 *                The ID string for the Joystick to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public Joystick(String id) {
		super(id);

		xAxis = 0.5;
		yAxis = 0.5;
		buttonState = false;
	}

	@Override
	public void setupSimulation() {
		simDevice = SimDevice.create(getId());
		xSim = simDevice.createDouble("Joystick X", SimDevice.Direction.kOutput, xAxis);
		ySim = simDevice.createDouble("Joystick Y", SimDevice.Direction.kOutput, yAxis);
		buttonSim = simDevice.createBoolean("Button", SimDevice.Direction.kOutput, buttonState);
	}

	@Override
	public void updateServer() {
		// Get data from NetworkTables
		xAxis = xSub.get();
		yAxis = ySub.get();
		buttonState = buttonSub.get();

		// Update simulation output.
		xSim.set(xAxis);
		ySim.set(yAxis);
		buttonSim.set(buttonState);
	}

	@Override
	public void updateClient() {
		xPub.set(xAxis);
		yPub.set(yAxis);
		buttonPub.set(buttonState);
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		xTopic = table.getDoubleTopic("X");
		xPub = xTopic.publish();
		xSub = xTopic.subscribe(xAxis);

		yTopic = table.getDoubleTopic("Y");
		yPub = yTopic.publish();
		ySub = yTopic.subscribe(yAxis);

		buttonTopic = table.getBooleanTopic("Button");
		buttonPub = buttonTopic.publish();
		buttonSub = buttonTopic.subscribe(buttonState);
	}

	/**
	 * Gets the X axis value.
	 *
	 * @return
	 *         The current X axis value [0-1].
	 */
	public double getX() {
		return xAxis;
	}

	/**
	 * Gets the Y axis value.
	 *
	 * @return
	 *         The current Y axis value [0-1].
	 */
	public double getY() {
		return yAxis;
	}

	/**
	 * Gets whether the joystick button is pressed or not.
	 *
	 * @return
	 *         Is the button pressed?
	 */
	public boolean getButton() {
		return buttonState;
	}

	/**
	 * Gets a trigger for the button.
	 *
	 * @return
	 *         Trigger.
	 */
	public Trigger getButtonTrigger() {
		return new Trigger(() -> buttonState);
	}

	/**
	 * Sets the X axis value.
	 */
	public void setX(double value) {
		xAxis = value;
	}

	/**
	 * Sets the Y axis value.
	 */
	public void setY(double value) {
		yAxis = value;
	}

	/**
	 * Sets whether the joystick button is pressed or not.
	 */
	public void setButton(boolean pressed) {
		buttonState = pressed;
	}
}
