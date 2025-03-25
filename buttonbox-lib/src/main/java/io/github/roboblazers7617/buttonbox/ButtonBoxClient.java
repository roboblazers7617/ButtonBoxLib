package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for client-side ButtonBox logic.
 */
public class ButtonBoxClient extends ControlContainer {
	/**
	 * Creates a new ButtonBoxClient.
	 *
	 * @param inst
	 *            {@link NetworkTableInstance} to use.
	 */
	public ButtonBoxClient(NetworkTableInstance inst) {
		super(inst);
	}

	/**
	 * Creates a new ButtonBoxClient with the default {@link NetworkTableInstance}.
	 */
	public ButtonBoxClient() {
		super();
	}

	/**
	 * Updates the states of the controls. Should be called regularly by the client program.
	 */
	@Override
	public void periodic() {
		for (Control control : controls) {
			control.updateOnClient();
		}
	}
}
