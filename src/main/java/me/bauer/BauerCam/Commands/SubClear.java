package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubClear implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.clearWaypoints();
		Utils.sendInformation(BauerCamPlugin.pathReset.toString());
		if (PathHandler.hasTarget()) {
			Utils.sendInformation(BauerCamPlugin.pathResetBewareTarget.toString());
		}
	}

	@Override
	public String getBase() {
		return "clear";
	}

	@Override
	public String getDescription() {
		return "/cam clear";
	}

}
