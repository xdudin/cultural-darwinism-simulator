package cz.muni.fi.iv109.core.playground;

import cz.muni.fi.iv109.core.Agent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Cell {

    private final int i;
    private final int j;

    private final List<Agent> agents = new ArrayList<>(20);
}
