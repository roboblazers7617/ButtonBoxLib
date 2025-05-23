package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Control;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanSubscriber;

import edu.wpi.first.wpilibj2.command.button.Trigger;

import edu.wpi.first.hal.SimDevice;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.hal.SimBoolean;

/**
 * A push button with configurable feedback. Intended for use with a momentary switch that is lit by
 * a controllable LED.
 */
public class Button extends Control {
	/**
	 * Used to communicate whether the physical button is pressed or not.
	 */
	private BooleanTopic pressedTopic;
	/**
	 * Publisher for {@link #pressedTopic}.
	 */
	private BooleanPublisher pressedPub;
	/**
	 * Subscriber for {@link #pressedTopic}.
	 */
	private BooleanSubscriber pressedSub;

	/**
	 * Used to communicate whether the button is active or not.
	 */
	private BooleanTopic stateTopic;
	/**
	 * Publisher for {@link #stateTopic}.
	 */
	private BooleanPublisher statePub;
	/**
	 * Subscriber for {@link #stateTopic}.
	 */
	private BooleanSubscriber stateSub;

	/**
	 * Stores whether the physical button is pressed or not.
	 */
	private boolean pressed;
	/**
	 * Stores whether the physical button was pressed or not on the last update. Used to determine
	 * rising/falling edges.
	 */
	private boolean lastPressed;
	/**
	 * Stores the state of the button on the server, which is updated according to the button's mode.
	 */
	private boolean state;

	/**
	 * Stores the selected {@link ButtonMode}.
	 */
	private ButtonMode buttonMode = ButtonMode.PUSH;

	/**
	 * Simulation device used to simulate the button.
	 */
	private SimDevice simDevice;
	/**
	 * Simulation object for the physical button's pressed state.
	 */
	private SimBoolean pressedSim;
	/**
	 * Simulation object for the button's feedback state.
	 */
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
	 *            The ID string for the Button to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public Button(String id) {
		super(id);

		lastPressed = false;
		pressed = false;
		state = false;
	}

	@Override
	public void setupSimulation() {
		simDevice = SimDevice.create(getId());
		pressedSim = simDevice.createBoolean("Pressed", SimDevice.Direction.kBidir, pressed);
		stateSim = simDevice.createBoolean("State", SimDevice.Direction.kOutput, state);
	}

	@Override
	public void updateServer() {
		List<Boolean> valueQueue = new ArrayList<Boolean>();
		for (boolean value : pressedSub.readQueueValues()) {
			valueQueue.add(value);
		}

		if (valueQueue.size() == 0) {
			valueQueue.add(pressedSim.get());
			pressedPub.set(pressedSim.get());
		}

		for (Boolean pressedValue : valueQueue) {
			pressed = pressedValue;

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

			statePub.set(state);
		}

		pressedSim.set(pressed);
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
	 * Gets the button's state.
	 *
	 * @return
	 *         Button state (true = pushed, false = released).
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * Sets whether the button is pressed or not. Published to NetworkTables immediately.
	 *
	 * @param pressed
	 *            Is the button pressed?
	 */
	public void setPressed(boolean pressed) {
		pressedPub.set(pressed);
		this.pressed = pressed;
	}

	/**
	 * Get a trigger for the button.
	 *
	 * @return
	 *         Trigger.
	 */
	public Trigger getTrigger() {
		return new Trigger(() -> state);
	}

	/**
	 * Sets the button's mode.
	 *
	 * @param buttonMode
	 *            Mode to set the button to.
	 * @return
	 *         The modified object for method chaining.
	 */
	public Button setMode(ButtonMode buttonMode) {
		this.buttonMode = buttonMode;
		return this;
	}
}
