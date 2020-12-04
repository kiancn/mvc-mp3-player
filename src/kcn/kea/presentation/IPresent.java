package kcn.kea.presentation;

import java.io.PrintStream;

public interface IPresent
{
    void setTopLine(String topLine);
    void setPrefix(String prefix);
    void setBottomLine(String bottomLine);

    /** Method promises to print the message sorrounded by top/bottom line, and inset by prefix */
    void prt(String message);

    void setStream(PrintStream printStream);
    PrintStream getStream();
}
