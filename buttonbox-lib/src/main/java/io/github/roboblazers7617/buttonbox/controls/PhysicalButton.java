package io.github.roboblazers7617.buttonbox.controls;

import io.github.roboblazers7617.buttonbox.Address;

/**
 * A button controlled by an {@link io.github.roboblazers7617.buttonbox.Address}.
 */
public class PhysicalButton extends Button {
	/**
	 * Address used for the button state.
	 */
	private final Address address;

	/**
	 * Creates a new PhysicalButton.
	 *
	 * @param id
	 *            The ID string for the PhysicalButton to use.
	 * @param buttonAddress
	 *            The Address for the PhysicalButton to use.
	 * @see io.github.roboblazers7617.buttonbox.Control
	 */
	public PhysicalButton(String id, Address buttonAddress) {
		super(id);
		this.address = buttonAddress;
	}

	@Override
	public void updateClient() {
		super.updateClient();
		if (getState()) {
			address.send(1);
		} else {
			address.send(0);
		}
		address.getFeedbackQueue().forEach((feedback) -> setPressed(feedback != 0));
	}
}
