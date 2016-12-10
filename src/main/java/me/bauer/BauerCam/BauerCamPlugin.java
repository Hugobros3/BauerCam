package me.bauer.BauerCam;

import java.io.File;

import io.xol.chunkstories.api.client.ClientInterface;
import io.xol.chunkstories.api.input.Input;
import io.xol.chunkstories.api.plugin.ClientPlugin;
import io.xol.chunkstories.api.plugin.PluginInformation;
import io.xol.chunkstories.content.GameDirectory;
import me.bauer.BauerCam.Commands.CamCommand;

public class BauerCamPlugin extends ClientPlugin {

	/**
	 * Lower case will be required for 1.11 and beyond
	 */
	public static final String modId = "bauercam";
	public static final String name = "BauerCam";
	public static final String version = "1.12";
	//public static final String minecraftTargetVersion = "1.11";

	public static Input point;
	public static Input fovHigh;
	public static Input fovLow;
	public static Input fovReset;
	public static Input cameraClock;
	public static Input cameraCounterClock; 
	public static Input cameraReset;
	
	public  static File bauercamDirectory;

	public BauerCamPlugin(PluginInformation pluginInformation, ClientInterface clientInterface) {
		super(pluginInformation, clientInterface);
		
		clientInterface.getInputsManager().getInputByName("bauercam.key.addPoint");
		
		point = clientInterface.getInputsManager().getInputByName("bauercam.key.addPoint");
		fovHigh = clientInterface.getInputsManager().getInputByName("bauercam.key.fovHigh");
		fovLow = clientInterface.getInputsManager().getInputByName("bauercam.key.fovLow");
		fovReset = clientInterface.getInputsManager().getInputByName("bauercam.key.fovReset");
		cameraClock = clientInterface.getInputsManager().getInputByName("bauercam.key.clockwise");
		cameraCounterClock = clientInterface.getInputsManager().getInputByName("bauercam.key.counterClockwise");
		cameraReset = clientInterface.getInputsManager().getInputByName("bauercam.key.reset");

		bauercamDirectory = new File(GameDirectory.getGameFolderPath()+"/bauercam/");
		bauercamDirectory.mkdirs();
		
		Utils.setup(this);
	}

	@Override
	public void onEnable() {
		this.getPluginManager().registerEventListener(EventListener.instance, this);
		this.getPluginManager().registerCommandHandler("cam", new CamCommand());
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Localized strings
	 */

	public final static LocalizedString pathStarted = new LocalizedString("bauercam.path.started");
	public final static LocalizedString pathStopped = new LocalizedString("bauercam.path.stopped");
	public final static LocalizedString pathIsEmpty = new LocalizedString("bauercam.path.isEmpty");
	public final static LocalizedString pathIsPopulated = new LocalizedString("bauercam.path.isPopulated");
	public final static LocalizedString pathUndo = new LocalizedString("bauercam.path.undo");
	public final static LocalizedString pathDoesNotExist = new LocalizedString("bauercam.path.doesNotExist");
	public final static LocalizedString pathReset = new LocalizedString("bauercam.path.reset");
	public final static LocalizedString pathResetBewareTarget = new LocalizedString("bauercam.path.resetBewareTarget");
	public final static LocalizedString pathReplace = new LocalizedString("bauercam.path.replace");
	public final static LocalizedString pathAdd = new LocalizedString("bauercam.path.add");
	public final static LocalizedString pathTargetSet = new LocalizedString("bauercam.path.targetSet");
	public final static LocalizedString pathTargetRemoved = new LocalizedString("bauercam.path.targetRemoved");
	public final static LocalizedString pathCircleCreated = new LocalizedString("bauercam.path.circleCreated");
	public final static LocalizedString pathPointInserted = new LocalizedString("bauercam.path.pointInserted");
	public final static LocalizedString pathPointRemoved = new LocalizedString("bauercam.path.pointRemoved");

	public final static LocalizedString commands = new LocalizedString("bauercam.cmd.commands");
	public final static LocalizedString commandHasToBePlayer = new LocalizedString("bauercam.cmd.hasToBePlayer");
	public final static LocalizedString commandTravelledTo = new LocalizedString("bauercam.cmd.travelledTo");
	public final static LocalizedString commandAtLeastTwoPoints = new LocalizedString("bauercam.cmd.atLeastTwoPoints");
	public final static LocalizedString commandInvalidFrames = new LocalizedString("bauercam.cmd.invalidFrames");

	public final static LocalizedString renderPreviewOn = new LocalizedString("bauercam.render.previewOn");
	public final static LocalizedString renderPreviewOff = new LocalizedString("bauercam.render.previewOff");

	public final static LocalizedString exportSuccessful = new LocalizedString("bauercam.exporter.successfulWrite");
	public final static LocalizedString importSuccessful = new LocalizedString("bauercam.exporter.successfulRead");
	public final static LocalizedString IOError = new LocalizedString("bauercam.exporter.IOError");
	public final static LocalizedString positionCannotBeParsed = new LocalizedString(
			"bauercam.exporter.posCannotBeParsed");
	public final static LocalizedString fileDoesNotExist = new LocalizedString("bauercam.exporter.fileDoesNotExist");
	public final static LocalizedString fileAlreadyExists = new LocalizedString("bauercam.exporter.fileAlreadyExists");

}
