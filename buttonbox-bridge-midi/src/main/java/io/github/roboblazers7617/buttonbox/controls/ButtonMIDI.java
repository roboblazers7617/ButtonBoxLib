package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.midi.MIDIAddress;

public class ButtonMIDI extends Button {
	private final MIDIAddress midiAddress;

	/**
	 * Creates a new ButtonMIDI.
	 *
	 * @param id
	 *                The ID string for the ButtonMIDI to use.
	 * @param buttonAddress
	 *                The MIDIAddress for the ButtonMIDI to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public ButtonMIDI(String id, MIDIAddress buttonAddress) {
		super(id);
		this.midiAddress = buttonAddress;
	}

	@Override
	public void updateClient() {
		super.updateClient();
		if (getState()) {
			midiAddress.send(127);
		} else {
			midiAddress.send(0);
		}
		setPressed(midiAddress.getFeedback() != 0);
	}
}
