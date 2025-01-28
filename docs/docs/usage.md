---
layout: default
title: Usage
---

# Usage

Now that you know how the library works, you can start to do things with it. First, we'll configure the robot-side code, and then we'll create a bridge program to connect your hardware to your robot.

## Robot code

The class on the robot is pretty simple to set up. We'll be working with the class below. Copy this into a Java file named `ButtonBox.java`, and I'll explain what everything does later.

```java
package frc.robot;

import io.github.roboblazers7617.buttonbox.ButtonBoxServer;
import io.github.roboblazers7617.buttonbox.controls.Button;
import io.github.roboblazers7617.buttonbox.controls.Knob;

public class ButtonBox {
	/**
	 * ButtonBoxServer to use to interface with the ButtonBox.
	 */
	private final ButtonBoxServer buttonBoxServer = new ButtonBoxServer();
	/**
	 * Test Button.
	 */
	private final Button testButton;
	/**
	 * Test Knob.
	 */
	private final Knob testKnob;

	/**
	 * Creates a new ButtonBox.
	 */
	public ButtonBox() {
		testButton = new Button("Test Button");
		buttonBoxServer.addControl(testButton);

		testKnob = new Knob("Test Knob");
		buttonBoxServer.addControl(testKnob);
	}

	/**
	 * Gets the test Button.
	 *
	 * @return
	 *         Test Button.
	 */
	public Button getTestButton() {
		return testJoystick;
	}

	/**
	 * Gets the test Knob.
	 *
	 * @return
	 *         Test Knob.
	 */
	public Knob getTestKnob() {
		return testKnob;
	}
}
```

First, we create a [ButtonBoxServer]({{ site.javadoc_url }}/io/github/roboblazers7617/buttonbox/ButtonBoxServer.html). This class is a subsystem, so you'll need to have command-based programming set up for it to work.

```java
private final ButtonBoxServer buttonBoxServer = new ButtonBoxServer();
```

Once we have that, we create some controls. We store these as members of our buttonbox class because we'll end up referencing them later.

```java
testButton = new Button("Test Button");
buttonBoxServer.addControl(testButton);

testKnob = new Knob("Test Knob");
buttonBoxServer.addControl(testKnob);
```

At this point, the only thing left is to initialize this class in RobotContainer and bind some things to the controls.

To initialize the library, put this at the top of the RobotContainer class around where you would create any other controllers.

```java
private final ButtonBox buttonBox = new ButtonBox();
```

Then, just get the controls and bind whatever you want to them.

```java
private void configureBindings() {
	buttonBox.getTestButton().getTrigger().onTrue(Commands.runOnce() -> {
		System.out.println("Button pressed!");
		System.out.println(String.format("The knob is currently set to %f.", buttonBox.getTestKnob().getPosition()));
	});
}
```

## Bridge

Now that we have the robot side of the code finished, we can work on the standalone bridge program. The bridge handles communication between the hardware and the robot program. We will use the MIDI library for the bridge.

Setting up the bridge is a little more involved than the robot program. In the root of your robot repository, create a subdirectory with whatever name you like. Create a `build.gradle` file within that directory, and paste in the build script below, making sure you replace the package name with your own package name.

```gradle
plugins {
	id "java"
	id 'application'
	id 'com.github.johnrengelman.shadow' version '8.1.1'
	id "edu.wpi.first.GradleRIO" version "2025.2.2"
	id 'edu.wpi.first.WpilibTools' version '1.3.0'
}

application {
	mainClass = 'your.package.name'
}

wpilibTools.deps.wpilibVersion = wpi.versions.wpilibVersion.get()

def nativeConfigName = 'wpilibNatives'
def nativeConfig = configurations.create(nativeConfigName)

def nativeTasks = wpilibTools.createExtractionTasks {
	configurationName = nativeConfigName
}

nativeTasks.addToSourceSetResources(sourceSets.main)
nativeConfig.dependencies.add wpilibTools.deps.wpilib("wpimath")
nativeConfig.dependencies.add wpilibTools.deps.wpilib("wpinet")
nativeConfig.dependencies.add wpilibTools.deps.wpilib("wpiutil")
nativeConfig.dependencies.add wpilibTools.deps.wpilib("ntcore")
nativeConfig.dependencies.add wpilibTools.deps.wpilib("cscore")
nativeConfig.dependencies.add wpilibTools.deps.wpilibOpenCv("frc" + wpi.frcYear.get(), wpi.versions.opencvVersion.get())

dependencies {
	implementation wpilibTools.deps.wpilibJava("wpiutil")
	implementation wpilibTools.deps.wpilibJava("wpimath")
	implementation wpilibTools.deps.wpilibJava("wpinet")
	implementation wpilibTools.deps.wpilibJava("ntcore")
	implementation wpilibTools.deps.wpilibJava("cscore")
	implementation wpilibTools.deps.wpilibJava("cameraserver")
	implementation wpilibTools.deps.wpilibOpenCvJava("frc" + wpi.frcYear.get(), wpi.versions.opencvVersion.get())

	implementation group: "com.fasterxml.jackson.core", name: "jackson-annotations", version: wpi.versions.jacksonVersion.get()
	implementation group: "com.fasterxml.jackson.core", name: "jackson-core", version: wpi.versions.jacksonVersion.get()
	implementation group: "com.fasterxml.jackson.core", name: "jackson-databind", version: wpi.versions.jacksonVersion.get()

	implementation group: "org.ejml", name: "ejml-simple", version: wpi.versions.ejmlVersion.get()
	implementation group: "us.hebi.quickbuf", name: "quickbuf-runtime", version: wpi.versions.quickbufVersion.get();
}

shadowJar {
	archiveBaseName = "ButtonBoxBridge"
	archiveVersion = ""
	exclude("module-info.class")
	archiveClassifier.set(wpilibTools.currentPlatform.platformName)
}
```

