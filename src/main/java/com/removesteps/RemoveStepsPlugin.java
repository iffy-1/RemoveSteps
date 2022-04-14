package com.removesteps;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Arrays;

import static net.runelite.api.ObjectID.STEPS_29778;

@Slf4j
@PluginDescriptor(
	name = "Remove Steps"
)
public class RemoveStepsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RemoveStepsConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}
	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event){
		System.out.println("CALLLED THIS FUNCTION!!!!!!!!!!!");
		MenuEntry[] newEntries = Arrays.stream(client.getMenuEntries())
				.filter(menuEntry -> !(menuEntry.getTarget().contains("Steps")))
				.toArray(MenuEntry[]::new);
		client.setMenuEntries(newEntries);
		Arrays.stream(client.getMenuEntries()).forEach(e-> System.out.println(e.toString()));
	}

	@Provides
	RemoveStepsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RemoveStepsConfig.class);
	}
}
