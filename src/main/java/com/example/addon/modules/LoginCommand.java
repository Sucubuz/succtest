package me.juusk.meteorextras.modules;

import me.juusk.meteorextras.MeteorExtras;
import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.orbit.EventHandler;

import java.util.*;

public class LoginCommand extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("Delay for the command")
        .defaultValue(1000)
        .min(0)
        .sliderMax(50000)
        .build()
    );


    private final Setting<List<String>> commands = sgGeneral.add(new StringListSetting.Builder()
        .name("commands")
        .description("Commands")
        .defaultValue(List.of("/togglepvp"))
        .build()
    );

    private final Setting<List<String>> servers = sgGeneral.add(new StringListSetting.Builder()
        .name("servers")
        .description("Servers (not working)")
        .defaultValue(List.of("localhost"))
        .build()
    );

    private int index = 0;
    private final Timer timer = new Timer();

    public LoginCommand() {
        super(MeteorExtras.CATEGORY, "LoginCommand", "Lets you send commands when joining a server");
        this.runInMainMenu = true;
    }

    @EventHandler
    public void onJoin(GameJoinedEvent event) {
        if (!this.isActive()) return;
        for(String s : servers.get()) {

            String command = commands.get().get(index);
            if (command != null) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (mc.player != null) {
                            mc.player.networkHandler.sendChatMessage(command);
                        }
                    }
                }, delay.get());
            }

        }
    }
}
