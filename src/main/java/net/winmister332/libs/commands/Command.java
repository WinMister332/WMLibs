/*
 * ==========
 * Command.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:15:58 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

import java.util.UUID;

/**
 * This class is the base of every WMCommand. The @see CommandForwarder and @see BukkitCommand creates a dynamic instance of this class for a bukkit command.
 * NOTE: Overriding default commands in-favor of WMCommands is neither tested nor supported at this time.
 */
public abstract class Command
{
    private CommandData data;
    private UUID commandID;

    public Command(CommandData data)
    {
        if (data == null)
            throw new NullPointerException();
        commandID = UUID.randomUUID();
        this.data = data;
    }

    UUID getCommandID() { return commandID; }

    /**
     * Gets the underlying data associated with the command that was passed to the CTOR.
     * @return The @see CommandData associated with the command.
     */
    public final CommandData getData() { return data; }

    /**
     * The name of the command.
     * @return A @see String representing the name of the command.
     */
    public final String getName() { return getData().getName(); }

    /**
     * Gets the description of the command.
     * @return A @see String representing the description of the command.
     */
    public final String getDescription() { return getData().getDescription(); }

    /**
     * Gets any alternate names or aliases that are associated with the command.
     * @return A @see String[] of alternate names the command instance will accept.
     */
    public final String[] getAliases() { return getData().getAliases(); }

    /**
     * If true the command is hidden from the custom help command.
     * @return @see boolean
     */
    public final boolean isHidden() { return getData().isHidden(); }
    public final void setHidden(boolean hide) { getData().setHidden(hide); }
    public final void changeName(String newName) { getData().changeName(newName); }

    //TODO: Add CommandProperties class.

    /**
     * Runs when a command is executed by the @see CommandInvoker
     * @param e The properties of the command.
     * @return True if the command executed normally.
     */
    public abstract boolean onInvoke(CommandProperties e);
}
