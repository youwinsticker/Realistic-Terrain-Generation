package rtg.world.gen.surface.enhancedbiomes;

import java.util.Random;

import rtg.util.CellNoise;
import rtg.util.CliffCalculator;
import rtg.util.OpenSimplexNoise;
import rtg.world.biome.realistic.enhancedbiomes.RealisticBiomeEBRedDesert;
import rtg.world.gen.surface.SurfaceBase;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class SurfaceEBRedDesert extends SurfaceEBBase
{
    
    private Block cliffBlock1;
    private Block cliffBlock2;
    private Block bottomBlock;
    
    public SurfaceEBRedDesert()
    {
    
        super(Blocks.sand, Blocks.sand);
        
        bottomBlock = Blocks.sandstone;
        cliffBlock1 = Blocks.stained_hardened_clay;
    }
    
    @Override
    public void paintTerrain(Block[] blocks, byte[] metadata, int i, int j, int x, int y, int depth, World world, Random rand,
        OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base)
    {
    
        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.4f ? true : false;
        
        for (int k = 255; k > -1; k--)
        {
            Block b = blocks[(y * 16 + x) * 256 + k];
            if (b == Blocks.air)
            {
                depth = -1;
            }
            else if (b == Blocks.stone)
            {
                depth++;

                if (shouldReplaceStone()) {
                    blocks[(y * 16 + x) * 256 + k] = RealisticBiomeEBRedDesert.ebDominantStoneBlock[0];
                    metadata[(y * 16 + x) * 256 + k] = RealisticBiomeEBRedDesert.ebDominantStoneMeta[0];
                }
                
                if (cliff)
                {
                    if (depth < 6)
                    {
                        blocks[(y * 16 + x) * 256 + k] = cliffBlock1;
                        metadata[(y * 16 + x) * 256 + k] = (byte) 14;
                    }
                }
                else if (depth < 6)
                {
                    if (depth == 0 && k > 61)
                    {
                        blocks[(y * 16 + x) * 256 + k] = topBlock;
                        metadata[(y * 16 + x) * 256 + k] = (byte) 1;
                    }
                    else if (depth < 4)
                    {
                        blocks[(y * 16 + x) * 256 + k] = fillerBlock;
                        metadata[(y * 16 + x) * 256 + k] = (byte) 1;
                    }
                    else
                    {
                        blocks[(y * 16 + x) * 256 + k] = bottomBlock;
                    }
                }
            }
        }
    }
}
