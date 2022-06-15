package dev.cube1.minecord

import dev.cube1.minecord.core.inviteUrl
import dev.cube1.minecord.core.serverAddress
import dev.cube1.minecord.core.showActivity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

object CommandDispatcher : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when (command.name) {
            "invite" -> {
                if (args.isEmpty()) {
                    sender.sendMessage("디스코드 주소: $inviteUrl")
                    return true
                }

                if (args[0] == "set" && sender.isOp) {
                    val url = try {
                        args[1]
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        sender.sendMessage(Component.text("디스코드 주소를 입력해 주시기 바랍니다.", NamedTextColor.RED))
                        return false
                    }

                    inviteUrl = url
                    sender.sendMessage(Component.text(
                        "디스코드 주소가 설정 되었습니다: ",
                        NamedTextColor.GREEN
                    ).append(Component.text(
                        url,
                        NamedTextColor.WHITE
                    )))

                    return true
                }

                false
            }

            "minecord" -> {
                if (args.isEmpty()) {
                    sender.sendMessage(Component.text("매개 변수를 입력해 주세요!", NamedTextColor.RED))
                    return false
                }

                if (!sender.isOp) {
                    sender.sendMessage(Component.text("이 명령어는 관리자만 사용할 수 있습니다!", NamedTextColor.RED))
                    return false
                }

                when (args[0]) {
                    "embed" -> {
                        when (args[1]) {
                            else -> return false
                        }

                        return false
                    }

                    "activity" -> {
                        when (args[1]) {
                            "set" -> {
                                val addr = try {
                                    args[2]
                                } catch (exception: ArrayIndexOutOfBoundsException) {
                                    sender.sendMessage(Component.text("주소를 입력해 주세요!", NamedTextColor.RED))
                                    return false
                                }

                                serverAddress = addr
                                return true
                            }

                            "toggle" -> {
                                showActivity = when (args[2]) {
                                    "on" -> {
                                        sender.sendMessage(Component.text()
                                            .content("Activity toggle 설정을").color(NamedTextColor.GREEN)
                                            .content("'on'").color(NamedTextColor.YELLOW)
                                            .content("으로 설정 하셨습니다.").color(NamedTextColor.GREEN)
                                        )
                                        true
                                    }

                                    "off" -> {
                                        sender.sendMessage(Component.text()
                                            .content("Activity toggle 설정을").color(NamedTextColor.GREEN)
                                            .content("'on'").color(NamedTextColor.YELLOW)
                                            .content("으로 설정 하셨습니다.").color(NamedTextColor.GREEN)
                                        )
                                        false
                                    }
                                    else -> {
                                        sender.sendMessage(Component.text()
                                            .content("'on'").color(NamedTextColor.RED)
                                            .content("'또는'").color(NamedTextColor.RED)
                                            .content("'off'").color(NamedTextColor.YELLOW)
                                            .content("만 입력해 주시기 바랍니다.").color(NamedTextColor.RED)
                                        )

                                        return false
                                    }
                                }

                                return true
                            }

                            else -> return false
                        }
                    }
                }

                false
            }

            else -> false
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        if (command.name == "invite") {
            if (args.size == 1) {
                return mutableListOf("set")
            }
        }

        return null
    }
}