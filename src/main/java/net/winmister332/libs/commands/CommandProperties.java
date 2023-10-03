/*
 * ==========
 * CommandProperties.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:16:47 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

import net.winmister332.libs.events.EventArgs;

public final class CommandProperties extends EventArgs
{
    private CommandInvoker invoker;
    private CommandArgs args;
    private org.bukkit.command.CommandSender sender;
    private String sentCommandLabel;

    CommandProperties(CommandInvoker invoker, CommandArgs args, org.bukkit.command.CommandSender sender, String sentCommandLabel)
    {
        this.invoker = invoker;
        this.args = args;
        this.sender = sender;
        this.sentCommandLabel = sentCommandLabel;
    }

    public CommandInvoker getInvoker() { return invoker; }
    public CommandArgs getArgs() { return args; }
    public org.bukkit.command.CommandSender getBukkitCommandSender() { return sender; }
    public String getSentCommandLabel() { return sentCommandLabel; }
}
