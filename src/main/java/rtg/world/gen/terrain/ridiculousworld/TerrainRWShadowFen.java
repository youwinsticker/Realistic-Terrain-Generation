package rtg.world.gen.terrain.ridiculousworld;

import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;
import rtg.world.gen.terrain.TerrainBase;

public class TerrainRWShadowFen extends TerrainBase
{
    
    public TerrainRWShadowFen()
    {
    
    }
    
    @Override
    public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river)
    {
    
        float h = simplex.noise2(x / 130f, y / 130f) * 30f;
        
        h += simplex.noise2(x / 12f, y / 12f) * 2f;
        h += simplex.noise2(x / 18f, y / 18f) * 4f;
        
        h = h < 4f ? 0f : h - 4f;
        
        if (h == 0f)
        {
            h += simplex.noise2(x / 20f, y / 20f) + simplex.noise2(x / 5f, y / 5f);
        }
        
        return 62f + h;
    }
}
