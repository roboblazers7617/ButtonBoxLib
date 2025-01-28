package io.github.roboblazers7617.buttonbox.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

/**
 * A wrapper for {@link MidiDevice MidiDevices} that combines recieving and transmitting objects into one object.
 */
public class MIDIDevice {
	private final MidiDevice rxDevice;
	private final MidiDevice txDevice;
	private final Receiver receiver;
	private final Transmitter transmitter;
	private final MIDIRouter router;

	/**
	 * Creates a MIDIDevice.
	 *
	 * @param rxDevice
	 *                {@link MidiDevice} to use for receiving messages
	 * @param txDevice
	 *                {@link MidiDevice} to use for transmitting messages
	 * @throws MidiUnavailableException
	 *                 Thrown when the rxDevice or txDevice is unavailable.
	 * @implNote
	 *           Provided devices must already be opened.
	 */
	public MIDIDevice(MidiDevice rxDevice, MidiDevice txDevice) throws MidiUnavailableException {
		this.rxDevice = rxDevice;
		this.txDevice = txDevice;
		this.receiver = rxDevice.getReceiver();
		this.transmitter = txDevice.getTransmitter();

		router = new MIDIRouter();
		this.transmitter.setReceiver(router);
	}

	/**
	 * Sends a message to this device.
	 *
	 * @param message
	 *                The message to send.
	 */
	public void send(ShortMessage message) {
		receiver.send(message, -1);
	}

	/**
	 * Gets the MIDIRouter assigned to this device.
	 *
	 * @return
	 *         The MIDIRouter assigned to this device.
	 */
	public MIDIRouter getRouter() {
		return router;
	}
}
