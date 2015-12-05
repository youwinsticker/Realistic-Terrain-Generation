package rtg.world.gen.surface.enhancedbiomes;

import java.util.Random;

import rtg.util.CellNoise;
import rtg.util.CliffCalculator;
import rtg.util.OpenSimplexNoise;
import rtg.world.biome.realistic.enhancedbiomes.RealisticBiomeEBAlpineMountains;
import enhancedbiomes.api.EBStoneMeta;
import enhancedbiomes.blocks.EnhancedBiomesBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class SurfaceEBAlpineMountainsM extends SurfaceEBBase
{
	private boolean beach;
	private Block beachBlock;
	private float min;
	
	private float sCliff = 1.5f;
	private float sHeight = 60f;
	private float sStrength = 65f;
	private float cCliff = 1.5f;
	
	public byte topByte = 0;
	public byte fillerByte = 0;
	
	public SurfaceEBAlpineMountainsM(Block top, Block fill, boolean genBeach, Block genBeachBlock, float minCliff) 
	{
		super(top, fill);
		beach = genBeach;
		beachBlock = genBeachBlock;
		min = minCliff;
	}
	
	public SurfaceEBAlpineMountainsM(Block top, Block fill, boolean genBeach, Block genBeachBlock, float minCliff, float stoneCliff, float stoneHeight, float stoneStrength, float clayCliff)
	{
		this(top, fill, genBeach, genBeachBlock, minCliff);
		
		sCliff = stoneCliff;
		sHeight = stoneHeight;
		sStrength = stoneStrength;
		cCliff = clayCliff;
	}
	
	@Override
	public void paintTerrain(Block[] blocks, byte[] metadata, int i, int j, int x, int y, int depth, World world, Random rand, OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base)
	{
		float c = CliffCalculator.calc(x, y, noise);
		int cliff = 0;
		boolean gravel = false;
		
    	Block b;
		for(int k = 255; k > -1; k--)
		{
			b = blocks[(y * 16 + x) * 256 + k];
            if(b == Blocks.air)
            {
            	depth = -1;
            }
            else if(b == Blocks.stone)
            {
                depth++;

                if (shouldReplaceStone()) {
                    blocks[(y * 16 + x) * 256 + k] = RealisticBiomeEBAlpineMountains.ebDominantStoneBlock[0];
                    metadata[(y * 16 + x) * 256 + k] = RealisticBiomeEBAlpineMountains.ebDominantStoneMeta[0];
                }
            	
            	if(depth == 0)
            	{
            		if(k < 63)
            		{
            			if(beach)
            			{
            				gravel = true;
            			}
            		}

					float p = simplex.noise3(i / 8f, j / 8f, k / 8f) * 0.5f;
        			if(c > min && c > sCliff - ((k - sHeight) / sStrength) + p)
        			{
        				cliff = 1;
        			}
            		if(c > cCliff)
        			{
        				cliff = 2;
        			}
            		
            		if(cliff == 1)
            		{
                        blocks[(y * 16 + x) * 256 + k] = EnhancedBiomesBlocks.stoneEB; 
                        metadata[(y * 16 + x) * 256 + k] = EBStoneMeta.SLATE;
            		}
            		else if(cliff == 2)
            		{
                        blocks[(y * 16 + x) * 256 + k] = EnhancedBiomesBlocks.stoneCobbleEB; 
                        metadata[(y * 16 + x) * 256 + k] = EBStoneMeta.SLATE;
            		}
            		else if(k < 63)
            		{
            			if(beach)
            			{
	            			blocks[(y * 16 + x) * 256 + k] = beachBlock;
	            			gravel = true;
            			}
            			else if(k < 62)
            			{
                			blocks[(y * 16 + x) * 256 + k] = fillerBlock;
                			metadata[(y * 16 + x) * 256 + k] = fillerByte;
            			}
            			else
            			{
                			blocks[(y * 16 + x) * 256 + k] = topBlock;
                			metadata[(y * 16 + x) * 256 + k] = topByte;
            			}
            		}
            		else
            		{
            			blocks[(y * 16 + x) * 256 + k] = topBlock;
            			metadata[(y * 16 + x) * 256 + k] = topByte;
            		}
            	}
            	else if(depth < 6)
        		{
            		if(cliff == 1)
            		{
                        blocks[(y * 16 + x) * 256 + k] = EnhancedBiomesBlocks.stoneEB; 
                        metadata[(y * 16 + x) * 256 + k] = EBStoneMeta.SLATE;
            		}
            		else if(cliff == 2)
            		{
                        blocks[(y * 16 + x) * 256 + k] = EnhancedBiomesBlocks.stoneCobbleEB; 
                        metadata[(y * 16 + x) * 256 + k] = EBStoneMeta.SLATE;
            		}
            		else if(gravel)
            		{
            			blocks[(y * 16 + x) * 256 + k] = beachBlock;
            		}
            		else
            		{
                        blocks[(y * 16 + x) * 256 + k] = fillerBlock;
                        metadata[(y * 16 + x) * 256 + k] = fillerByte;
            		}
        		}
            }
		}
	}
}
