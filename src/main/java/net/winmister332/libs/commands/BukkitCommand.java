/*
 * ==========
 * BukkitCommand.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:15:48 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

//import org.bukkit.command.Command;

import java.util.ArrayList;

/***
 * This class is used to execute a bukkit command as if it were a WMCommand.
 */
public class BukkitCommand extends net.winmister332.libs.commands.Command
{
    private org.bukkit.command.Command command;

    //Creates an instance of CommandData based on the BukkitCommand passed to the Ctor.
    private static CommandData createCommandData(org.bukkit.command.Command command)
    {
        String cmdName = command.getLabel();
        String cmdDesc = command.getDescription();
        String[] cmdAlias = (String[]) command.getAliases().toArray();
        CommandData data = new CommandData(cmdName, cmdDesc, cmdAlias, true);
        return data;
    }

    public BukkitCommand(org.bukkit.command.Command command)
    {
        super(createCommandData(command));
        this.command = command;
    }

    /**
     * Invokes a Bukkit command as if it were a WMCommand.
     * @param e The properties of the command.
     * @return true if the command executed successfully.
     */
    @Override
    public boolean onInvoke(CommandProperties e)
    {
        //Try executing a WMCommand first.
        Command cmd = CommandInvoker.getInstance().getCommand(e.getSentCommandLabel());
        if (cmd == this)
            return command.execute(e.getBukkitCommandSender(), e.getSentCommandLabel(), e.getArgs().getArguments());
        else
            return e.getInvoker().invoke(cmd, e.getArgs(), e.getSentCommandLabel(), e.getBukkitCommandSender());
    }
}
