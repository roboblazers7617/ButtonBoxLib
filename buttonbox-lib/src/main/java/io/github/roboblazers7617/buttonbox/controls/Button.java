package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;

import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimBoolean;

public class Button extends Control {
	/** Used to communicate whether the physical button is pressed or not. */
	private BooleanTopic pressedTopic;
	private BooleanPublisher pressedPub;
	private BooleanSubscriber pressedSub;

	/** Used to communicate whether the button is active or not. */
	private BooleanTopic stateTopic;
	private BooleanPublisher statePub;
	private BooleanSubscriber stateSub;

	/** Stores whether the physical button is pressed or not. */
	private boolean pressed;
	/** Stores whether the physical button was pressed or not on the last update. Used to determine rising/falling edges. */
	private boolean lastPressed;
	/** Stores the state of the button on the server, which is updated according to the button's mode. */
	private boolean state;

	/** Stores the selected {@link ButtonMode}. */
	private ButtonMode buttonMode = ButtonMode.PUSH;

	private SimDevice simDevice;
	private SimBoolean pressedSim;
	private SimBoolean stateSim;

	/**
	 * Determines how the state of the button should be updated when the button is pressed or released.
	 */
	public enum ButtonMode {
		/** Enabled when pressed, disabled when released. */
		PUSH,
		/** Toggle button, triggers on the rising edge. */
		TOGGLE_RISING,
		/** Toggle button, triggers on the falling edge. */
		TOGGLE_FALLING,
	}

	/**
	 * Creates a new Button.
	 *
	 * @param id
	 *                The ID string for the Button to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public Button(String id) {
		super(id);

		lastPressed = false;
		pressed = false;
		state = false;

		simDevice = SimDevice.create("Button");
		pressedSim = simDevice.createBoolean("Pressed", SimDevice.Direction.kBidir, pressed);
		stateSim = simDevice.createBoolean("State", SimDevice.Direction.kOutput, state);
	}

	@Override
	public void updateServer() {
		pressed = pressedSub.get();

		if (pressed == lastPressed) {
			pressed = pressedSim.get();
		} else {
			pressedSim.set(pressed);
		}

		if (pressed != lastPressed) {
			if (buttonMode == ButtonMode.TOGGLE_RISING || buttonMode == ButtonMode.TOGGLE_FALLING) {
				if (lastPressed) {
					if (buttonMode == ButtonMode.TOGGLE_FALLING) {
						state = !state;
					}
				} else {
					if (buttonMode == ButtonMode.TOGGLE_RISING) {
						state = !state;
					}
				}
			} else if (buttonMode == ButtonMode.PUSH) {
				state = pressed;
			}

			lastPressed = pressed;
		}

		pressedPub.set(pressed);
		statePub.set(state);

		stateSim.set(state);
	}

	@Override
	public void updateClient() {
		state = stateSub.get();
	}

	@Override
	public void setupNetworkTables(NetworkTable table) {
		pressedTopic = table.getBooleanTopic("Pressed");
		pressedPub = pressedTopic.publish();
		pressedSub = pressedTopic.subscribe(false);
		pressedPub.set(false);

		stateTopic = table.getBooleanTopic("State");
		statePub = stateTopic.publish();
		stateSub = stateTopic.subscribe(false);
	}

	/**
	 * Gets button state
	 *
	 * @return
	 *         button state (true = pushed, false = released)
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * Get a trigger for the button
	 *
	 * @return
	 *         trigger
	 */
	public Trigger getTrigger() {
		return new Trigger(() -> state);
	}

	/**
	 * Sets the button's mode
	 *
	 * @param buttonMode
	 *                mode to set the button to
	 */
	public void setMode(ButtonMode buttonMode) {
		this.buttonMode = buttonMode;
	}
}
