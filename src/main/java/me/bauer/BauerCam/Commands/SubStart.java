package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubStart implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		final long frames = Utils.parseSafely(args[1], 0);
		if (frames <= 0) {
			throw new CommandException(BauerCamPlugin.commandInvalidFrames.toString(), new Object[0]);
		}
		if (PathHandler.getWaypointSize() <= 1) {
			throw new CommandException(BauerCamPlugin.commandAtLeastTwoPoints.toString(), new Object[0]);
		}

		PathHandler.startTravelling(frames);
		Utils.sendInformation(BauerCamPlugin.pathStarted.toString());
	}

	@Override
	public String getBase() {
		return "start";
	}

	@Override
	public String getDescription() {
		return "/cam start <frames>";
	}

}
