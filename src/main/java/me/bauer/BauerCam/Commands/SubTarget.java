package me.bauer.BauerCam.Commands;

import io.xol.chunkstories.api.Location;
import io.xol.chunkstories.api.entity.interfaces.EntityControllable;
import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.Path.PathHandler;
import me.bauer.BauerCam.Path.Vector3D;
import me.bauer.BauerCam.compat.CommandException;

public class SubTarget implements ISubCommand {

	@Override
	public void execute(final String[] args) throws CommandException {
		if (args.length == 1) {
			throw new CommandException(getDescription(), new Object[0]);
		}

		final String op = args[1].toLowerCase();

		if ("set".equals(op)) {
			final EntityControllable player = Utils.client.getPlayer().getControlledEntity();
			Location loc = player.getLocation();
			final Vector3D target = new Vector3D(loc.getX(), loc.getY(), loc.getZ());
			PathHandler.setTarget(target);
			Utils.sendInformation(BauerCamPlugin.pathTargetSet.toString());
		} else if ("off".equals(op)) {
			PathHandler.removeTarget();
			Utils.sendInformation(BauerCamPlugin.pathTargetRemoved.toString());
		} else {
			throw new CommandException(getDescription(), new Object[0]);
		}
	}

	@Override
	public String getBase() {
		return "target";
	}

	@Override
	public String getDescription() {
		return "/cam target <set/off>";
	}

}
