package me.bauer.BauerCam.compat;

public class CommandException extends Exception
{
	String complaint;
	
	public CommandException(String complaint, Object[] objects)
	{
		this.complaint = complaint;
	}

	public String getMessage()
	{
		return complaint;
	}
}
