package me.bauer.BauerCam.Commands;

import me.bauer.BauerCam.compat.CommandException;

public interface ISubCommand {

	public void execute(String[] args) throws CommandException;

	public String getBase();

	public String getDescription();

}
