package io.github.roboblazers7617.buttonbox.controls;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.InvalidMidiDataException;

import io.github.roboblazers7617.buttonbox.midi.MIDIDevice;

/**
 * A test {@link io.github.roboblazers7617.buttonbox.Control} that reads the data published by a {@link io.github.roboblazers7617.buttonbox.controls.TestControl} and outputs it as MIDI messages.
 */
public class TestControlMIDI extends TestControl {
	private final MIDIDevice midiDevice;

	/**
	 * Creates a new TestControlMIDI.
	 *
	 * @param id
	 *                The ID string for the TestControlMIDI to use.
	 * @param midiDevice
	 *                The MIDI device to send messages to.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public TestControlMIDI(String id, MIDIDevice midiDevice) {
		super(id);
		this.midiDevice = midiDevice;
	}

	@Override
	public void updateHardware() {
		System.out.println(getValue());
		try {
			ShortMessage message = new ShortMessage();
			message.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, (int) (getValue() % 127));
			midiDevice.send(message);
		} catch (InvalidMidiDataException ex) {
			System.err.println("Invalid MIDI data!");
		}
	}
}
