package kcn.kea.model;

public class Track
{
    private String title;
    private float duration;
    private Genre genre;
    private Band band;

    private String pathToFile;

    public Track()
    {
    }

    public Track(String title, float duration, Genre genre, Band band, String pathToFile)
    {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.band = band;
        this.pathToFile = pathToFile;
    }

    public Band getBand()
    {
        return band;
    }

    public void setBand(Band band)
    {
        this.band = band;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public float getDuration()
    {
        return duration;
    }

    public void setDuration(float duration)
    {
        this.duration = duration;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    public String getPathToFile()
    {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile)
    {
        this.pathToFile = pathToFile;
    }

    @Override
    public String toString()
    {
        return "Track{" +
               "title='" + title + '\'' +
               ", duration=" + duration +
               ", genre=" + genre +
               ", band=" + band +
               '}';
    }
}
