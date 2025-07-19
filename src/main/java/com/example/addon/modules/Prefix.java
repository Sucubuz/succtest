package me.juusk.meteorextras.modules;

import me.juusk.meteorextras.MeteorExtras;
import me.juusk.meteorextras.mixin.ChatUtilsAccessor;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


public class Prefix extends Module {

    private final SettingGroup sgPrefix = settings.getDefaultGroup();
    private final SettingGroup sgColor = settings.getDefaultGroup();


    private final Setting<String> seperatorLeft = sgPrefix.add(new StringSetting.Builder()
        .name("seperator-left")
        .description("What the prefix it should be")
        .defaultValue("[")
        .build()
    );

    private final Setting<String> prefix = sgPrefix.add(new StringSetting.Builder()
        .name("prefix")
        .description("What the prefix it should be")
        .defaultValue("MeteorExtras")
        .build()
    );

    private final Setting<String> seperatorRight = sgPrefix.add(new StringSetting.Builder()
        .name("seperator-right")
        .description("What the prefix it should be")
        .defaultValue("]")
        .build()
    );
    private final Setting<SettingColor> color = sgColor.add(new ColorSetting.Builder()
        .name("color")
        .description("The color of the prefix")
        .defaultValue(new SettingColor(255, 0, 0))
        .build()
    );

    private final Setting<SettingColor> seperatorColor = sgColor.add(new ColorSetting.Builder()
        .name("seperator-color")
        .description("The color of the prefix")
        .defaultValue(new SettingColor(Formatting.GRAY))
        .build()
    );




    public Prefix() {
        super(MeteorExtras.CATEGORY, "Prefix", "Changes prefix");
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if(this.isActive()) {
            ChatUtilsAccessor.setPrefix(Text.literal(seperatorLeft.get())
                .withColor(seperatorColor.get().getPacked()).append(
                    Text.literal(prefix.get()).withColor(color.get().getPacked()).append(
                        Text.literal(seperatorRight.get() + " ").withColor(seperatorColor.get().getPacked()))));
        }
    }
}
