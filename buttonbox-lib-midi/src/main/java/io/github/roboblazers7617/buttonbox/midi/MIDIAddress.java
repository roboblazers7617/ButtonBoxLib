package io.github.roboblazers7617.buttonbox.midi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

import io.github.roboblazers7617.buttonbox.Address;

/**
 * An {@link Address} that sends and recieves data over MIDI.
 */
public class MIDIAddress implements Address {
	/**
	 * MIDIDevice to communicate with.
	 */
	public final MIDIDevice midiDevice;
	/**
	 * MIDI command to use.
	 *
	 * @see
	 *      ShortMessage
	 */
	public final int command;
	/**
	 * MIDI channel to communicate on.
	 */
	public final int channel;
	/**
	 * Data1 to use (note number, CC number, etc.);
	 */
	public final int data1;
	/**
	 * Stores the last value sent so duplicate values aren't sent to the address.
	 */
	private int lastData;
	/**
	 * Stores the current feedback value.
	 * Empty if no feedback has been recieved.
	 */
	private Optional<Integer> feedback;
	/**
	 * Stores the historical feedback values.
	 * Cleared when calling {@link #getFeedbackQueue()}.
	 */
	private ArrayList<Integer> feedbackQueue;

	/**
	 * Creates a new MIDIAddress with the specified command, channel, and data1.
	 *
	 * @param midiDevice
	 *            MIDIDevice to use.
	 * @param command
	 *            MIDI command to use.
	 * @param channel
	 *            MIDI channel to use.
	 * @param data1
	 *            Data1 to use (note number, CC number, etc.).
	 */
	public MIDIAddress(MIDIDevice midiDevice, int command, int channel, int data1) {
		this.midiDevice = midiDevice;
		this.command = command;
		this.channel = channel;
		this.data1 = data1;

		feedbackQueue = new ArrayList<>();
		feedbackQueue.ensureCapacity(100);

		midiDevice.getRouter().addAddress(this);
	}

	/**
	 * Gets the address's command.
	 *
	 * @return
	 *         MIDI command number.
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * Gets the address's channel.
	 *
	 * @return
	 *         MIDI channel number.
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * Gets the address's data1 value.
	 *
	 * @return
	 *         Data1 value.
	 */
	public int getData1() {
		return data1;
	}

	public void send(double data) {
		sendRaw((int) (data * 127));
	}

	/**
	 * Sends raw data to the address.
	 *
	 * @param data
	 *            Integer between 0 and 127 to send.
	 */
	public void sendRaw(int data) {
		if (data != lastData) {
			ShortMessage message = new ShortMessage();
			try {
				message.setMessage(command, channel, data1, data);
			} catch (InvalidMidiDataException ex) {
				System.err.println(ex);
			}
			midiDevice.send(message);
			lastData = data;
		}
	}

	public Optional<Double> getFeedback() {
		if (feedback.isPresent()) {
			return Optional.of(feedback.get() / 127.0);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Gets feedback from the address.
	 *
	 * @return
	 *         Integer between 0 and 127 containing control feedback.
	 *         Empty if no feedback has been recieved.
	 */
	public Optional<Integer> getFeedbackRaw() {
		return feedback;
	}

	/**
	 * Sets the feedback for the address. Not intended to be called outside of the {@link MIDIRouter} class.
	 *
	 * @param feedback
	 *            The feedback value to set [0-127].
	 */
	public void setFeedbackRaw(int feedback) {
		this.feedback = Optional.of(feedback);
		// TOOD: Make this size configurable
		if (feedbackQueue.size() >= 100) {
			feedbackQueue.remove(0);
		}
		feedbackQueue.add(feedback);
	}

	public List<Double> getFeedbackQueue() {
		return getFeedbackQueueRaw().stream()
				.flatMapToDouble((value) -> DoubleStream.of(value * 127.0))
				.boxed()
				.collect(Collectors.toList());
	}

	/**
	 * Gets changes to the feedback from the address, and clears the queue of changes.
	 *
	 * @return
	 *         Integers between 0 and 127 containing control feedback changes, ordered from least to most recent.
	 */
	public List<Integer> getFeedbackQueueRaw() {
		feedbackQueue.clear();
		return feedbackQueue;
	}
}
