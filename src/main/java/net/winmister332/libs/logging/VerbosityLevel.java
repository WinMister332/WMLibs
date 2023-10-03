/*
 * ==========
 * VerbosityLevel.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:17:52 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.logging;

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

public class VerbosityLevel
{
    private LogStatus[] accepts;

    VerbosityLevel(LogStatus... accepts)
    {
        this.accepts = accepts;
    }

    LogStatus[] GetAccepts() { return accepts; }

    public boolean accepts(LogStatus status)
    {
        for (LogStatus s : accepts)
        {
            if (s == status)
                return true;
        }
        return false;
    }

    @Deprecated(forRemoval = true)
    private static LogStatus[] createStatusesSingle(LogStatus status)
    {
        return new LogStatus[] {status};
    }

    @Deprecated(forRemoval = true)
    private static LogStatus[] createStatusesUpTo(LogStatus status, boolean goBackwards)
    {
        List<LogStatus> sx = new ArrayList<>();

        if (goBackwards)
        {
            for (int i = 0; i > -6; i--)
            {
                sx.add(LogStatus.getLogStatus(i));
            }

            return (LogStatus[]) sx.toArray();
        }
        else
        {
            for (int i = 0; i < 5; i++)
            {
                sx.add(LogStatus.getLogStatus(i));
            }
            return (LogStatus[]) sx.toArray();
        }
    }

    @Deprecated(forRemoval = true)
    private static LogStatus[] createStatusesUpTo(LogStatus logStatus)
    {
        return createStatusesUpTo(logStatus, false);
    }

    @Deprecated(forRemoval = true)
    private static LogStatus[] createAllStatuses()
    {
        List<LogStatus> sx = new ArrayList<>();
        for (int i = LogStatus.DEBUG_FATAL.getIdentifier(); i < LogStatus.FATAL.getIdentifier() + 1; i++)
        {
            sx.add(LogStatus.getLogStatus(i));
        }
        return (LogStatus[]) sx.toArray();
    }

    public static VerbosityLevel OFF = new VerbosityLevel(LogStatus.OFF);
    public static VerbosityLevel INFO = new VerbosityLevel(LogStatus.INFO);
    public static VerbosityLevel WARNING = new VerbosityLevel(LogStatus.INFO, LogStatus.WARNING);
    public static VerbosityLevel ERROR = new VerbosityLevel(LogStatus.INFO, LogStatus.WARNING, LogStatus.ERROR);
    public static VerbosityLevel SEVERE = new VerbosityLevel(LogStatus.INFO, LogStatus.WARNING, LogStatus.ERROR, LogStatus.SEVERE);
    public static VerbosityLevel FATAL = new VerbosityLevel(LogStatus.INFO, LogStatus.WARNING, LogStatus.ERROR, LogStatus.SEVERE, LogStatus.FATAL);
    public static VerbosityLevel DEBUG = new VerbosityLevel(LogStatus.INFO, LogStatus.DEBUG_INFO, LogStatus.DEBUG_WARNING, LogStatus.DEBUG_ERROR, LogStatus.DEBUG_SEVERE, LogStatus.DEBUG_FATAL);
    public static VerbosityLevel ALL = new VerbosityLevel(LogStatus.values());
}
