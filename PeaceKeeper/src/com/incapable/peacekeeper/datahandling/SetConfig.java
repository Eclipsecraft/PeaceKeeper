package com.incapable.peacekeeper.datahandling;

import com.incapable.peacekeeper.Main;

public class SetConfig {
	Config config;
	Main main;
	PersistantStorage storage;
	
	public SetConfig(Config config, Main main, PersistantStorage storage){
		this.config = config;
		this.main = main;
		this.storage = storage;
	}
	
	public boolean set(String param, Object value) {
		try {
			try
			{
				value = Integer.parseInt((String) value);
			}
			catch(Exception ex)
			{
				try
				{
					value = Boolean.parseBoolean((String) value);
				}
				catch(Exception ex2)
				{
					value = value.toString();
				}
			}
			
			main.getConfig().set(param, value);
			main.saveConfig();
			config.load(main, storage);
			return true;
		}
		catch (Exception ex){
			return false;
		}
	}
}
