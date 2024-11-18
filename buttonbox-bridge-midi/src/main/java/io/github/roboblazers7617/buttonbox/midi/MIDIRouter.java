package io.github.roboblazers7617.buttonbox.midi;

import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MIDIRouter implements Receiver {
	private ArrayList<MIDIAddress> addresses = new ArrayList<MIDIAddress>();

	public void send(MidiMessage msg, long timeStamp) {
		if (msg instanceof ShortMessage) {
			ShortMessage shortMessage = (ShortMessage) msg;

			int channel = shortMessage.getChannel();
			int command = shortMessage.getCommand();
			int data1 = shortMessage.getData1();
			int value = shortMessage.getData2();
			System.out.println("Command: " + command);
			System.out.println("Channel: " + channel);
			System.out.println("Pitch: " + data1);
			System.out.println("Value: " + value);

			for (MIDIAddress address : addresses) {
				if (address.getChannel() == channel && address.getData1() == data1) {
					if (command == ShortMessage.NOTE_OFF && address.getCommand() == ShortMessage.NOTE_ON) {
						address.setFeedbackRaw(0);
					} else if (address.getCommand() == command) {
						address.setFeedbackRaw(value);
					}
				}
			}
		}
	}

	public void close() {}

	/**
	 * Adds an address to which MIDI messages will be routed to.
	 *
	 * @param address
	 *                The address to add to the MIDIRouter.
	 */
	public void addAddress(MIDIAddress address) {
		addresses.add(address);
	}
}
