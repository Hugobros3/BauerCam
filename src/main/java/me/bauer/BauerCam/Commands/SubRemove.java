package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.compat.CommandException;

public class SubRemove implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		try {
			final int index = Integer.parseInt(args[1]) - 1;

			if (PathHandler.remove(index)) {
				Utils.sendInformation(BauerCamPlugin.pathPointRemoved.toString() + (index + 1));
			} else {
				Utils.sendInformation(BauerCamPlugin.pathDoesNotExist.toString());
			}
		} catch (final NumberFormatException e) {
			throw new CommandException(BauerCamPlugin.pathDoesNotExist.toString(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "remove";
	}

	@Override
	public String getDescription() {
		return "/cam remove <point>";
	}

}
