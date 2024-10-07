package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTable;

/**
 * Class for buttonbox controls that handles feedback and NetworkTables.
 */
public class Control {
	private NetworkTable table;

	/** Creates a new Control. */
	public Control() {}

	/** Updates the control's state. This doesn't do anything by default, and should be overridden by the class inheritting this. */
	public void update() {}

	/**
	 * Called by the Control class when the NetworkTable is set. Not intended to be called outside of the class.
	 *
	 * This should create all of the NetworkTables publishers and subscribers for the inheritting class.
	 *
	 * @param table
	 * 	{@link edu.wpi.first.networktables.NetworkTable}
	 */
	public void setupNetworkTables(NetworkTable table) {}

	/** Gets the NetworkTable used by this Control. */
	public NetworkTable getTable() {
		return table;
	}

	/** Sets the NetworkTable to be used by this Control. */
	public void setTable(NetworkTable table) {
		this.table = table;
		setupNetworkTables(table);
	}
}
