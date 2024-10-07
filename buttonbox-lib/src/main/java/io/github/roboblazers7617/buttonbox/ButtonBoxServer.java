package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import io.github.roboblazers7617.buttonbox.controls.TestControl;

/**
 * Class for robot-side ButtonBox logic.
 */
public class ButtonBoxServer extends SubsystemBase {
	private final Control testControl;
	private ArrayList<Control> controls = new ArrayList<Control>();

	/** Creates a new ButtonBoxServer. */
	public ButtonBoxServer() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		NetworkTable table = inst.getTable("ButtonBox");
		testControl = new TestControl(table);
		controls.add(testControl);
	}

	@Override
	public void periodic() {
		for (Control control : controls) {
			control.update();
		}
	}
}
