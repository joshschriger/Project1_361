package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class DFA implements DFAInterface {

    private Set<Character> sigma;
    private Set<DFAState> Q;
    private Set<DFAState> F;
    private DFAState q0;
    private HashMap<String, DFAState> delta;

    public DFA() {
        Q = new LinkedHashSet<>();          // states
        sigma = new LinkedHashSet<>();      // alphabet
        delta = new HashMap<>();            // transition function
        q0 = null;                          // start state
        F = new LinkedHashSet<>();          // final state
    }

    /**
     * Adds the initial state to the DFA instance
     *
     * @param name is the label of the start state
     */
    public void addStartState(String name) {
        q0 = new DFAState(name);
        q0.setAsStart();
        Q.add(q0);
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
        newFinal.toggleFinal();
        F.add(newFinal);
        Q.add(newFinal);
    }

    // ****** HELPER METHODS ******

    /**
     * Looks for the specified DFAState in Q, the set of states
     *
     * @param name is the label of the state we're looking for
     * @return DFAState that's name matches the string or null
     */
    private DFAState getState(String name) {
        for (DFAState state : Q) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    // ****** HELPER METHODS ******


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
            DFAState state = getState(toState);
            delta.put(transition, state);
            //builds Alphabet
            sigma.add(onSymb);
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

//        for (int i = 0; i < s.length(); i++) {
//            currState = (DFAState) getToState(currState, s.charAt(i));
//        }

        for (int i = 0; i < s.length(); i++) {
            if((DFAState) getToState(currState, s.charAt(i)) != null) {
                currState = (DFAState) getToState(currState, s.charAt(i));
            }
        }

        if(F.contains(currState)) {
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
        if (onSymb == 'e') {
            return from;
        }
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
        DFA tempDFA = new DFA();

        tempDFA.Q = this.Q;
        tempDFA.q0 = this.q0;
        tempDFA.sigma = this.sigma;
        tempDFA.delta = this.delta;

        //System.out.println(F); //debug
        //System.out.println(tempDFA.F); //debug

        for (DFAState s : Q) {
            if (!F.contains(s)) {
                tempDFA.F.add(s);
            }
        }

        //System.out.println(tempDFA.F); //debug

        return tempDFA;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Q = { ");
        for (DFAState s : Q)
            builder.append(s.toString()).append(" ");
        builder.append("}\n");

        builder.append("Sigma = { ");
        for (char c : sigma)
            builder.append(c).append(" ");
        builder.append("}\n");

        builder.append("delta = \n\t\t\t\t");

        for (char c : sigma)
            builder.append(c).append("\t\t");
        builder.append("\n");

        for (DFAState s : Q) {
            builder.append("\t\t");
            builder.append(s.toString()).append("\t\t");
            for (char c : sigma)
                builder.append(getToState(s, c)).append("\t\t");
            builder.append("\n");
        }

        builder.append("q0 = ").append(getStartState()).append("\n");

        builder.append("F = { ");
        for (DFAState c : F)
            builder.append(c.toString()).append(" ");
        builder.append("}\n");

        return builder.toString();
    }
}