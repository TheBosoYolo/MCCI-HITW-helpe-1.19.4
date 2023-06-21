package com.agentkosticka.keys;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_MCCIHELPER = "key.mccihitw.category.main";
    public static final String KEY_KEYBIND_ENABLE = "key.mccihitw.keybind.enable";

    public static KeyBinding enableButton;

    public static void registerInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (enableButton.wasPressed()) {

            }
        });
    }
    public static void register(){
        enableButton = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_KEYBIND_ENABLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_MCCIHELPER
        ));

        registerInputs();
    }
}
