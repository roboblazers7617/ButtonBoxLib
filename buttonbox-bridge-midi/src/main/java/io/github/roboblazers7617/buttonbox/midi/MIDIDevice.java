package io.github.roboblazers7617.buttonbox.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

public class MIDIDevice {
	private final MidiDevice rxDevice;
	private final MidiDevice txDevice;
	private final Receiver receiver;
	private final Transmitter transmitter;

	/**
	 * Wrapper for {@link MidiDevice}
	 *
	 * @param rxDevice
	 *                {@link MidiDevice} to use for receiving messages
	 * @param txDevice
	 *                {@link MidiDevice} to use for transmitting messages
	 * @implNote
	 *           Provided devices must already be opened.
	 */
	public MIDIDevice(MidiDevice rxDevice, MidiDevice txDevice) throws MidiUnavailableException {
		this.rxDevice = rxDevice;
		this.txDevice = txDevice;
		this.receiver = rxDevice.getReceiver();
		this.transmitter = txDevice.getTransmitter();
	}

	public void send(ShortMessage message) {
		receiver.send(message, -1);
	}
}
