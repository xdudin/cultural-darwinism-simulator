package cz.muni.fi.iv109.core.playground;

import cz.muni.fi.iv109.core.Agent;
import cz.muni.fi.iv109.core.Simulation;
import lombok.Getter;

import java.util.function.Consumer;

public class Grid implements PositionUpdateListener {

    private final int cellSide;
    private final float communicationRadius;
    private final int gridSide; // number of cells on one side of the grid
    private final Cell[][] cells;
    @Getter
    private final Agent[] agents;

    public Grid(float communicationRadius, Agent[] agents) {
        if (Simulation.PLAYGROUND_SIZE % (communicationRadius * 2) != 0)
            throw new IllegalArgumentException("playground size must be divisible by communication radius * 2");

        this.communicationRadius = communicationRadius;
        this.cellSide = (int) (communicationRadius * 2);
        this.gridSide = Simulation.PLAYGROUND_SIZE / cellSide;
        this.cells = new Cell[gridSide][gridSide];

        for (int i = 0; i < gridSide; i++) {
            for (int j = 0; j < gridSide; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        this.agents = agents;
        for(Agent agent: agents) {
            agent.setListener(this);

            float x = agent.getPosition().getX();
            float y = agent.getPosition().getY();
            agent.setParent(computeCell(x, y));

            computeCell(x, y).getAgents().add(agent);
        }
    }

    public void applyWithinCommunicationRadius(Agent agent, Consumer<Agent> operation) {
        float x = agent.getPosition().getX();
        float y = agent.getPosition().getY();

        float xs1 = x - communicationRadius;
        float ys1 = y - communicationRadius;
        float xs2 = x + communicationRadius;
        float ys2 = y + communicationRadius;

        applyForCell(agent, computeCell(xs1, ys1), operation);
        applyForCell(agent, computeCell(xs1, ys2), operation);
        applyForCell(agent, computeCell(xs2, ys1), operation);
        applyForCell(agent, computeCell(xs2, ys2), operation);
    }

    private void applyForCell(Agent agent, Cell cell, Consumer<Agent> operation) {
        for (Agent candidate : cell.getAgents()) {
            if (agent == candidate) continue;

            float ac = agent.getCulture();
            float cc = candidate.getCulture();
            if (ac == 100 && cc == 100) continue;
            if (ac == -100 && cc == -100) continue;

            if (!(candidate.getPosition().distance(agent.getPosition()) < communicationRadius)) continue;

            operation.accept(candidate);
        }
    }

    @Override
    public void onPositionUpdate(Agent agent) {
        Cell parent = agent.getParent();

        float x = agent.getPosition().getX();
        float y = agent.getPosition().getY();

        if (isWithinCell(parent, x, y))
            return;

        parent.getAgents().remove(agent);
        Cell newParent = computeCell(x, y);

        newParent.getAgents().add(agent);
        agent.setParent(newParent);
    }

    private Cell computeCell(float x, float y) {
        int i = (int) Math.floor(x / cellSide);
        int j = (int) Math.floor(y / cellSide);

        if (i > gridSide - 1) i = 0;
        if (j > gridSide - 1) j = 0;

        if (i < 0) i = gridSide - 1;
        if (j < 0) j = gridSide - 1;

        return cells[i][j];
    }

    private boolean isWithinCell(Cell parent, float x, float y) {
        return isWithinSquare(
                x,
                y,
                parent.getI() * cellSide,
                parent.getJ() * cellSide,
                communicationRadius
        );
    }

    private boolean isWithinSquare(float x, float y, float xs, float ys, float side) {
        return x >= xs && x < xs + side
            && y >= ys && y < ys + side;
    }
}
