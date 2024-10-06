package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for buttonbox controls. Handles NetworkTables logic and feedback.
 */
public class Control extends SubsystemBase {
	private final DoublePublisher valuePub;
	private double value = 0;

	/** Creates a new Control. */
	public Control(NetworkTable table) {
		valuePub = table.getDoubleTopic("value").publish();
	}

	@Override
	public void periodic() {
		value += 0.01;
		valuePub.set(value);
	}
}
