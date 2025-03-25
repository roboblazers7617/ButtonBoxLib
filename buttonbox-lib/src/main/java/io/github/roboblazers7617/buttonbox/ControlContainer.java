package io.github.roboblazers7617.buttonbox;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * A class that contains a set of Controls and handles updating them.
 */
abstract class ControlContainer {
	/**
	 * The registered Controls. These are iterated over and updated every call to {@link #periodic()}.
	 */
	protected ArrayList<Control> controls = new ArrayList<Control>();
	/**
	 * The root NetworkTable.
	 */
	protected final NetworkTable table;
	/**
	 * The NetworkTable used for the Controls.
	 */
	protected final NetworkTable controlTable;

	/**
	 * Creates a new ControlContainer.
	 *
	 * @param inst
	 *            {@link NetworkTableInstance} to use.
	 */
	public ControlContainer(NetworkTableInstance inst) {
		table = inst.getTable("ButtonBox");
		controlTable = table.getSubTable("Controls");
	}

	/**
	 * Creates a new ControlContainer with the default {@link NetworkTableInstance}.
	 */
	public ControlContainer() {
		this(NetworkTableInstance.getDefault());
	}

	/**
	 * Updates the states of the controls. Should be called regularly by the wrapper program.
	 */
	abstract void periodic();

	/**
	 * Adds a control to the ControlContainer.
	 *
	 * @param control
	 *            The {@link io.github.roboblazers7617.buttonbox.Control} to add to the client.
	 */
	public void addControl(Control control) {
		control.setTable(controlTable);
		controls.add(control);
	}
}
