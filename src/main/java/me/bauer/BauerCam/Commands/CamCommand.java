package me.bauer.BauerCam.Commands;

import io.xol.chunkstories.api.plugin.commands.Command;
import io.xol.chunkstories.api.plugin.commands.CommandEmitter;
import io.xol.chunkstories.api.plugin.commands.CommandHandler;
import me.bauer.BauerCam.BauerCamPlugin;
import me.bauer.BauerCam.Utils;
import me.bauer.BauerCam.compat.CommandException;

public final class CamCommand implements CommandHandler {

	private static final ISubCommand[] commands = { new SubStart(), new SubStop(), new SubGoto(), new SubInsert(),
			new SubRemove(), new SubReplace(), new SubUndo(), new SubClear(), new SubSave(), new SubLoad(),
			new SubTarget(), new SubCircle(), new SubPreview() };

	public String getCommandUsage(final CommandEmitter sender) {
		final StringBuilder s = new StringBuilder();
		s.append(BauerCamPlugin.commands);
		for (final ISubCommand c : commands) {
			s.append("\n");
			s.append(c.getDescription());
		}
		return s.toString();
	}

	@Override
	public boolean handleCommand(CommandEmitter emitter, Command command, String[] args) {
		try {
			if (emitter != Utils.client.getClientSideController().getControlledEntity()) {
				throw new CommandException(BauerCamPlugin.commandHasToBePlayer.toString(), new Object[0]);

			}
			if (args.length == 0) {
				throw new CommandException(getCommandUsage(emitter), new Object[0]);
			}

			final String base = args[0].toLowerCase();

			for (final ISubCommand c : commands) {
				if (c.getBase().equals(base)) {
					c.execute(args);
					return true;
				}
			}

			throw new CommandException(getCommandUsage(emitter), new Object[0]);
		} catch (CommandException e) {
			emitter.sendMessage(e.getMessage());
			return true;
		}
	}

}
