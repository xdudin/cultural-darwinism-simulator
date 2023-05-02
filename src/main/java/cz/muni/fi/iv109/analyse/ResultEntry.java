package cz.muni.fi.iv109.analyse;

public record ResultEntry(
        long seed,
        float assimilationFactor,
        float fertilityFactor,
        float averageCulture,
        int assimilationFactor_x,
        int fertilityFactor_y
) {}
