package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer extends ControlContainer {
	/**
	 * Creates a new ButtonBoxServer.
	 *
	 * @param inst
	 *            {@link NetworkTableInstance} to use.
	 */
	public ButtonBoxServer(NetworkTableInstance inst) {
		super(inst);
	}

	/**
	 * Creates a new ButtonBoxServer with the default {@link NetworkTableInstance}.
	 */
	public ButtonBoxServer() {
		super();
	}

	/**
	 * Updates the states of the controls. Should be called regularly by the server program.
	 */
	@Override
	public void periodic() {
		for (Control control : controls) {
			control.updateServer();
		}
	}

	/**
	 * Adds a control to the ButtonBoxServer.
	 *
	 * @param control
	 *            The {@link io.github.roboblazers7617.buttonbox.Control} to add to the server.
	 */
	@Override
	public void addControl(Control control) {
		if (RobotBase.isSimulation()) {
			control.setupSimulation();
		}

		super.addControl(control);
	}
}
