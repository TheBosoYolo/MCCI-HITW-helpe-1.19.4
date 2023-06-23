package com.agentkosticka.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import me.x150.renderer.render.Renderer3d;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;


public class OverlayManager {
        public static void renderOutline(BlockPos pos, Color color){
            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            Renderer3d myRenderer = new Renderer3d();
            myRenderer.renderThroughWalls();
            myRenderer.renderOutline(matrixStack, color, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), new Vec3d(1, 1, 1));
        }
}

