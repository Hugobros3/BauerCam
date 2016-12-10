package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubStop implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.stopTravelling();
		Utils.sendInformation(BauerCamPlugin.pathStopped.toString());
	}

	@Override
	public String getBase() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "/cam stop";
	}

}
