package dev.cube1.minecord.core

import dev.cube1.minecord.core.listeners.MessageReceived
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.bukkit.plugin.java.JavaPlugin

lateinit var jda: JDA
lateinit var instance: JavaPlugin

private val token = instance.config.getString("token").toString()

val guildId = instance.config.getString("guild_id").toString()
val targetChannel = jda.getTextChannelById(
    instance.config.getString("channel_id").toString()
)

var inviteUrl: String
    get() = instance.config.getString("invite_url").toString()
    set(value) = instance.config.set("invite_url", value)

var showActivity: Boolean
    get() = instance.config.getBoolean("show_activity")
    set(value) = instance.config.set("show_activity", value)

var serverAddress: String
    get() = instance.config.getString("server_address").toString()
    set(value) = instance.config.set("server_address", value)

fun jdaInit() {
    val builder = JDABuilder.createDefault(token).apply {
        registerEvents(this)
        addEventListeners(MessageReceived())
    }

    if (showActivity) {
        builder.setActivity(Activity.playing("Minecraft : $serverAddress"))
    }

    jda = builder.build()
    registerData(jda)
//    instance.logger.info("Minecord - Discord module enabled!")
}