/*
 * ==========
 * CommandForwarder.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:16:24 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

import net.winmister332.libs.events.Event;
import net.winmister332.libs.events.EventArgs;

//Handle Bukkit/Spigot commands and pass to net.winmister332.libs.commands system.
public final class CommandForwarder
{
    public final class CommandEventArgs extends EventArgs
    {
        private CommandProperties properties;
        private boolean commandSucceeded = false;

        public CommandEventArgs(CommandProperties properties)
        {
            this.properties = properties;
        }

        public CommandProperties getProperties() { return properties; }
        public boolean getCommandSucceeded() { return commandSucceeded; }

        void setCommandSucceeded(boolean succeeded)
        {
            commandSucceeded = succeeded;
        }
    }

    public static CommandForwarder getInstance() { return new CommandForwarder(); }

    public Event<CommandEventArgs> commandProcessedEvent = new Event<>();

    public boolean invokeCommand(org.bukkit.command.Command command, org.bukkit.command.CommandSender sender, String[] args, String label)
    {
        BukkitCommand bcmd = new BukkitCommand(command);
        boolean x = CommandInvoker.getInstance().invoke(bcmd, new CommandArgs(args), label, sender);
        CommandEventArgs argsx = new CommandEventArgs(new CommandProperties(CommandInvoker.getInstance(), new CommandArgs(args), sender, label));
        commandProcessedEvent.invoke(this, argsx);
        return x;
    }
}
