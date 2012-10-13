package com.grizzly.habbohotel.misc;

import java.util.HashMap;
import java.util.Map;

import com.grizzly.Grizzly;
import com.grizzly.habbohotel.misc.commands.*;
import com.grizzly.habbohotel.sessions.Session;

public class ChatCommandParser 
{
	private Map<String, ChatCommand> ChatCommands;
	
	public ChatCommandParser()
	{
		ChatCommands = new HashMap<String, ChatCommand>();
		
		//Default in Grizzly
		AddTo("help", new Help());
		AddTo("coords", new Coords());
	}
	
	private void AddTo(String Name, ChatCommand Command) //2Lazy4Lyfe
	{
		ChatCommands.put(Name, Command);
	}
	
	public boolean ParseChatCommand(Session mSession, String Command)
	{
		if (!ChatCommands.containsKey((SplitCommand(Command, true)[0]).toLowerCase()))
		{
			Grizzly.WriteOut(Command + " is not a valid Console Command!");
			return false;
		}
		
		ChatCommands.get(SplitCommand(Command, true)[0]).Execute(mSession, SplitCommand(Command, false));
		return true;
	}
	
	public Map<String, ChatCommand> GrabCommands()
	{
		return ChatCommands;
	}
	
	private String[] SplitCommand(String Command, boolean Start)
	{
		String[] Split = Command.split(System.getProperty("line.separator"));
		
		if (Start)
		{
			return Split;
		}
		else
		{
			String Arguments = Command.replace(Split[0], "");
			
			return Arguments.split(System.getProperty("line.separator"));
		}
	}
}
