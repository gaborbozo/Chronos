package hu.bozgab.chronos.orion.events.components.voice;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import hu.bozgab.chronos.orion.events.components.voice.exceptions.AudioManagerAlreadyConnectedException;
import hu.bozgab.chronos.orion.events.components.voice.exceptions.AudioManagerNotDefinedException;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoiceManager {

    private AudioManager audioManager;
    private VoiceChannel voiceChannel;
    private AudioPlayerManager audioPlayerManager;
    private AudioPlayer audioPlayer;

    @Autowired
    public VoiceManager(AudioPlayerManager audioPlayerManager){
        this.audioPlayerManager = audioPlayerManager;

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayer = audioPlayerManager.createPlayer();
    }

    public void setAudioManager(AudioManager audioManager){
        this.audioManager = audioManager;
        audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
    }

    public void join(VoiceChannel voiceChannel) throws AudioManagerNotDefinedException, AudioManagerAlreadyConnectedException {
        if (audioManager == null)
            throw new AudioManagerNotDefinedException();

        if(audioManager.isConnected())
            throw new AudioManagerAlreadyConnectedException();

        this.voiceChannel = voiceChannel;
        audioManager.openAudioConnection(voiceChannel);
    }

    public void leave() throws AudioManagerNotDefinedException{
        if (audioManager == null)
            throw new AudioManagerNotDefinedException();

        this.voiceChannel = null;
        audioManager.closeAudioConnection();
    }

}
