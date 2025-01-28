package io.github.roboblazers7617.buttonbox;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for client-side ButtonBox logic.
 */
public class ButtonBoxClient {
	private ArrayList<Control> controls = new ArrayList<Control>();
	private final NetworkTable table;
	private final NetworkTable controlTable;

	/**
	 * Creates a new ButtonBoxClient.
	 *
	 * @param inst
	 *                {@link NetworkTableInstance} to use.
	 */
	public ButtonBoxClient(NetworkTableInstance inst) {
		table = inst.getTable("ButtonBox");
		controlTable = table.getSubTable("Controls");
	}

	/** Should be called regularly to update the state of the Controls. */
	public void periodic() {
		for (Control control : controls) {
			control.updateOnClient();
		}
	}

	/**
	 * Adds a control to the ButtonBoxClient.
	 *
	 * @param control
	 *                The {@link io.github.roboblazers7617.buttonbox.Control} to add to the client.
	 */
	public void addControl(Control control) {
		control.setTable(controlTable);
		controls.add(control);
	}
}
