/*
 * ==========
 * CommandData.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:16:17 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.commands;

public final class CommandData
{
    private String commandName, commandDescription;
    private String[] commandAliases;
    private boolean hidden, canHide;

    public CommandData(String name, String description, String[] aliases, boolean hide)
    {
        if (name == null || (name != null && name.isEmpty() || name.isBlank()))
            throw new NullPointerException();
        commandDescription = description;
        if (name.startsWith("#") || name.startsWith("@") || name.startsWith(".") || name.startsWith("!") || name.startsWith("?") || name.startsWith("$"))
        {
            String s = "";
            for (int i = 0; i < name.length(); i++)
            {
                if (i > 0)
                    s += name.toCharArray()[i];
                else continue;
            }
            name = s;
            canHide = false;
            hide = true;
        }
        else
        {
            canHide = true;
            hidden = hide;
        }
        commandName = name;
    }

    public CommandData(String name, String description, String... aliases)
    {
        this(name, description, aliases, false);
    }

    public String getName() { return commandName; }
    public String getDescription() { return commandDescription; }
    public String[] getAliases() { return commandAliases; }
    public boolean isHidden() { return hidden; }

    public void setHidden(boolean hide)
    {
        if (canHide)
            hidden = hide;
    }

    public void changeName(String newName)
    {
        if (newName.startsWith("#") || newName.startsWith("@") || newName.startsWith(".") || newName.startsWith("!") || newName.startsWith("?") || newName.startsWith("$"))
        {
            String s = "";
            for (int i = 0; i < newName.length(); i++)
            {
                if (i > 0)
                    s += newName.toCharArray()[i];
                else continue;
            }
            newName = s;
        }
        commandName = newName;
    }
}