package rtg.world.biome.realistic.highlands;

import highlands.api.HighlandsBiomes;
import rtg.api.biome.BiomeConfig;
import rtg.world.gen.surface.highlands.SurfaceHLForestIsland;
import rtg.world.gen.terrain.highlands.TerrainHLForestIsland;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;

public class RealisticBiomeHLForestIsland extends RealisticBiomeHLBase
{
    
    public static BiomeGenBase hlBiome = HighlandsBiomes.forestIsland;
    
    public static Block topBlock = hlBiome.topBlock;
    public static Block fillerBlock = hlBiome.fillerBlock;
    
    public RealisticBiomeHLForestIsland(BiomeConfig config)
    {
    
        super(config, 
            hlBiome, BiomeGenBase.river,
            new TerrainHLForestIsland(90f, 180f, 13f, 100f, 1f, 260f, 59f),
            new SurfaceHLForestIsland(config, topBlock, fillerBlock));
    }

    @Override
    public float rNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river) {
        // no rivers or lakes
        return terrain.generateNoise(simplex, cell, x, y, border, river);
    }

}
