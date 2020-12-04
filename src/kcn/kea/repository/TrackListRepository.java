package kcn.kea.repository;

import kcn.kea.model.Genre;
import kcn.kea.model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrackListRepository implements ITrackRepository
{
    private List<Track> tracks; // represents all tracks registered in program

    private List<Track> currentTrackList; // represents the most current tracks found.

    public TrackListRepository(List<Track> tracks)
    {
        this.tracks = tracks;
    }

    @Override
    public List<Track> findTracksByName(String name)
    {
        ArrayList<Track> result = new ArrayList<>();

        for(Track track : tracks) { if(track.getTitle().contains(name)){result.add(track);} }

        return result;
    }

    @Override
    public List<Track> findTracksByGenre(Genre genre)
    {
        ArrayList<Track> result = new ArrayList<>();

        for(Track track : tracks) { if(track.getGenre().equals(genre)){result.add(track);} }

        return result;

    }

    @Override
    public List<Track> findTracksByBandNameContaining(String bandName)
    {
        ArrayList<Track> result = new ArrayList<>();

        for(Track track : tracks) { if(track.getBand().getBandName().contains(bandName)){result.add(track);} }

        return result;
    }

    @Override
    public List<Track> findAllTracks()
    {
        return tracks;
    }

    @Override
    public void saveTrack(Track track)
    {
        tracks.add(track);
    }

    @Override
    public List<Track> currentTrackList()
    {
        if(Objects.isNull(currentTrackList)){currentTrackList = new ArrayList<>();}
        return currentTrackList;
    }


}
