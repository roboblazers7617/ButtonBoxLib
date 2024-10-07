package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTable;

/**
 * Class for buttonbox controls that handles feedback and NetworkTables.
 */
public class Control {
	private NetworkTable table;
	private String id = "Control";

	/** Creates a new Control. */
	public Control() {}

	/**
	 * Updates the control's state on the server.
	 * <p>
	 * This doesn't do anything by default, and should be overridden by the class inheritting this.
	 */
	public void updateServer() {}

	/**
	 * Updates the control's state on the client. This should have logic in it that polls the hardware's state and sends it to the server.
	 * <p>
	 * This doesn't do anything by default, and should be overridden by the class inheritting this.
	 */
	public void updateClient() {}

	/**
	 * Called by the Control class when the NetworkTable is set. Not intended to be called outside of the class.
	 * <p>
	 * This should create all of the NetworkTables publishers and subscribers for the inheritting class.
	 *
	 * @param table
	 *                {@link edu.wpi.first.networktables.NetworkTable}
	 */
	public void setupNetworkTables(NetworkTable table) {}

	/** Gets the NetworkTable used by this Control. */
	public NetworkTable getTable() {
		return table;
	}

	/** Sets the NetworkTable to be used by this Control. A subtable will be created with the ID string as its key. */
	public void setTable(NetworkTable table) {
		this.table = table.getSubTable(id);
		setupNetworkTables(this.table);
	}

	/** Sets the ID string. This must be called before setTable, otherwise the subtable name will not match the ID. */
	public void setID(String id) {
		this.id = id;
	}
}
