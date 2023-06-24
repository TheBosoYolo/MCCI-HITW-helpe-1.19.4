package com.agentkosticka.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import me.x150.renderer.render.Renderer3d;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;


public class OverlayManager {
        public static void renderOutline(MatrixStack matrixStack, BlockPos pos, Color color){
            if(pos == null){
                return;
            }
            Renderer3d.renderThroughWalls();
            Renderer3d.renderOutline(matrixStack, color, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), new Vec3d(1, 1, 1));
        }
        public static void renderBigOutline(MatrixStack matrixStack, BlockPos corner1, BlockPos corner2, Color color){
            if(corner1 == null || corner2 == null){
                return;
            }

            int cor1X = corner1.getX();
            int cor1Y = corner1.getY();
            int cor1Z = corner1.getZ();
            int cor2X = corner2.getX();
            int cor2Y = corner2.getY();
            int cor2Z = corner2.getZ();
            BlockPos smallCorner = new BlockPos(cor1X, cor1Y, cor1Z);
            BlockPos bigCorner = new BlockPos(cor2X, cor2Y, cor2Z);
            boolean changed = false;

            if(cor1X > cor2X){
                smallCorner = new BlockPos(cor2X, smallCorner.getY(), smallCorner.getZ());
                bigCorner = new BlockPos(cor1X, bigCorner.getY(), bigCorner.getZ());
                changed = true;
            }
            if(cor1Y > cor2Y){
                smallCorner = new BlockPos(smallCorner.getX(), cor2Y, smallCorner.getZ());
                bigCorner = new BlockPos(bigCorner.getX(), cor1Y, bigCorner.getZ());
                changed = true;
            }
            if(cor1Z > cor2Z){
                smallCorner = new BlockPos(smallCorner.getX(), smallCorner.getY(), cor2Z);
                bigCorner = new BlockPos(bigCorner.getX(), bigCorner.getY(), cor1Z);
                changed = true;
            }
            if(changed){
                renderBigOutline(matrixStack, smallCorner, bigCorner, color);
                return;
            }
            Renderer3d.renderThroughWalls();
            Renderer3d.renderOutline(matrixStack, color, new Vec3d(corner1.getX(), corner1.getY(), corner1.getZ()), new Vec3d((corner2.getX() - corner1.getX()) + 1, (corner2.getY() - corner1.getY()) + 1,(corner2.getZ() - corner1.getZ()) + 1));
        }
}

