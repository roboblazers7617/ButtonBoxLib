package io.github.roboblazers7617.buttonbox.controls;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that reads the data published by a {@link io.github.roboblazers7617.buttonbox.controls.TestControl} and outputs it as MIDI messages.
 */
public class TestControlMIDI extends TestControl {
	/**
	 * Creates a new TestControlMIDI.
	 *
	 * @param id
	 *                The ID string for the TestControlMIDI to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public TestControlMIDI(String id) {
		super(id);
	}

	@Override
	public void updateHardware() {
		System.out.println(getValue());
	}
}
