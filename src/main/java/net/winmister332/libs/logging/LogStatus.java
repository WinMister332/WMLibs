/*
 * ==========
 * LogStatus.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:17:46 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.logging;

import org.bukkit.Color;

public enum LogStatus
{
    OFF(-6),
    DEBUG_FATAL(-5),
    DEBUG_SEVERE(-4),
    DEBUG_ERROR(-3),
    DEBUG_WARNING(-2),
    DEBUG_INFO(-1),
    INFO(0),
    WARNING(1),
    ERROR(2),
    SEVERE(3),
    FATAL(4);

    private int identifier = -6;

    LogStatus(int identifier)
    {
        this.identifier = identifier;
    }

    public int getIdentifier() { return identifier; }

    @Override
    public String toString()
    {
        String x = this.name();
        if (x.contains("_"))
            x = x.replace("_", "/");
        return x.toUpperCase();
    }

    public boolean isDebugStatus()
    {
        return getIdentifier() > OFF.getIdentifier() && getIdentifier() < INFO.getIdentifier();
    }

    public boolean isErrorStatus()
    {
        return (getIdentifier() >= ERROR.getIdentifier()) || (isDebugStatus() && (getIdentifier() <= ERROR.getIdentifier() && getIdentifier() > OFF.getIdentifier()));
    }

    public static LogStatus getLogStatus(int identifier)
    {
        LogStatus[] e = OFF.getDeclaringClass().getEnumConstants();
        for (int i = 0; i < e.length; i++)
        {
            LogStatus s = e[i];
            if (s.getIdentifier() == identifier)
                return s;
        }
        return OFF;
    }
}
