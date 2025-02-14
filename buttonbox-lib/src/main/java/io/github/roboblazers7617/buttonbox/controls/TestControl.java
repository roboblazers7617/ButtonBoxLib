package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that sends a float that is increased every time server feedback is updated and outputs it to the client TTY.
 */
public class TestControl extends Control {
	/**
	 * Used to communicate the current value.
	 */
	private DoubleTopic valueTopic;
	/**
	 * Publisher for {@link #valueTopic}.
	 */
	private DoublePublisher valuePub;
	/**
	 * Subscriber for {@link #valueTopic}.
	 */
	private DoubleSubscriber valueSub;

	/**
	 * The current value of the TestControl.
	 */
	private double value = 0;

	/**
	 * Simulation device used to simulate the TestControl.
	 */
	private SimDevice simDevice;
	/**
	 * Simulation object for the value.
	 */
	private SimDouble valueSim;

	/**
	 * Creates a new TestControl.
	 *
	 * @param id
	 *            The ID string for the TestControl to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public TestControl(String id) {
		super(id);
	}

	@Override
	public void setupSimulation() {
		simDevice = SimDevice.create(getId());
		valueSim = simDevice.createDouble("Value", SimDevice.Direction.kOutput, value);
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		valueTopic = table.getDoubleTopic("Value");
		valuePub = valueTopic.publish();
		valueSub = valueTopic.subscribe(0.0);
	}

	@Override
	public void updateServer() {
		value += 0.001;

		// Wrap values
		if (value > 1.0) {
			value = 0;
		}

		valuePub.set(value);
		valueSim.set(value);
	}

	@Override
	public void updateClient() {
		value = valueSub.get();
	}

	@Override
	public void updateHardware() {
		System.out.println(value);
	}

	/**
	 * Gets the current value of the TestControl.
	 *
	 * @return
	 *         The current value of the TestControl.
	 */
	public double getValue() {
		return value;
	}
}
