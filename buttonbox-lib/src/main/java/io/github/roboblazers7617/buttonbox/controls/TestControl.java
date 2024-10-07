package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;

public class TestControl extends Control {
	private DoublePublisher valuePub;
	private double value = 0;

	/** Creates a new Control. */
	public TestControl() {}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		valuePub = getTable().getDoubleTopic("value").publish();
	}

	@Override
	public void update() {
		value += 0.01;
		valuePub.set(value);
	}
}
