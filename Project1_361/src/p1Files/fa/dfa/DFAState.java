package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;

public class DFAState extends State {


    private boolean startState, finalState;

    public DFAState(String name) {
        super.name  = name;
        startState = false;
        finalState = false;
        HashMap<Character, DFAState> transitions = new HashMap<>();
    }

    public void setAsStart() {
        startState = true;
    }

    public void toggleFinal() {
        finalState = !finalState;
    }

}
