package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubUndo implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (PathHandler.removeLastWaypoint()) {
			Utils.sendInformation(BauerCamPlugin.pathUndo.toString());
		} else {
			Utils.sendInformation(BauerCamPlugin.pathIsEmpty.toString());
		}
	}

	@Override
	public String getBase() {
		return "undo";
	}

	@Override
	public String getDescription() {
		return "/cam undo";
	}

}
