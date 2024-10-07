package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer extends SubsystemBase {
	private ArrayList<Control> controls = new ArrayList<Control>();
	private final NetworkTable table;
	private final NetworkTable controlTable;

	/** Creates a new ButtonBoxServer. */
	public ButtonBoxServer() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		table = inst.getTable("ButtonBox");
		controlTable = table.getSubTable("Controls");
	}

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
	 *                The {@link io.github.roboblazers7617.buttonbox.Control} to add to the server.
	 */
	public void addControl(Control control) {
		control.setTable(controlTable);
		controls.add(control);
	}
}
