package dev.cube1.minecord.core.listeners

import dev.cube1.minecord.core.*
import dev.cube1.minecord.core.utils.FormatModule
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit

class MessageReceived : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        if (event.channelType == ChannelType.PRIVATE) return
        if (event.channel.id == targetChannel?.id) {
            val format: String = instance.config.getString("message_format")
                ?: "<<dark_purple><sender><reset>> <message>"
            val customColor: Boolean = instance.config.getBoolean("custom_color")
            val supportMarkdown: Boolean = instance.config.getBoolean("support_markdown")
            val formatModule = FormatModule()
            val msg = MiniMessage.miniMessage().deserialize(formatModule.replaceChatFormat(
                event,
                format,
                supportMarkdown,
                customColor
            ))
            Bukkit.broadcast(msg)
        }
    }
}