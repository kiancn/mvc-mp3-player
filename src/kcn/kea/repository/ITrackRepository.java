package kcn.kea.repository;

import kcn.kea.model.Genre;
import kcn.kea.model.Track;

import java.util.List;

public interface ITrackRepository
{
    List<Track> findTracksByName(String name);
    List<Track> findTracksByGenre(Genre genre);
    List<Track> findTracksByBandNameContaining(String bandName);
    List<Track> findAllTracks();

    void saveTrack(Track track);

    List<Track> currentTrackList();
}
