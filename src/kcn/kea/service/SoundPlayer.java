package kcn.kea.service;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer implements IPlaySound
{

    boolean playErrorSoundInsteadOfErrorMessage;
    String pathToErrorSound;

    public SoundPlayer()
    {
        playErrorSoundInsteadOfErrorMessage = false;
    }

    public SoundPlayer(String pathToErrorSound, boolean playErrorSoundInsteadOfErrorMessage)
    {
        this.pathToErrorSound = pathToErrorSound;
        this.playErrorSoundInsteadOfErrorMessage = playErrorSoundInsteadOfErrorMessage;
    }

    public boolean isPlayErrorSoundInsteadOfErrorMessage()
    {
        return playErrorSoundInsteadOfErrorMessage;
    }

    public void setPlayErrorSoundInsteadOfErrorMessage(boolean playErrorSoundInsteadOfErrorMessage)
    {
        this.playErrorSoundInsteadOfErrorMessage = playErrorSoundInsteadOfErrorMessage;
    }

    @Override
    public void play(String pathToFile)
    {
        try
        {
            InputStream streamy = new FileInputStream(pathToFile);

            try
            {
                Player player = new Player(streamy);
                player.play();
            } catch(JavaLayerException e)
            {   /*very bad style, because the path to the error sound might be bad, and if is - the exception loops into itself indefinitely */
                if(playErrorSoundInsteadOfErrorMessage){play(pathToErrorSound);} else
                    e.printStackTrace();
            }
        } catch(IOException e)
        {
            if(playErrorSoundInsteadOfErrorMessage){play(pathToErrorSound);} else
                e.printStackTrace();
        }
    }
}
