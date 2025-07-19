package me.juusk.meteorextras.modules;

import me.juusk.meteorextras.MeteorExtras;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FlightPlus extends Module {

    public FlightPlus() {
        super(MeteorExtras.CATEGORY, "Flight+", "Better Meteor Flight");
    }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgAntiKick = settings.createGroup("Anti Kick");

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .defaultValue(1)
        .min(0)
        .sliderMax(100)
        .build()
    );
    private final Setting<Boolean> noSneak = sgGeneral.add(new BoolSetting.Builder()
        .name("no-sneak")
        .description("Prevents you from sneaking while flying.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> delay = sgAntiKick.add(new IntSetting.Builder()
        .name("delay")
        .description("The amount of delay, in ticks, between flying down a bit and return to original position")
        .defaultValue(20)
        .min(1)
        .sliderMax(200)
        .build()
    );

    private final Setting<Integer> amount = sgAntiKick.add(new IntSetting.Builder()
        .name("amount")
        .description("The amount of distance when flying down a bit and return to original position")
        .defaultValue(20)
        .min(1)
        .sliderMax(200)
        .build()
    );

    private int tick = 0;

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if(Utils.canUpdate()) {

           double x = 0,y = 0,z = 0;
            if(mc.options.jumpKey.isPressed()) {
                y += speed.get();
            }
            if(mc.options.sneakKey.isPressed()) {
                y -= speed.get();
            }

            float yaw = MathHelper.wrapDegrees(mc.player.getYaw());
            if(mc.options.forwardKey.isPressed()) {
                x -= Math.sin(Math.toRadians(yaw)) * speed.get();
                z += Math.cos(Math.toRadians(yaw)) * speed.get();
            }
            if(mc.options.leftKey.isPressed()) {

                x += Math.cos(Math.toRadians(yaw)) * speed.get();
                z += Math.sin(Math.toRadians(yaw)) * speed.get();
            }
            if(mc.options.rightKey.isPressed()) {
                x -= Math.cos(Math.toRadians(yaw)) * speed.get();
                z -= Math.sin(Math.toRadians(yaw)) * speed.get();
            }
            if(mc.options.backKey.isPressed()) {
                x += Math.sin(Math.toRadians(yaw)) * speed.get();
                z -= Math.cos(Math.toRadians(yaw)) * speed.get();
            }


            if(tick >= delay.get()) {
                y = y - (double) amount.get() / 100;
                tick=0;
            }
            mc.player.setVelocity(x,y,z);

            if(noSneak.get()) {
                mc.player.setSneaking(false);
            }
        }


        tick++;
    }








}
