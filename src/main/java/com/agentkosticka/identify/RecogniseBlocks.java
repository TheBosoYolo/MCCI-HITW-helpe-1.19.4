package com.agentkosticka.identify;

import com.agentkosticka.Main;
import com.agentkosticka.shared.SharedMethods;
import com.agentkosticka.shared.SharedVariables;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class RecogniseBlocks {
    private static int terracotaCount = 0;
    private static int bedrockCount = 0;
    public static void findFloorBounds() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }
        int aboveFloorRange = 5;

        BlockPos playerPos = player.getBlockPos();

        int minY = playerPos.getY() - aboveFloorRange;
        int maxY = playerPos.getY();

        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) {
            return;
        }

        BlockPos currentBlock = new BlockPos(playerPos.getX(), maxY, playerPos.getZ());
        int floorYLevel = -64;

        while (currentBlock.getY() >= minY){
            if(world.getBlockState(currentBlock).getBlock() == Blocks.GRAY_GLAZED_TERRACOTTA || world.getBlockState(currentBlock).getBlock() == Blocks.BEDROCK){
                floorYLevel = currentBlock.getY();
                break;
            }
            currentBlock = new BlockPos(currentBlock.getX(), currentBlock.getY() - 1, currentBlock.getZ());
        }
        if(floorYLevel == -64){
            return;
        }
        int maxX = currentBlock.getX();
        int maxZ = currentBlock.getZ();
        int minX = currentBlock.getX();
        int minZ = currentBlock.getZ();

        boolean newBlockFound = true;
        while (newBlockFound){
            newBlockFound = false;

            //Expand positive x
            calculateBlockCounts(new BlockPos(maxX + 1, floorYLevel, maxZ), new BlockPos(maxX + 1, floorYLevel, minZ));
            if(terracotaCount != 0 || bedrockCount != 0){
                newBlockFound = true;
                maxX = maxX + 1;
            }

            //Expand positive z
            calculateBlockCounts(new BlockPos(maxX, floorYLevel, maxZ + 1), new BlockPos(minX, floorYLevel, maxZ + 1));
            if(terracotaCount != 0 || bedrockCount != 0){
                newBlockFound = true;
                maxZ = maxZ + 1;
            }

            //Expand negative x
            calculateBlockCounts(new BlockPos(minX - 1, floorYLevel, maxZ), new BlockPos(minX - 1, floorYLevel, minZ));
            if(terracotaCount != 0 || bedrockCount != 0){
                newBlockFound = true;
                minX = minX - 1;
            }

            //Expand negative z
            calculateBlockCounts(new BlockPos(maxX, floorYLevel, minZ - 1), new BlockPos(minX, floorYLevel, minZ - 1));
            if(terracotaCount != 0 || bedrockCount != 0){
                newBlockFound = true;
                minZ = minZ - 1;
            }
        }

        SharedVariables.floorCorner1 = new BlockPos(minX, floorYLevel, minZ);
        SharedVariables.floorCorner2 = new BlockPos(maxX, floorYLevel, maxZ);
    }
    public static void calculateBlockCounts(BlockPos pos1, BlockPos pos2) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) {
            return;
        }

        int[] blockCounts = new int[2]; // Index 0: gray glazed terracotta count, Index 1: bedrock count

        int minX = Math.min(pos1.getX(), pos2.getX());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockPos pos = new BlockPos(x, minY, z);
                Block block = world.getBlockState(pos).getBlock();

                if (block == Blocks.GRAY_GLAZED_TERRACOTTA) {
                    blockCounts[0]++;
                } else if (block == Blocks.BEDROCK) {
                    blockCounts[1]++;
                }
            }
        }

        terracotaCount = blockCounts[0];
        bedrockCount = blockCounts[1];

        Main.LOGGER.info("Running calculateBlockCount! Found "+terracotaCount+" gray terracotas and "+bedrockCount+" bedrocks");
    }
}
