package io.github.roboblazers7617.buttonbox;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for client-side ButtonBox logic.
 */
public class ButtonBoxClient {
	/**
	 * The registered Controls. These are iterated over and updated every call to {@link #periodic()}.
	 */
	private ArrayList<Control> controls = new ArrayList<Control>();
	/**
	 * The root NetworkTable.
	 */
	private final NetworkTable table;
	/**
	 * The NetworkTable used for the Controls.
	 */
	private final NetworkTable controlTable;

	/**
	 * Creates a new ButtonBoxClient.
	 *
	 * @param inst
	 *            {@link NetworkTableInstance} to use.
	 */
	public ButtonBoxClient(NetworkTableInstance inst) {
		table = inst.getTable("ButtonBox");
		controlTable = table.getSubTable("Controls");
	}

	/**
	 * Updates the states of the controls. Should be called regularly by the client program.
	 */
	public void periodic() {
		for (Control control : controls) {
			control.updateOnClient();
		}
	}

	/**
	 * Adds a control to the ButtonBoxClient.
	 *
	 * @param control
	 *            The {@link io.github.roboblazers7617.buttonbox.Control} to add to the client.
	 */
	public void addControl(Control control) {
		control.setTable(controlTable);
		controls.add(control);
	}
}
