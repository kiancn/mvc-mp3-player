package kcn.kea;

import kcn.kea.model.Band;
import kcn.kea.model.Genre;
import kcn.kea.model.Track;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData
{
    public List<Track> generateDummyTracks()
    {

        List<String> bandMembersAlpha = Arrays.asList("Alex van Halen", "David Lee Roth", "Wolfgang van Halen");
        List<String> bandMembersGasolin = Arrays.asList("Kim Larsen", "Franz Beckerlee", "Wili Jønsson", "Bjørn Uglebjerg", "Søren Berlev");

        Band alphaBand = new Band("Van Halen Band", bandMembersAlpha, 1956, Arrays.asList(Genre.ROCK, Genre.SEXYTIME));
        Band gasolinBand = new Band("Gasolin", bandMembersGasolin, 1969, Arrays.asList(Genre.ROCK, Genre.SEXYTIME, Genre.FOLK));
        Band overwerk = new Band("Overwerk", Arrays.asList("Edmond Huszar"), 2010, Arrays.asList(Genre.CLASSICAL, Genre.SEXYTIME));

        List<Track> trackList = new ArrayList<>();

        Track gasT1 = new Track("På en sommerdag", 2.3f, Genre.ROCK, gasolinBand, "");
        Track gasT2 = new Track("Se din by fra tårnets top", 2.3f, Genre.SEXYTIME, gasolinBand, "");
        Track gasT3 = new Track("Fi-fi dong", 2.3f, Genre.SEXYTIME, gasolinBand, "");
        Track gasT4 = new Track("Snehvide", 2.3f, Genre.SEXYTIME, gasolinBand, "");
        Track gasT5 = new Track("Nanna", 2.3f, Genre.SEXYTIME, gasolinBand, "");
        trackList.addAll(Arrays.asList(gasT1, gasT2, gasT3, gasT4, gasT5));

        Track vanT1 = new Track("And the Cradle will Rock...", 3.31f, Genre.ROCK, alphaBand, "");
        Track vanT4 = new Track("Everybody want some!!", 3.31f, Genre.ROCK, alphaBand, "");
        Track vanT5 = new Track("Fools", 3.31f, Genre.ROCK, alphaBand, "");
        Track vanT6 = new Track("Romeo Delight", 3.31f, Genre.ROCK, alphaBand, "");
        trackList.addAll(Arrays.asList(vanT1, vanT4, vanT5, vanT6));

        Track ovT1 = new Track("Canon", 3.2f, Genre.SEXYTIME, overwerk, "src/data/OVERWERK-Canon-01Canon.mp3");
        Track ovT2 = new Track("Winter", 3.2f, Genre.SEXYTIME, overwerk, "W:/music/OVERWERK-Canon-05Winter.mp3");
        trackList.addAll(Arrays.asList(ovT1, ovT2));
//            trackList.forEach(System.out::println);

        return trackList;
    }
}
