package io.ph.util;

import java.awt.Color;

import io.ph.bot.Bot;
import io.ph.bot.model.Guild;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class MessageUtils {
	/**
	 * Send a message to a channel
	 * @param channel Channel target
	 * @param content Content of message
	 * @return 
	 */
	public static IMessage sendMessage(IChannel channel, String content) {
		return sendMessage(channel, content, null, false);
	}
	public static IMessage sendMessage(IChannel channel, EmbedObject embed) {
		return sendMessage(channel, "", embed, false);
	}

	/**
	 * Send a message to a channel
	 * @param channel Channel target
	 * @param content Content of message
	 * @param embed EmbedObject to format with
	 */
	private static IMessage sendMessage(IChannel channel, String content, EmbedObject embed, boolean bypass) {
		if(!bypass)
			content = content.replaceAll("@", "\\\\@");
		if(content.equals("") && embed == null)
			return null;
		try {
			return new MessageBuilder(Bot.getInstance().getBot()).withChannel(channel).withContent(content).withEmbed(embed).build();
		} catch (RateLimitException e) {
			e.printStackTrace();
			return null;
		} catch (DiscordException e) {
			e.printStackTrace();
			return null;
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Send a message that shows mentions (for user welcomes)
	 * @param channel Channel to send in
	 * @param content Message to send
	 * @param bypass Bypass to true
	 * @return IMessage built
	 */
	public static IMessage sendMessage(IChannel channel, String content, boolean bypass) {
		return sendMessage(channel, content, null, true);
	}
	public static IMessage sendErrorEmbed(IChannel channel, String title, String description) {
		EmbedBuilder em = new EmbedBuilder().withColor(Color.red).withTitle(title).withDesc(description)
				.withTimestamp(System.currentTimeMillis());
		return sendMessage(channel, em.build());
	}
	
	/**
	 * Create an EmbedBuilder template to notify a user of correct command usage
	 * @param originalCommand Original IMessage sent
	 * @param command Name of the command
	 * @param params String of the parameters you want to represent
	 * @param paramDescription Variable array of parameters and how you want to describe them
	 * @return
	 */
	public static EmbedBuilder commandErrorMessage(IMessage originalCommand, String command, String params, String... paramDescription) {
		String prefix = Guild.guildMap.get(originalCommand.getGuild().getID()).getGuildConfig().getCommandPrefix();
		StringBuilder sb = new StringBuilder();
		for(String s : paramDescription) {
			sb.append(s+"\n");
		}
		return new EmbedBuilder().withColor(Color.RED).withTitle("Usage: " + prefix + command + " " + params)
				.withDesc(sb.toString());
		}

	/**
	 * Send a private message to target user
	 * @param target User to send to
	 * @param content Content of message
	 */
	public static void sendPrivateMessage(IUser target, String content) {
		try {
			IPrivateChannel privChannel = Bot.getInstance().getBot().getOrCreatePMChannel(target);
			privChannel.sendMessage(content);
		} catch (DiscordException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		}
	}
	public static void sendPrivateMessage(IUser target, EmbedObject embed) {
		try {
			IPrivateChannel privChannel = Bot.getInstance().getBot().getOrCreatePMChannel(target);
			privChannel.sendMessage("", embed, false);
		} catch (DiscordException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		}
		
	}
}
