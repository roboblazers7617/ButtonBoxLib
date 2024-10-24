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
	private DoubleTopic valueTopic;
	private DoublePublisher valuePub;
	private DoubleSubscriber valueSub;
	private double value = 0;

	private SimDevice simDevice;
	private SimDouble valueSim;

	/**
	 * Creates a new TestControl.
	 *
	 * @param id
	 *                The ID string for the TestControl to use.
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
		valueTopic = table.getDoubleTopic("value");
		valuePub = valueTopic.publish();
		valueSub = valueTopic.subscribe(0.0);
	}

	@Override
	public void updateServer() {
		value += 0.01;
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
	 * @return
	 *         The current value of the TestControl
	 */
	public double getValue() {
		return value;
	}
}
