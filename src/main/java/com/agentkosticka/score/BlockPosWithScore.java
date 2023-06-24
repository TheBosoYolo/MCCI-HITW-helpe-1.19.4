package com.agentkosticka.score;

import net.minecraft.util.math.BlockPos;

public class BlockPosWithScore {
    private BlockPos blockPos;
    private double score;

    public BlockPosWithScore(BlockPos blockPos, int value) {
        this.blockPos = blockPos;
        this.score = value;
    }
    public BlockPos getBlockPos() {
        return blockPos;
    }

    public double getValue() {
        return score;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setValue(int value) {
        this.score = value;
    }
}
