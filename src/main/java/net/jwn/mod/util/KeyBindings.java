package net.jwn.mod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.jwn.mod.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY_MOD = "key.category." + Main.MOD_ID + ".mod_category";
    public static final String KEY_TEST = "key." + Main.MOD_ID + ".test";
    public static final KeyMapping TEST_KEY =
            new KeyMapping(KEY_TEST, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_MOD);
}
