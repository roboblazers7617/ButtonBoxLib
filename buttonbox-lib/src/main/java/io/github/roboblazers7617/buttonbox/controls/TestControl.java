package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that outputs a float that is increased every time feedback is updated.
 */
public class TestControl extends Control {
	private DoubleTopic valueTopic;
	private DoublePublisher valuePub;
	private DoubleSubscriber valueSub;
	private double value = 0;

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
	public void setupNetworkTables(NetworkTable table) {
		valueTopic = table.getDoubleTopic("value");
		valuePub = valueTopic.publish();
		valueSub = valueTopic.subscribe(0.0);
	}

	@Override
	public void updateServer() {
		value += 0.01;
		valuePub.set(value);
	}

	@Override
	public void updateClient() {
		value = valueSub.get();
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
