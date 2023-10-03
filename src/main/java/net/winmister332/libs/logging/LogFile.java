/*
 * ==========
 * LogFile.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:17:28 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.logging;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class LogFile {
    public enum LogFileType {
        DIRECTORY,
        FILE
    }

    private File file;

    LogFile(String path) throws IOException {
        this(new File(path));
    }

    LogFile(File file) throws IOException {
        if (file.isFile() && !file.isDirectory()) {
            if (!file.getName().toLowerCase().endsWith(".log"))
                file = new File(file.getParentFile().getAbsolutePath(), file.getName() + ".log");
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();
        } else {
            File nFile = new File(file.getAbsolutePath(), getTimestamp(true) + ".log");
            if (!nFile.exists())
                nFile.createNewFile();
            file = nFile;
        }
        this.file = file;
    }

    public String readFile() throws IOException {
        try (FileInputStream fis = new FileInputStream(file))
        {
            if (fis.available() <= 0)
                return "";
            else {
                byte[] b = new byte[fis.available()];
                fis.readNBytes(b, 0, b.length);
                return getString(b);
            }
        }
    }

    public void writeFile(String data) throws IOException {
        String s = readFile();
        StringBuilder b = new StringBuilder();
        if (!(s.isEmpty() && s.isBlank()))
            b.append(s);
        b.append(data);
        s = b.toString();
        try (FileOutputStream fos = new FileOutputStream(file))
        {
            byte[] bytes = getBytes(s);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
        }
    }

    private static char[] getChars(byte[] b)
    {
        char[] cx = new char[b.length];
        for (int i = 0; i < b.length; i++)
            cx[i] = (char)b[i];
        return cx;
    }

    private static byte[] getBytes(char[] c)
    {
        byte[] b = new byte[c.length];
        for (int i = 0; i < b.length; i++)
            b[i] = (byte) c[i];
        return b;
    }

    private static byte[] getBytes(String s)
    {
        return getBytes(s.toCharArray());
    }

    public static String getString(byte[] b)
    {
        return new String(getChars(b));
    }

    private static String getTimestamp(boolean useFileSafe)
    {
        String xFrmt = useFileSafe ? "yyyy-MM-dd_HH-mm-ss" : "yyyy/MM/dd @ HH:mm:ss";
        DateFormat frmt = new SimpleDateFormat(xFrmt);
        Timestamp ts = new Timestamp(frmt.getCalendar().getTimeInMillis());
        return ts.toString().toUpperCase();
    }
}
