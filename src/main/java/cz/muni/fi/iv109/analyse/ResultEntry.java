package cz.muni.fi.iv109.analyse;

public record ResultEntry(
        long seed,
        float assimilationFactor,
        float fertilityMultiplier,
        float averageCulture,
        int resultGrid_x,
        int resultGrid_y
) {}
