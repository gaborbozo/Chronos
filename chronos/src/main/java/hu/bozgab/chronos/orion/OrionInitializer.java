package hu.bozgab.chronos.orion;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OrionInitializer implements CommandLineRunner {
    private final OrionEventListener orionEventListener;

    @Value("${token}")
    private String token;

    @Autowired
    public OrionInitializer(OrionEventListener orionEventListener){
        this.orionEventListener = orionEventListener;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        JDA jda = JDABuilder
                .createDefault(token,
                        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(orionEventListener)
                .build();

        jda.awaitReady();
    }
}
