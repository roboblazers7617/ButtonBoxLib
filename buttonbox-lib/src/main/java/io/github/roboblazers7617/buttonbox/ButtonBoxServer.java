package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj.RobotBase;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer {
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
	 * Creates a new ButtonBoxServer.
	 */
	public ButtonBoxServer() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		table = inst.getTable("ButtonBox");
		controlTable = table.getSubTable("Controls");
	}

	/**
	 * Updates the states of the controls. Should be called regularly by the server program.
	 */
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
	public void addControl(Control control) {
		if (RobotBase.isSimulation()) {
			control.setupSimulation();
		}

		control.setTable(controlTable);
		controls.add(control);
	}
}
