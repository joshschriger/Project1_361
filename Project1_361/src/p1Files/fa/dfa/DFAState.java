package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;

public class DFAState extends State {


    private boolean startState, finalState;
    private HashMap<Character, DFAState> transitions;

    public DFAState(String name) {
        super.name  = name;
        startState = false;
        finalState = false;
        transitions = new HashMap<Character, DFAState>();
    }

    public void setAsStart() {
        startState = true;
    }

    public void setAsFinal() {
        finalState = true;
    }//@brayan, did you add this and why?

}