Now, open your `settings.gradle` file and add in the line below. By doing this, you are turning the subdirectory you created into a Gradle subproject, which allows you to run the tasks in it from your root project. If you run tasks with just the name of the task, Gradle will run that task for all subprojects, so make sure you prefix your tasks with a `:` if you don't want to run them for every subproject (and, if you want to run them for a specific subproject, use the syntax `:SUBPROJECT-NAME:TASK`). The name of your subproject will be the same as the name of the subdirectory you created.

```gradle
include 'YOUR-BRIDGE-SUBPROJECT'
```

Now, create a subdirectory called `vendordeps`, and download the vendordep JSONS for ButtonBoxLib and ButtonBoxLibMIDI to that directory. Once you have that, create a subdirectory called `src/main/java/your/package/name` (substituting your package name in the path), and create a file called `ButtonBoxBridge.java` with the contents below (once again, substituting in your package name, as well as the name of your MIDI device).

```java
package your.package.name;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;

import java.io.IOException;
import java.util.Optional;

import edu.wpi.first.math.jni.WPIMathJNI;
import edu.wpi.first.util.WPIUtilJNI;

import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

import io.github.roboblazers7617.buttonbox.controls.PhysicalButton;
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

		Optional<MIDIDevice> midiDevice = MIDIUtil.getDeviceByName("Your Midi Device Name");

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
		client.addControl(new PhysicalButton("Test Button", new MIDIAddress(midiDevice, ShortMessage.NOTE_ON, 0, 0)));
		client.addControl(new PhysicalPotentiometer("Test Knob", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 0)));
	}
}
```

First, we initialize the program and create a NetworkTableInstance. I won't go into how to do any of this here, since that's not really a part of this library (take a look at WPILib's docs, particularly the page on [creating a NetworkTables client](https://docs.wpilib.org/en/stable/docs/software/networktables/client-side-program.html), if you want to learn more about how that all works). The code in the sample above should work fine for almost any use case.

Then, we create a MIDIDevice. This is the object that will handle the MIDI communication with the actual hardware. We use the MIDIUtil class to do this, which contains a helper function that iterates through all the connected MIDI devices and returns the first one with the name you give.

```java
Optional<MIDIDevice> midiDevice = MIDIUtil.getDeviceByName("Your Midi Device Name");
```

There is also another version of this method that you can give both a transmitter device and a reciever device to.

```java
Optional<MIDIDevice> midiDevice = MIDIUtil.getDeviceByName("Your Midi Reciever Device Name", "Your Midi Transmitter Device Name");
```

Now, we create a ButtonBoxClient. This can actually be done before setting up the MIDIDevice, since it doesn't depend on it, but for this example we'll just do it after.

```java
ButtonBoxClient client = new ButtonBoxClient(inst);
```

Now that we have that, we can create all the controls. For this example, I have a method that does that to keep things organized. Feel free to customize the MIDIAddresses here to match the hardware you are testing with (and, if you don't have any hardware handy, or even if you just want a way to test without having to set up the hardware, take a look at [Open Stage Control](https://openstagecontrol.ammd.net/)).

```java
client.addControl(new PhysicalButton("Test Button", new MIDIAddress(midiDevice, ShortMessage.NOTE_ON, 0, 0)));
client.addControl(new PhysicalPotentiometer("Test Knob", new MIDIAddress(midiDevice, ShortMessage.CONTROL_CHANGE, 0, 0)));
```

Once we have that, the only thing left is to periodically update the library.

```java
while (true) {
	try {
		Thread.sleep(10);
	} catch (InterruptedException ex) {
		System.out.println("interrupted");
		return;
	}
	client.periodic();
}
```

Now that everything is set up, we can go ahead and give it a try. Run your robot program like normal (the library even works in simulation - take a look at the `Other Devices` panel in the simulation view), and run your bridge program by running `./gradlew :YOUR-BRIDGE-SUBPROJECT:run`. If you did everything right above, it should work! Push the button, and see if the robot program prints out `Button pressed!` to the command line, followed by the knob position. Also, take a look at NetworkTables with a tool like AdvantageScope, and you'll be able to see all the data that's being passed around.

# What to do now?

You can build pretty much any control surface from here! Take a look at the [Javadoc]({{ site.javadoc_url }}), and just play around with all the classes there. It's pretty easy to create custom controls if there isn't one for what you want (take a look at the source code of the existing controls and the [Control Javadoc]({{ site.javadoc_url }}/io/github/roboblazers7617/buttonbox/Control.html) for help here), and feel free to [submit pull requests](https://github.com/roboblazers7617/ButtonBoxLib/pulls) to add your controls in. If you run into any bugs, feel free to [create an issue](https://github.com/roboblazers7617/ButtonBoxLib/issues/new), and we'll try our best to fix them.
