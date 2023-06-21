package com.agentkosticka.shared;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class SharedMethods {
    public static void sendCustomMessageToClient(String message) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null){
            player.sendMessage(Text.of("§b[MCCI HITW Helper]§r "+message));
        }
    }

}
