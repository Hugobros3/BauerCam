package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Position;
import me.bauer.BauerCam.compat.CommandException;

public class SubGoto implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}
		try {
			final int index = Integer.parseInt(args[1]) - 1;

			final Position pos = PathHandler.getWaypoint(index);
			if (pos == null) {
				throw new NumberFormatException();
			}
			Utils.teleport(pos, true);

			Utils.sendInformation(BauerCamPlugin.commandTravelledTo.toString() + (index + 1));
		} catch (final NumberFormatException e) {
			throw new CommandException(BauerCamPlugin.pathDoesNotExist.toString(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "goto";
	}

	@Override
	public String getDescription() {
		return "/cam goto <point>";
	}

}
