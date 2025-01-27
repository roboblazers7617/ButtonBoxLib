package io.github.roboblazers7617.buttonbox.midi;

import java.util.Optional;
import java.util.Vector;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 * Some static utility functions for working with MIDI devices.
 */
public class MIDIUtil {
	/**
	 * Finds a MIDI device by name.
	 *
	 * @param rxName
	 *                The name of the reciever device.
	 * @param txName
	 *                The name of the transmitter device.
	 * @return
	 *         The found device, or empty if no device was found.
	 * @implNote
	 *           If multiple devices with the same name exist, the one with the highest index will be returned.
	 */
	public static Optional<MIDIDevice> getDeviceByName(String rxName, String txName) {
		MidiDevice device;
		Vector<MidiDevice> recieverDevices = new Vector<MidiDevice>();
		Vector<MidiDevice> transmitterDevices = new Vector<MidiDevice>();
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

		// Find all devices that match the given conditions.
		for (int i = 0; i < infos.length; i++) {
			try {
				device = MidiSystem.getMidiDevice(infos[i]);

				if (device.getMaxReceivers() != 0 && infos[i].getName().equals(rxName)) {
					recieverDevices.add(device);
				}

				if (device.getMaxTransmitters() != 0 && infos[i].getName().equals(txName)) {
					transmitterDevices.add(device);
				}
			} catch (MidiUnavailableException ex) {
				// Handle or throw exception...
				ex.printStackTrace();
			}
		}

		// Make sure the devices exist, and, if they do, create a MIDIDevice with them and return that.
		if (!recieverDevices.isEmpty() && !transmitterDevices.isEmpty()) {
			try {
				MidiDevice recieverDevice = recieverDevices.firstElement();
				MidiDevice transmitterDevice = transmitterDevices.firstElement();

				recieverDevice.open();
				transmitterDevice.open();

				return Optional.of(new MIDIDevice(recieverDevice, transmitterDevice));
			} catch (MidiUnavailableException ex) {
				// Handle or throw exception...
				ex.printStackTrace();
			}
		}

		// Return an empty value if failed.
		return Optional.empty();
	}

	/**
	 * Finds a MIDI device by name.
	 *
	 * @param name
	 *                The name of the MIDI device.
	 * @return
	 *         The found device, or empty if no device was found.
	 * @see
	 *      #getDeviceByName(String, String)
	 */
	public static Optional<MIDIDevice> getDeviceByName(String name) {
		return getDeviceByName(name, name);
	}
}
