package kcn.kea.presentation;

import kcn.kea.presentation.IPresent;

import java.io.PrintStream;

public class Console implements IPresent
{
private    PrintStream stream;
    private String topLine;
    private String prefix;
    private String bottomLine;
    public Console(PrintStream printStream)
    {
        stream = printStream;
    }

    public PrintStream getStream()
    {
        return stream;
    }

    public void setStream(PrintStream stream)
    {
        this.stream = stream;
    }

    @Override
    public void setTopLine(String topLine)
    {
        this.topLine = topLine;
    }

    @Override
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    @Override
    public void setBottomLine(String bottomLine)
    {
        this.bottomLine = bottomLine;
    }

    /**
     * Method promises to print the message sorrounded by top/bottom line, and inset by prefix
     *
     * @param message
     */
    @Override
    public void prt(String message)
    {

    }
}
