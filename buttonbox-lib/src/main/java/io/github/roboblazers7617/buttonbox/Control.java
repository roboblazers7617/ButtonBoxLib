package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTable;

/**
 * Class for buttonbox controls that handles feedback.
 */
public class Control {
	private NetworkTable table;

	public Control(NetworkTable table) {
		this.table = table;
		setupNetworkTables(table);
	}

	/** Updates the control's state. This doesn't do anything by default, and should be overridden by the class inheritting this. */
	public void update() {}

	/** Called when the NetworkTable is set. Should create all of the NetworkTables publishers and subscribers for the inheritting class. */
	public void setupNetworkTables(NetworkTable table) {}

	/** Gets the NetworkTable used by this Control. */
	public NetworkTable getTable() {
		return table;
	}
}
