# CustomDeathMessages is a lightweight, but highly customizable plugin. It comes with default messages, which you can modify, delete, or add any of your own messages. All you have to do is install the plugin, and everything is all set to go! (Works on all versions 1.8 to 1.17.1)

Features
Toggleable and configurable messages sent to the victim and the killer for PvP deaths
Toggleable and configurable global death messages.
All death messages (natural causes, mobs, and players) all have their own spot in config which is configurable with multiple messages.
Custom entities get their own messages! (toggleable)
Update checker which sends a message when the plugin has an update
Toggleable lightning effect when a player is killed.
Customizable percent chance for a player to drop their head on death. (Customizable head name)
Extensive and very customizable config, see the example below.
Built-in placeholders, feel free to request any to be added.
In-Game command to change any messages with ease.
Hex colors for all messages, to use, use &#hex
Toggleable feature where hovering over a message shows the original message.
Toggle feature where hovering over the kill-weapons name will display an item tooltip.

## Commands and Permissions

CDM Command:
/cdm reload - allows you to reload config after making a change.
permission: cdm.reload

/cdm add [path] [new message] - allows you to add messages to config from a command, the 'path' tab autocompletes, so no need to go looking in config.
permission: cdm.add.message

/cdm list [path] - lists the messages for a death message cause, and numbers them which allows you to delete certain messages.
permission: cdm.list

/cdm remove [path] [number] - allows you to delete messages from config, the path is tab completed and the number is the number of the message you want to delete, obtainable from /cdm list.
permission: cdm.remove.message

/cdm toggle [path] - allows you to toggle features in config. The path is tab completed.
permission: cdm.remove.message
IMPORTANT: Use of the command will delete all comments in the config, due to spigot's API. If you need comments, it is suggested you edit messages manually. Always best practice is to keep a backup of your configuration as once something is changed, it can't be undone.

## Update Checker
permission: cdm.updates
allows players with this permission to receive a message when a plugin update is available.

[![Maven Package](https://github.com/Legoman99573/CustomDeathMessages-1/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/Legoman99573/CustomDeathMessages-1/actions/workflows/maven-publish.yml)
