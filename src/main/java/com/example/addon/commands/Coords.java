package me.juusk.meteorextras.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import net.minecraft.command.CommandSource;
import net.minecraft.text.ClickEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Coords extends Command {
    public Coords() {
        super("coords", "Copies your coordinates to clipboard", "coordinates", "coord", "coordinate");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            try {
                String coordsMessage = "x: " + mc.player.getX() + ", y: " + mc.player.getY() + ", z: " + mc.player.getZ();
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection stringSelection = new StringSelection(coordsMessage);
                clipboard.setContents(stringSelection, null);
            } catch(HeadlessException exc) {
                exc.printStackTrace();
            }
            info("Copied coordinates to clipboard!");
            return SINGLE_SUCCESS;
        });
    }
}
