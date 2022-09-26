package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;

public class DFAState extends State {

    private boolean startState, finalState;

    public DFAState(String name) {
        super.name = name;
        startState = false;
        finalState = false;
    }

    protected void setAsStart() {
        startState = true;
    }

    protected void toggleFinal() {
        finalState = !finalState;
    }

}
