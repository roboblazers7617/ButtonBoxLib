package frc.robot;

import io.github.roboblazers7617.buttonbox.ButtonBoxServerSubsystem;
import io.github.roboblazers7617.buttonbox.controls.Button;
import io.github.roboblazers7617.buttonbox.controls.Joystick;
import io.github.roboblazers7617.buttonbox.controls.Knob;
import io.github.roboblazers7617.buttonbox.controls.TestControl;

/**
 * The ButtonBox used to control the robot.
 */
public class ButtonBox {
	/**
	 * The ButtonBoxServer used to interface with the ButtonBox.
	 */
	private final ButtonBoxServerSubsystem buttonBoxServer = new ButtonBoxServerSubsystem();

	/**
	 * A test Control.
	 */
	private final TestControl testControl;
	/**
	 * A test Button.
	 */
	private final Button testButton;
	/**
	 * A test Joystick.
	 */
	private final Joystick testJoystick;
	/**
	 * A test Knob. Bound to a PhysicalPotentiometer on the client.
	 */
	private final Knob testPotentiometer;
	/**
	 * A test Knob. Bound to a PhysicalEncoder on the client.
	 */
	private final Knob testEncoder;

	/**
	 * Creates a new ButtonBox.
	 */
	public ButtonBox() {
		// Test controls
		testControl = new TestControl("Test Control");
		buttonBoxServer.addControl(testControl);

		testButton = new Button("Test Button");
		buttonBoxServer.addControl(testButton);

		testJoystick = new Joystick("Test Joystick");
		buttonBoxServer.addControl(testJoystick);

		testPotentiometer = new Knob("Test Potentiometer");
		buttonBoxServer.addControl(testPotentiometer);

		testEncoder = new Knob("Test Encoder");
		buttonBoxServer.addControl(testEncoder);
	}

	/**
	 * Gets the {@link #testControl} for this ButtonBox.
	 *
	 * @return
	 *         The {@link #testControl} for this ButtonBox.
	 */
	public TestControl getTestControl() {
		return testControl;
	}

	/**
	 * Gets the {@link #testButton} for this ButtonBox.
	 *
	 * @return
	 *         The {@link #testButton} for this ButtonBox.
	 */
	public Button getTestButton() {
		return testButton;
	}

	/**
	 * Gets the {@link #testJoystick} for this ButtonBox.
	 *
	 * @return
	 *         The {@link #testJoystick} for this ButtonBox.
	 */
	public Joystick getTestJoystick() {
		return testJoystick;
	}

	/**
	 * Gets the {@link #testPotentiometer} for this ButtonBox.
	 *
	 * @return
	 *         The {@link #testPotentiometer} for this ButtonBox.
	 */
	public Knob getTestPotentiometer() {
		return testPotentiometer;
	}

	/**
	 * Gets the {@link #testEncoder} for this ButtonBox.
	 *
	 * @return
	 *         The {@link #testEncoder} for this ButtonBox.
	 */
	public Knob getTestEncoder() {
		return testEncoder;
	}
}
