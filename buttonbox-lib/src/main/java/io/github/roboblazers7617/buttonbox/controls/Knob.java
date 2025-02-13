package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;

/**
 * A knob, fader, encoder, or any other sort of analog control.
 */
public class Knob extends Control {
	/**
	 * Used to communicate the position of the knob.
	 */
	private DoubleTopic positionTopic;
	/**
	 * Publisher for {@link positionTopic}.
	 */
	private DoublePublisher positionPub;
	/**
	 * Subscriber for {@link positionTopic}.
	 */
	private DoubleSubscriber positionSub;

	/**
	 * Stores the current position of the Knob.
	 */
	private double position;
	/**
	 * Stores where the physical Knob was in the last update. Used to determine whether simulation values should be used or not.
	 */
	private double lastPosition;

	/**
	 * Simulation device used to simulate the knob.
	 */
	private SimDevice simDevice;
	/**
	 * Simulation object for the knob's position.
	 */
	private SimDouble positionSim;

	/**
	 * Creates a new Knob.
	 *
	 * @param id
	 *            The ID string for the Knob to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public Knob(String id) {
		super(id);
	}

	@Override
	public void setupSimulation() {
		simDevice = SimDevice.create(getId());
		positionSim = simDevice.createDouble("Position", SimDevice.Direction.kBidir, position);
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		positionTopic = table.getDoubleTopic("Position");
		positionPub = positionTopic.publish();
		positionSub = positionTopic.subscribe(0.0);
	}

	@Override
	public void updateServer() {
		position = positionSub.get();

		if (position == lastPosition) {
			setPosition(positionSim.get());
		} else {
			positionSim.set(position);
			lastPosition = position;
		}
	}

	@Override
	public void updateClient() {
		position = positionSub.get();
	}

	/**
	 * Gets the position of the Knob.
	 *
	 * @return
	 *         The current position of the Knob [0-1].
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * Sets the position of the Knob. Published to NetworkTables immediately.
	 *
	 * @param position
	 *            The position to set.
	 */
	public void setPosition(double position) {
		positionPub.set(position);
		this.position = position;
	}
}
