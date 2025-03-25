package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;

/**
 * A subsystem for robot-side ButtonBox logic. Steals most of its Subsystem-related logic from
 * {@link edu.wpi.first.wpilibj2.command.SubsystemBase}.
 */
public class ButtonBoxServerSubsystem extends ButtonBoxServer implements Subsystem, Sendable {
	/**
	 * Creates a new ButtonBoxServerSubsystem.
	 */
	public ButtonBoxServerSubsystem() {
		super();

		String name = this.getClass().getSimpleName();
		name = name.substring(name.lastIndexOf('.') + 1);
		SendableRegistry.addLW(this, name, name);
		CommandScheduler.getInstance().registerSubsystem(this);
	}

	/**
	 * Gets the name of this Subsystem.
	 *
	 * @return
	 *         The name of this Subsystem.
	 */
	@Override
	public String getName() {
		return SendableRegistry.getName(this);
	}

	/**
	 * Sets the name of this Subsystem.
	 *
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		SendableRegistry.setName(this, name);
	}

	/**
	 * Gets the subsystem name of this Subsystem.
	 *
	 * @return
	 *         The subsystem name of this Subsystem.
	 */
	public String getSubsystem() {
		return SendableRegistry.getSubsystem(this);
	}

	/**
	 * Sets the subsystem name of this Subsystem.
	 *
	 * @param subsystem
	 *            The subsystem name to set.
	 */
	public void setSubsystem(String subsystem) {
		SendableRegistry.setSubsystem(this, subsystem);
	}

	/**
	 * Associates a {@link Sendable} with this Subsystem. Also update the child's name.
	 *
	 * @param name
	 *            The name to give the child.
	 * @param child
	 *            The sendable child to add.
	 */
	public void addChild(String name, Sendable child) {
		SendableRegistry.addLW(child, getSubsystem(), name);
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Subsystem");

		builder.addBooleanProperty(".hasDefault", () -> getDefaultCommand() != null, null);
		builder.addStringProperty(".default", () -> getDefaultCommand() != null ? getDefaultCommand().getName() : "none", null);
		builder.addBooleanProperty(".hasCommand", () -> getCurrentCommand() != null, null);
		builder.addStringProperty(".command", () -> getCurrentCommand() != null ? getCurrentCommand().getName() : "none", null);
	}
}
