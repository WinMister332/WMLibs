/*
 * ==========
 * Logger.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:17:37 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.logging;

import net.kyori.adventure.title.Title;
import org.checkerframework.checker.mustcall.qual.Owning;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//TODO: Revamp logger so the Ctor doesn't need IOExceptions.
public class Logger {
    @NotNull
    private Object owner;
    private String tag;
    private boolean useLongTimestamp, debug;
    private VerbosityLevel level;
    private LogFile lf;

    public Logger(Object owner, String tag, boolean debug, boolean useLongTimestamp, VerbosityLevel level, File file) throws IOException {
        if (owner == null)
            throw new NullPointerException();
        this.owner = owner;
        if (tag == null || tag.isBlank() || tag.isEmpty())
            tag = owner.getClass().getSimpleName();
        lf = file == null ? null : new LogFile(file);
        this.useLongTimestamp = useLongTimestamp;
        this.debug = debug;
        this.level = level;
    }

    public Logger(Object owner, String tag, boolean debug, boolean useLongTimestamp, VerbosityLevel level) throws IOException {
        this(owner, tag, debug, useLongTimestamp, level, null);
    }

    public Logger(Object owner, String tag, boolean debug, boolean useLongTimestamp) throws IOException {
        this(owner, tag, debug, useLongTimestamp, VerbosityLevel.ALL);
    }

    public Logger(Object owner, boolean debug, boolean useLongTimestamp, VerbosityLevel level) throws IOException {
        this(owner, null, debug, useLongTimestamp, level);
    }

    public Logger(Object owner, String tag, boolean debug) throws IOException {
        this(owner, tag, debug, false);
    }

    public Logger(Object owner, boolean debug, boolean useLongTimestamp) throws IOException {
        this(owner, debug, useLongTimestamp, VerbosityLevel.ALL);
    }

    public Logger(Object owner, String tag) throws IOException {
        this(owner, tag, false);
    }

    public Logger(Object owner) throws IOException {
        this(owner, false, false);
    }

    public Logger(String tag, boolean debug, boolean useLongTimestamp, VerbosityLevel level, File file) throws IOException {
        this(new Object(), tag, debug, useLongTimestamp, level, file);
    }

    public Logger(String tag, boolean debug, boolean useLongTimestamp, VerbosityLevel level) throws IOException {
        this(tag, debug, useLongTimestamp, level, null);
    }

    public Logger(String tag, boolean debug, boolean useLongTimestamp) throws IOException {
        this(tag, debug, useLongTimestamp, VerbosityLevel.ALL);
    }

    public Logger(String tag) throws IOException {
        this(tag, false, false);
    }

    public Object getOwner() {
        return owner;
    }

    public String getTag() {
        return tag == null || tag.isBlank() || tag.isEmpty() ? getOwner().getClass().getSimpleName() : tag;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean usesLongTimestamp() {
        return useLongTimestamp;
    }

    public VerbosityLevel getVerbosityLevel() {
        return level;
    }

    public void setVerbosityLevel(VerbosityLevel level)
    {
        this.level = level;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    private void out(String msg)
    {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println(msg);
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    private void err(String msg)
    {
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
        System.err.println(msg);
    }

    public void log(LogStatus status, String message) {
        DateFormat frmt = new SimpleDateFormat(usesLongTimestamp() ? "YYYY/MM/dd HH:mm:ss" : "HH:mm:ss");
        Date date = new Date();
        String strx = frmt.format(date);

        if (!(status == LogStatus.OFF)) {
            String msg = "[" + strx + " "  + status.toString() + "]: [" + getTag() + "] " + message;
            if (status.isDebugStatus() && !isDebug() || !getVerbosityLevel().accepts(status)) return;

            if (!status.isErrorStatus())
                out(msg);
            else
                err(msg);

            if (lf != null)
            {
                try
                {
                    lf.writeFile(msg);
                }
                catch (Exception ex) {}
            }
        }
    }

    public void logInfo(String message)
    {
        log(LogStatus.INFO, message);
    }

    public void logWarning(String message)
    {
        log(LogStatus.WARNING, message);
    }

    public void logError(String message)
    {
        log(LogStatus.ERROR, message);
    }

    public void logError(String message, Throwable ex)
    {
        logError(message + " -> " + ex.toString());
    }
}
