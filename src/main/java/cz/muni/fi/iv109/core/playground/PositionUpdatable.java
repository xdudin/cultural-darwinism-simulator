package cz.muni.fi.iv109.core.playground;

import cz.muni.fi.iv109.core.Agent;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PositionUpdatable {

    protected PositionUpdateListener listener;
    protected Cell parent;

    protected void positionUpdate(Agent agent) {
        listener.onPositionUpdate(agent);
    }
}
