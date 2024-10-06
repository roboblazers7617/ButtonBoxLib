package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer extends SubsystemBase {
	/** Creates a new ButtonBoxServer. */
	public ButtonBoxServer() {
	}

	@Override
	public void periodic() {
		System.out.println("Test!");
	}
}