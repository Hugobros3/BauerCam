package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubPreview implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		PathHandler.switchPreview();
		if (PathHandler.showPreview()) {
			Utils.sendInformation(BauerCamPlugin.renderPreviewOn.toString());
		} else {
			Utils.sendInformation(BauerCamPlugin.renderPreviewOff.toString());
		}
	}

	@Override
	public String getBase() {
		return "preview";
	}

	@Override
	public String getDescription() {
		return "/cam preview";
	}

}
