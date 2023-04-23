package cz.muni.fi.iv109.core;

public record SimulationParameters(

        float distancePerStep,
        float communicationRadius,
        float messageFactor,
        float assimilationFactor,
        float k_fertilityFactor,
        float r_fertilityFactor
) {}
