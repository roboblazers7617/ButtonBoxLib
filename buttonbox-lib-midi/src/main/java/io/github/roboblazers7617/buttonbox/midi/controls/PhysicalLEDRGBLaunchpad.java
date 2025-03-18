package io.github.roboblazers7617.buttonbox.midi.controls;

import java.nio.ByteBuffer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import io.github.roboblazers7617.buttonbox.Color;
import io.github.roboblazers7617.buttonbox.controls.LEDMulticolor;
import io.github.roboblazers7617.buttonbox.midi.MIDIDevice;

/**
 * An RGB LED that sends its RGB components to an address, encoded into the Novation Launchpad's
 * SYSEX data format.
 */
public class PhysicalLEDRGBLaunchpad extends LEDMulticolor {
	/**
	 * Device to output data to.
	 */
	private final MIDIDevice device;
	/**
	 * The index of the LED to control.
	 */
	private final byte index;
	/**
	 * The sysex message to use.
	 */
	private SysexMessage sysexMessage = new SysexMessage();
	/**
	 * The last color sent to the Launchpad. Used to prevent sending duplicate data.
	 */
	private Color lastColor;

	/**
	 * Header prepended to LED data.
	 */
	private static final byte[] sysexHeader = { (byte) 0xF0, 0x00, 0x20, 0x29, 0x02, 0x0C, 0x03 };

	/**
	 * Creates a new PhysicalLEDRGBLaunchpad.
	 *
	 * @param id
	 *            The ID string for the LED to use.
	 * @param index
	 *            The index of the LED to control.
	 * @param device
	 *            The device to send SYSEX data to.
	 */
	public PhysicalLEDRGBLaunchpad(String id, byte index, MIDIDevice device) {
		super(id);
		this.index = index;
		this.device = device;
	}

	@Override
	public void updateHardware() {
		Color color = getColor().scaleBrightness(getBrightness());

		// Create a buffer for the message data
		byte[] messageData = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		ByteBuffer messageBuffer = ByteBuffer.wrap(messageData);
		messageBuffer.put(sysexHeader);
		messageBuffer.put((byte) 0x03); // Set data type to RGB
		messageBuffer.put(index);
		messageBuffer.put((byte) (color.getRed() * 127));
		messageBuffer.put((byte) (color.getGreen() * 127));
		messageBuffer.put((byte) (color.getBlue() * 127));
		messageBuffer.put((byte) 0xF7); // Status byte

		// Get data as an array
		byte[] data = messageBuffer.array();

		try {
			// If the color has changed, send the new value over MIDI
			if (!color.equals(lastColor)) {
				sysexMessage.setMessage(data, data.length);
				device.send(sysexMessage);
				lastColor = color.copy();
			}
		} catch (InvalidMidiDataException ex) {
			System.err.println(ex);
		}
	}
}
