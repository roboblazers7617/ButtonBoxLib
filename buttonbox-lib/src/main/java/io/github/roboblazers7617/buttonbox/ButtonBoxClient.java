package io.github.roboblazers7617.buttonbox;

/**
 * Class for client-side ButtonBox logic.
 */
public class ButtonBoxClient extends ControlContainer {
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
