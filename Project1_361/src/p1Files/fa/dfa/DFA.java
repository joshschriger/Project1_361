package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DFA implements DFAInterface {


    private Set<Character> sigma;
    private Set<DFAState> Q, F;
    private DFAState q0;
    private HashMap<String, String> delta;

    public DFA() {
        Q = new HashSet<>(); // states
        sigma = new HashSet<>(); // alphabet
        delta = new HashMap<String, String>(); // transition function
        q0 = null; // start state
        F = new HashSet<>(); // final state
    }

    /**
     * Adds the initial state to the DFA instance
     *
     * @param name is the label of the start state
     */
    public void addStartState(String name) {
        addState(name);
        q0 = new DFAState(name);
        q0.setAsStart();

    }

    /**
     * Adds a non-final, not initial state to the DFA instance
     *
     * @param name is the label of the state
     */
    public void addState(String name) {
        Q.add(new DFAState(name));
    }

    /**
     * Adds a final state to the DFA
     *
     * @param name is the label of the state
     */
    public void addFinalState(String name) {
        DFAState newFinal = new DFAState(name);
        addState(name);
        F.add(newFinal);
    }

    /**
     * Adds the transition to the DFA's delta data structure and builds the Alphabet, sigma
     *
     * @param fromState is the label of the state where the transition starts
     * @param onSymb    is the symbol from the DFA's alphabet.
     * @param toState   is the label of the state where the transition ends
     */
    public void addTransition(String fromState, char onSymb, String toState) {
        // reads from the input file, line 4
        String transition = fromState + onSymb;

        if (Q.toString().contains(fromState) && Q.toString().contains(toState)) {
            //builds Alphabet of the language
            sigma.add(onSymb);
            delta.put(transition, toState);
        }
    }

    /**
     * Getter for Q
     *
     * @return a set of states that FA has
     */
    public Set<? extends State> getStates() {
        return Q;
    }

    /**
     * Getter for F
     *
     * @return a set of final states that FA has
     */
    public Set<? extends State> getFinalStates() {
        return F;
    }

    /**
     * Getter for q0
     *
     * @return the start state of FA
     */
    public State getStartState() {
        return q0;
    }

    /**
     * Getter for Sigma
     *
     * @return the alphabet of FA
     */
    public Set<Character> getABC() {
        return sigma;
    }

    /**
     * Simulates a DFA on input s to determine
     * whether the DFA accepts s.
     *
     * @param s - the input string
     * @return true if s in the language of the DFA and false otherwise
     */
    public boolean accepts(String s) {
        DFAState currState = q0;
        for (int i = 0; i < s.length(); i++) {
            currState = (DFAState) getToState(currState, s.charAt(i));
        }

        if (F.contains(currState)) {
            return true;
        }
        return false;
    }

    /**
     * Uses transition function delta of FA
     *
     * @param from   the source state
     * @param onSymb the label of the transition
     * @return the sink state.
     */
    public State getToState(DFAState from, char onSymb) {
        return delta.get(from.toString() + onSymb);
    }

    /**
     * Computes a copy of this DFA
     * which language is the complement
     * of this DFA's language.
     *
     * @return a copy of this DFA
     */
    public DFA complement() {
        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Q = { ");
        for (DFAState c : Q)
            builder.append(c.toString() + " ");
        builder.append("}\n");

        builder.append("Sigma = { ");
        for (char c : sigma)
            builder.append(c + " ");
        builder.append( "}\n");

        builder.append("delta = \n"); //TODO print the table of delta like the one in the sample output

        builder.append("q0 = " + getStartState() + "\n");

        builder.append("F = { ");
        for (DFAState c : F)
            builder.append(c.toString() + " ");
        builder.append("}\n");

        return builder.toString();
    }

}
