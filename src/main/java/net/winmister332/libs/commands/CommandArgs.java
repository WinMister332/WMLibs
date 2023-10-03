/*
 * ==========
 * CommandArgs.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:16:10 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

import net.winmister332.libs.utils.Utilities;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the arguments that were passed to the command invoker.
 */
public final class CommandArgs implements Iterable<String> {
    private List<String> args;

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return args.iterator();
    }

    public CommandArgs(String... arguments)
    {
        this(List.of(arguments));
    }

    CommandArgs(List<String> arguments)
    {
        if (arguments == null)
            arguments = new ArrayList<>(0);

        args = arguments;
    }

    /**
     * The size of the argument array.
     * @return the size of the argument array.
     */
    public int count() { return args.size(); }

    /**
     * The size of the argument array.
     * @return the size of the argument array.
     */
    public int size() { return count(); }

    /**
     * Checks if the length of the argument array is empty.
     * @return True if the argument array is empty.
     */
    public boolean isEmpty() { return count() <= 0; }

    /**
     * Gets ab argument at the specified position within the argument array.
     * Treat the position index as if it were an array (The first position is actually 0.)
     * @param position The array-safe position within the array.
     * @return The string which was at the specified position.
     */
    public String getArgumentAt(int position)
    {
        if (!isEmpty())
            return args.get(position);
        else return "";
    }

    /**
     * Gets all the arguments that was passed to a command.
     * @return @see String[]
     */
    public String[] getArguments() { return (String[])args.toArray(); }

    /**
     * Checks if an argument is at the specified position within the argument array.
     * @param position The array-safe position.
     * @param argument The argument/string to check.
     * @return
     */
    public boolean isArgumentAtPosition(int position, String argument)
    {
        if (position >= count())
            position = count() - 1;
        return getArgumentAt(position).equalsIgnoreCase(argument);
    }

    /**
     * Checks if the specified argument exists anywhere in the argument array.
     * @param arg The argument to check.
     * @return True if the argument was found.
     */
    public boolean containsArgument(String arg)
    {
        for (String s : args)
        {
            if (s.equalsIgnoreCase(arg))
                return true;
        }
        return false;
    }

    /**
     * Checks if the specified argument is at the start (index 0) of the array.
     * @param arg The argument to check.
     * @return True if the argument existed at index 0.
     */
    public boolean startsWith(String arg)
    {
        return !isEmpty() && getArgumentAt(0).equalsIgnoreCase(arg);
    }

    /**
     * Checks if the specified argument is at the end of the argument array.
     * @param arg The argument to check.
     * @return True if the argument is at the last position in the array.
     */
    public boolean endsWith(String arg)
    {
        int index = count() - 1;
        if (index < 0)
            index = 0;
        return !isEmpty() && getArgumentAt(index).equalsIgnoreCase(arg);
    }

    /**
     * Gets the index of the specified array if the argument exists anywhere within the argument array.
     * @param arg The argument to check.
     * @return The index of the array. -1 if the argument was NOT found in the array.
     */
    public int indexOf(String arg)
    {
        for (int i = 0; i < count(); i++)
        {
            String x = getArgumentAt(i);
            if (x.equalsIgnoreCase(arg))
                return i;
        }
        return -1;
    }

    //TODO: Get argument before and get argument after functions.

    /**
     * Parse the raw argument string (without command name as that'll just be treated as an argument.
     * @param rawArgs The unprocessed arguments.
     * @return An instance of @see CommandArgs which contains the processed arguments.
     */
    public static CommandArgs parseArguments(String rawArgs)
    {
        return parseArguments(rawArgs, true, false);
    }

    /**
     * Parse the raw argument string (without command name as that'll just be treated as an argument.
     * @param rawArgs The unprocessed arguments.
     * @param parseQuotes If true, it tells the CommandArgs class to treat any data within quotes as its own argument.
     * @param ignoreQuotes If true, it tells the CommandArgs class to ignore innerquotes.
     * @return An instance of @see CommandArgs which contains the processed arguments.
     */
    public static CommandArgs parseArguments(String rawArgs, boolean parseQuotes, boolean ignoreQuotes)
    {
        String[] s;
        if (parseQuotes && (rawArgs.contains("\"") || rawArgs.contains("\\\"")))
        {
            List<String> tokens = new ArrayList<>();
            String currentToken = "";

            boolean isInQuotes = false;
            for (int i = 0; i < rawArgs.length(); i++)
            {
                char c = rawArgs.toCharArray()[i];
                if (c == '\"')
                {
                    if (!isInQuotes)
                        isInQuotes = true;
                    else
                    {
                        if (currentToken.length() > 0)
                            tokens.add(currentToken);
                        currentToken = "";
                        isInQuotes = false;
                    }
                }
                else if ((c == '\\' && isInQuotes) && !ignoreQuotes)
                {
                    int nCharI = rawArgs.indexOf(c);
                    char nChar = rawArgs.toCharArray()[nCharI];
                    if (nChar == '\"')
                    {
                        currentToken += "$[&QUOTE];";
                        rawArgs = Utilities.removeCharAt(rawArgs, i, 1);
                    }
                }
                else
                {
                    if (c == ' ' && !isInQuotes)
                    {
                        if (currentToken.length() > 0)
                            tokens.add(currentToken);
                        currentToken = "";
                    }
                    else
                        currentToken += c;
                }
            }

            isInQuotes = false;

            if (currentToken.length() > 0)
                tokens.add(currentToken);
            currentToken = "";

            if (!ignoreQuotes)
            {
                for (int i = 0; i < tokens.size(); i++)
                {
                    if (tokens.get(i).contains("$[&QUOTE];"))
                        tokens.set(i, tokens.get(i).replace("$[&QUOTE];", "\""));
                }
            }
            s = (String[]) tokens.toArray();
        }
        else
        {
            s = rawArgs.split(" ");
        }

        return new CommandArgs(s);
    }
}
