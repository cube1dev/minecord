package dev.cube1.minecord.core.commands

import dev.cube1.minecord.core.*
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.internal.interactions.CommandDataImpl
import org.bukkit.Bukkit

object OnlineCommand: CommandHandler {

    override var data: CommandData = CommandData.fromData(CommandDataImpl(
        "a",
        "온라인 커맨드 입니다. **채팅이 연동된 채널에서 사용해 주시기 바랍니다.**"
    ).toData())

    override fun execute(event: SlashCommandInteractionEvent) {
        if (event.guild?.id != guildId || event.channel.id != targetChannel?.id) return
        val noMember: String? = instance.config.getString("no_member_message")
        when (instance.config.getInt("style_online_command")) {
            0 -> {
                var memberStr = "**온라인 유저** : \n```"
                memberStr += "인원: ${Bukkit.getOnlinePlayers().size}명/${Bukkit.getMaxPlayers()}명\n"
                if (Bukkit.getOnlinePlayers().isNotEmpty()) {
                    for ((i, player) in Bukkit.getOnlinePlayers().withIndex()) {
                        memberStr += "$i: ${player.name}\n"
                    }
                } else {
                    memberStr += "${noMember ?: "사람이 없습니다."}\n"
                }
                memberStr += "```"

                event.reply(memberStr).queue()
            }

            1 -> {
                val title: String?  = instance.config.getString("embed_title")
                val color: Int      = instance.config.getInt("embed_color")
                val field1: String? = instance.config.getString("embed_field_1")
                val field2: String? = instance.config.getString("embed_field_2")

                val embed = EmbedBuilder().setTitle(title ?: "**온라인 유저**")
                    .setColor(color)
                    .addField(
                        field1 ?: "인원:",
                        "${Bukkit.getOnlinePlayers().size}명/${Bukkit.getMaxPlayers()}명",
                        false
                    )

                var memberStr = "```\n"
                if (Bukkit.getOnlinePlayers().isNotEmpty()) {
                    for ((i, player) in Bukkit.getOnlinePlayers().withIndex()) {
                        memberStr += "${i + 1} 번째: ${player.name} 월드위치: ${player.world.name}\n"
                    }
                } else {
                    memberStr += "${noMember ?: "사람이 없습니다."}\n"
                }
                memberStr += "```"

                embed.addField(field2 ?: "목록:", memberStr, false)
                    .setFooter(event.user.asTag, event.user.avatarUrl)

                val result: MessageEmbed = embed.build()
                event.replyEmbeds(result).queue()
            }
        }
    }
}
