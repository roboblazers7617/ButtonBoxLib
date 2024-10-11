package io.github.roboblazers7617.buttonbox.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

public class MIDIAddress {
	public final MIDIDevice midiDevice;
	public final int command;
	public final int channel;
	public final int data1;
	/** Stores the last value sent so duplicate values aren't sent to the address. */
	private int lastData;

	/**
	 * Creates a new MIDIAddress with the specified command, channel, and data1.
	 *
	 * @param midiDevice
	 *                MIDIDevice to use.
	 * @param command
	 *                MIDI command to use.
	 * @param channel
	 *                MIDI channel to use.
	 * @param data1
	 *                Data1 to use (note number, CC number, etc.).
	 */
	public MIDIAddress(MIDIDevice midiDevice, int command, int channel, int data1) {
		this.midiDevice = midiDevice;
		this.command = command;
		this.channel = channel;
		this.data1 = data1;
	}

	/**
	 * Sends data to the address.
	 *
	 * @param data
	 *                Integer between 0 and 127 to send.
	 * @throws InvalidMidiDataException
	 */
	public void send(int data) throws InvalidMidiDataException {
		if (data != lastData) {
			ShortMessage message = new ShortMessage();
			message.setMessage(command, channel, data1, data);
			midiDevice.send(message);
			lastData = data;
		}
	}

	/**
	 * Scales a double and sends it.
	 *
	 * @param data
	 *                Double between 0 and 1 to send.
	 * @throws InvalidMidiDataException
	 */
	public void sendDouble(MIDIDevice device, int data) throws InvalidMidiDataException {
		send((int) (data * 127));
	}
}
