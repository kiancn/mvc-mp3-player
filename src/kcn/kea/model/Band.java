package kcn.kea.model;

import java.util.List;

public class Band
{
    private String bandName;
    private List<String> bandMembers;
    private int activeSince;
    private List<Genre> associatedGenres;

    public Band(String bandName, List<String> bandMembers, int activeSince, List<Genre> associatedGenres)
    {
        this.bandName = bandName;
        this.bandMembers = bandMembers;
        this.activeSince = activeSince;
        this.associatedGenres = associatedGenres;
    }

    public String getBandName()
    {
        return bandName;
    }

    public void setBandName(String bandName)
    {
        this.bandName = bandName;
    }

    public List<String> getBandMembers()
    {
        return bandMembers;
    }

    public void setBandMembers(List<String> bandMembers)
    {
        this.bandMembers = bandMembers;
    }

    public List<Genre> getAssociatedGenres()
    {
        return associatedGenres;
    }

    public void setAssociatedGenres(List<Genre> associatedGenres)
    {
        this.associatedGenres = associatedGenres;
    }

    @Override
    public String toString()
    {
        return "Band{" +
               "bandName='" + bandName + '\'' +
               ", bandMembers=" + bandMembers +
               ", activeSince=" + activeSince +
               ", associatedGenres=" + associatedGenres +
               '}';
    }
}
