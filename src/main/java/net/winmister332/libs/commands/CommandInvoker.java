/*
 * ==========
 * CommandInvoker.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:16:37 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

import net.winmister332.libs.events.Event;
import net.winmister332.libs.utils.Utilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is the class which handles the searching and invocation of all WMCommands.
 */
public final class CommandInvoker implements Iterable<Command>
{
    private List<Command> commands;
    private boolean parseQuotes = true, ignoreInnerQuotes = false;

    /**
     * Gets an instance of the @see CommandInvoker class.
     * @param parseQuotes If true, all arguments surrounded in quotes will be treated as their own argument.
     * @param ignoreInnerQuotes If true, all inner escaped quotes are hidden and not shown as unescaped quotes.
     * @return The instance of the @see CommandInvoker with the specified settings.
     */
    public static CommandInvoker getInstance(boolean parseQuotes, boolean ignoreInnerQuotes) { return new CommandInvoker(parseQuotes, ignoreInnerQuotes); }

    /**
     * Gets an instance of the @see CommandInvoker class.
     * @return The instance of the @see CommandInvoker with the default settings.
     */
    public static CommandInvoker getInstance() { return getInstance(); }

    private CommandInvoker()
    {
        this(true, false);
    }

    private CommandInvoker(boolean parseQuotes, boolean ignoreInnerQuotes)
    {
        commands = new ArrayList<>();
        this.parseQuotes = parseQuotes;
        this.ignoreInnerQuotes = ignoreInnerQuotes;
    }

    /**
     * Registeres a command with the invoker. If the command is not registered it will NOT be invoked.
     * @param command The command to register.
     */
    public void register(Command command)
    {
        if (!commandExists(command))
            commands.add(command);
        else return;
    }

    /**
     * Registeres a collection of Commands one at a time.
     * @param commands The collection of commands to register.
     */
    public void registerAll(Command... commands)
    {
        for (Command c : commands)
            register(c);
    }

    /**
     * Unregisters a specified command, so it cannot be invoked by the invoker.
     * @param c The command to unregister.
     */
    public void unregister(Command c)
    {
        if (commandExists(c))
        {
            int ix = indexOf(c);
            commands.remove(ix);
        }
    }

    /**
     * Unregisters a collection of commands, so they cannot be invoked by the invoker.
     * @param commands The collection of commands to unregister.
     */
    public void unregisterAll(Command... commands)
    {
        for (Command c : commands)
            unregister(c);
    }

    /**
     * Checks if a command is registered and is recognised by the invoker.
     * @param command The command to check.
     * @return True if the command is found.
     */
    public boolean commandExists(Command command)
    {
        if (command == null)
            return false;
        for (Command c : getCommands())
        {
            if (c.getCommandID().equals(command.getCommandID()))
                return true;
        }
        return false;
    }

    /**
     * Gets the index of a command which was registered to the invoker.
     * @param c The command to check.
     * @return The index of the command if found, -1 if not found.
     */
    public int indexOf(Command c)
    {
        for (int i = 0; i < commands.size(); i++)
        {
            if (commands.get(i).getCommandID().equals(c.getCommandID()))
                return i;
        }
        return -1;
    }

    /**
     * Gets all commands registered with the @see CommandInvoker.
     * @return
     */
    public Command[] getAllCommands() { return (Command[]) commands.toArray(); }

    /**
     * Gets only the commands which are visible to the user and are not hidden.
     * @return The visible commands.
     */
    public Command[] getCommands()
    {
        List<Command> commandsx = new ArrayList<>();
        for (Command c : getAllCommands())
        {
            if (!c.isHidden())
                commandsx.add(c);
        }
        return (Command[])commandsx.toArray();
    }

    /**
     * Gets the command with the specified name.
     * @param name The name to check;
     * @return The command with the specified name if one is found.
     */
    public Command getCommandByName(String name)
    {
        for (Command c : getCommands())
        {
            if (c.getName().equalsIgnoreCase(name))
                return c;
        }
        return null;
    }

    /**
     * Gets the command with the specified alias.
     * @param alias The alias to check.
     * @return The command with the specified name if one is found.
     */
    public Command getCommandByAlias(String alias)
    {
        Command c = null;
        for (Command cmd : getCommands())
        {
            String[] aliases = cmd.getAliases();
            if ((aliases != null) && aliases.length > 0)
            {
                for (String a : aliases)
                {
                    boolean isNull = a == null || a.isEmpty() || a.isBlank();
                    if (!isNull && a.equalsIgnoreCase(alias))
                    {
                        c = cmd;
                        break;
                    }
                }
            }
        }
        return c;
    }

    /**
     * Gets the command with the specified name or alias.
     * @param value The name or alias to check.
     * @return The command with the specified name or alias.
     */
    public Command getCommand(String value)
    {
        Command cname = getCommandByName(value);
        Command calias = getCommandByAlias(value);

        boolean nameNull = cname == null;
        boolean aliasNull = calias == null;

        return !nameNull && aliasNull ? cname : nameNull && !aliasNull ? calias : !nameNull && !aliasNull ? cname : null;
    }

    /**
     * Process and invoke a string as a command.
     * @param input The raw input of the command to be processed and invoked.
     * @param sender The sender of the command. (Forwarded by a Bukkit command.)
     * @return True if the command invoked and completed successfully.
     */
    public boolean invoke(String input, org.bukkit.command.CommandSender sender)
    {
        //Split the input by the number of spaces (if any).
        String[] rawArgs = input.split(" ");
        //Get the name from the raw args, or the input passed based on the length of the input.
        String name = rawArgs.length > 0 ? rawArgs[0] : input;

        //Remove the name from the input.
        String s = Utilities.removeCharAt(input, 0, name.length() + 1);
        //Parse the arguments of the command and create instance of CommandArgs.
        CommandArgs args = CommandArgs.parseArguments(s, parseQuotes, ignoreInnerQuotes);

        //Search for instance of command based on the name.
        Command command = getCommand(name);

        //Handle invokation.
        return invoke(getCommand(input), args, name, sender);
    }

    /**
     * Process and invoke a string as a command
     * @param command The command to invoke.
     * @param arguments The arguments associated with the command.
     * @param cmdInput The input label string typed associated with the command (the name or alias).
     * @param sender The sender forwarded by the Bukkit command.
     * @return True if the command executed successfully.
     */
    public boolean invoke(Command command, CommandArgs arguments, String cmdInput, org.bukkit.command.CommandSender sender)
    {
        if (commandExists(command))
        {
            if ((!arguments.isEmpty()) && (arguments.startsWith("help") && !command.getName().equalsIgnoreCase("help")))
            {
                Command helpCmd = getCommand("help");
                //Forward to help if a help command exists.
                if (commandExists(helpCmd))
                    invoke(helpCmd, new CommandArgs(command.getName()), cmdInput, sender);
            }

            CommandProperties e = new CommandProperties(this, arguments, sender, cmdInput);

            return command.onInvoke(e);
        }
        else
            return false;
    }

    @NotNull
    @Override
    public Iterator<Command> iterator() {
        return commands.iterator();
    }
}
