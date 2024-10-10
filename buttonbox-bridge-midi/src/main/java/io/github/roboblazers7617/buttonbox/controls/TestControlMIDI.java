package io.github.roboblazers7617.buttonbox.controls;

import javax.sound.midi.InvalidMidiDataException;

import io.github.roboblazers7617.buttonbox.midi.MIDIAddress;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that reads the data published by a {@link io.github.roboblazers7617.buttonbox.controls.TestControl} and outputs it as MIDI messages.
 */
public class TestControlMIDI extends TestControl {
	private final MIDIAddress midiAddress;

	/**
	 * Creates a new TestControlMIDI.
	 *
	 * @param id
	 *                The ID string for the TestControlMIDI to use.
	 * @param midiAddress
	 *                The MIDI address to send messages to.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public TestControlMIDI(String id, MIDIAddress midiAddress) {
		super(id);
		this.midiAddress = midiAddress;
	}

	@Override
	public void updateHardware() {
		System.out.println(getValue());
		try {
			midiAddress.send((int) (getValue() % 127));
		} catch (InvalidMidiDataException ex) {
			System.err.println("Invalid MIDI data!");
		}
	}
}
