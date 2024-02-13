package hu.bozgab.chronos.orion.events.components.voice;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoiceManagerConfiguration {

    @Bean
    public AudioPlayerManager audioPlayerManager(){
        return new DefaultAudioPlayerManager();
    }

}
