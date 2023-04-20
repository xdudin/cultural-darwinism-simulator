package cz.muni.fi.iv109.core;

public record SimulationParameters(

        float distancePerStep,
        float shiftOnMessage,
        float communicationRadius,
        float assimilationFactor
) {}
