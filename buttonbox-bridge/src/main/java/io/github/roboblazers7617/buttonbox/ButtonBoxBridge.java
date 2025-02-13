package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;

import java.io.IOException;
import java.util.Optional;

import edu.wpi.first.math.jni.WPIMathJNI;
import edu.wpi.first.util.WPIUtilJNI;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import io.github.roboblazers7617.buttonbox.controls.PhysicalTestControl;
import io.github.roboblazers7617.buttonbox.controls.PhysicalEncoder.LoopMode;
import io.github.roboblazers7617.buttonbox.controls.PhysicalButton;
import io.github.roboblazers7617.buttonbox.controls.PhysicalEncoder;
import io.github.roboblazers7617.buttonbox.controls.PhysicalJoystick;
import io.github.roboblazers7617.buttonbox.controls.PhysicalPotentiometer;
import io.github.roboblazers7617.buttonbox.midi.MIDIUtil;
import io.github.roboblazers7617.buttonbox.midi.MIDIDevice;
import io.github.roboblazers7617.buttonbox.midi.MIDIAddress;

/**
 * Example bridge program for connecting ButtonBox hardware to NetworkTables.
 */
public class ButtonBoxBridge {
	public static void main(String[] args) throws IOException, MidiUnavailableException {
		NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
		WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
		WPIMathJNI.Helper.setExtractOnStaticLoad(false);

		CombinedRuntimeLoader.loadLibraries(ButtonBoxBridge.class, "wpiutiljni", "wpimathjni", "ntcorejni");

		new ButtonBoxBridge().run();
	}

	public void run() throws MidiUnavailableException {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		inst.startClient4("ButtonBox Bridge");
		inst.setServer("localhost"); // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
		inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS

		Optional<MIDIDevice> midiDevice = MIDIUtil.getDeviceByName("IAC Bus 1", "IAC Bus 2");

		if (midiDevice.isEmpty()) {
			throw new MidiUnavailableException("No MIDI device found.");
		}

		ButtonBoxClient client = new ButtonBoxClient(inst);

		configureControls(client, midiDevice.get());

		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				System.out.println("interrupted");
				return;
			}
			client.periodic();
		}
	}

	/**
	 * Configures the ButtonBox controls.
	 */
	private void configureControls(ButtonBoxClient client, MIDIDevice midiDevice) {
		// Test controls
		client.addControl(new PhysicalTestControl("Test Control", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 0)));
		client.addControl(new PhysicalButton("Test Button", new MIDIAddress(midiDevice, ShortMessage.NOTE_ON, 0, 0)));
		client.addControl(new PhysicalJoystick("Test Joystick", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 1), new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 2), new MIDIAddress(midiDevice, ShortMessage.NOTE_ON, 0, 1)));
		client.addControl(new PhysicalPotentiometer("Test Potentiometer", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 2)));
		client.addControl(new PhysicalEncoder("Test Encoder", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 3))
				.setLoopMode(LoopMode.WRAP)
				.setScale(0.1));
	}
}
