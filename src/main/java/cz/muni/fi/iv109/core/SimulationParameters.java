package cz.muni.fi.iv109.core;

public record SimulationParameters(

        PrngHolder prngHolder,
        float distancePerStep,
        float communicationRadius,
        float messageFactor,
        float assimilationFactor,
        float k_fertility,
        float r_fertility
) {}
