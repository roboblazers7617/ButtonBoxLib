package io.github.roboblazers7617.buttonbox;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.first.cscore.CameraServerCvJNI;
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.util.WPIUtilJNI;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.DoubleSubscriber;

import io.github.roboblazers7617.buttonbox.ButtonBoxClient;
import io.github.roboblazers7617.buttonbox.controls.TestControl;

/**
 * Bridge program to connect the ButtonBox hardware to NetworkTables.
 */
public class ButtonBoxBridge {
	public static void main(String[] args) throws IOException {
		NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
		WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
		WPIMathJNI.Helper.setExtractOnStaticLoad(false);
		CameraServerJNI.Helper.setExtractOnStaticLoad(false);
		CameraServerCvJNI.Helper.setExtractOnStaticLoad(false);

		CombinedRuntimeLoader.loadLibraries(ButtonBoxBridge.class, "wpiutiljni", "wpimathjni", "ntcorejni", Core.NATIVE_LIBRARY_NAME, "cscorejni");

		Mat m = new Mat();

		new ButtonBoxBridge().run();
	}

	public void run() {
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
		inst.startClient4("ButtonBox Bridge");
		inst.setServer("localhost"); // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
		inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
		ButtonBoxClient client = new ButtonBoxClient(inst);
		client.addControl(new TestControl("Test Control"));
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				System.out.println("interrupted");
				return;
			}
			client.periodic();
		}
	}
}
