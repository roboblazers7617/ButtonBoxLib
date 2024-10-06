package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer extends SubsystemBase {
	private final DoublePublisher valuePub;
	private double value = 0;

	/** Creates a new ButtonBoxServer. */
	public ButtonBoxServer() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("ButtonBox");
		valuePub = table.getDoubleTopic("value").publish();
	}

	@Override
	public void periodic() {
		value += 0.01;
		valuePub.set(value);
	}
}
