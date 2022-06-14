package dev.cube1.minecord.core

import dev.cube1.minecord.core.listeners.MessageReceived
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.bukkit.plugin.java.JavaPlugin

lateinit var jda: JDA
lateinit var instance: JavaPlugin

fun jdaInit() {
    val builder = JDABuilder.createDefault(instance.config.getString("token")).apply {
        registerEvents(this)
        addEventListeners(MessageReceived())
    }

    if (instance.config.getBoolean("show_activity")) {
        builder.setActivity(Activity.playing("Minecraft : ${instance.config.getString("server_address")}"))
    }

    jda = builder.build()
    registerData(jda)
//    instance.logger.info("Minecord - Discord module enabled!")
}