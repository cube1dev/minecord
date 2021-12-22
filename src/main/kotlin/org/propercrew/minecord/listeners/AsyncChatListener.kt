package org.propercrew.minecord.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.propercrew.minecord.Minecord
import org.propercrew.minecord.utils.FormatModule

class AsyncChatListener: Listener {

    @Suppress("DEPRECATION")
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val plugin = Minecord.instance!!
        val formatModule = FormatModule()
        val channel = Minecord.jda?.getTextChannelById(plugin.config.getString("channelId")!!)
        val format: String = plugin.config.getString("chatFormat") ?: "**<player>**: <message>"
        channel?.sendMessage(formatModule.replaceMsgFormat(event, format))?.queue()
    }
}