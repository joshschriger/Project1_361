package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class DFA implements DFAInterface {

    private Set<Character> sigma;               // Alphabet
    private Set<DFAState> Q;                    // states
    private final Set<DFAState> F;              // final states
    private DFAState q0;                        // start state
    private HashMap<String, DFAState> delta;    // transition function

    public DFA() {
        Q = new LinkedHashSet<>();
        sigma = new LinkedHashSet<>();
        delta = new HashMap<>();
        q0 = null;
        F = new LinkedHashSet<>();
    }

    /**
     * Adds the initial state to the DFA instance
     *
     * @param name is the label of the start state
     */
    public void addStartState(String name) {
        q0 = new DFAState(name);
        addState(name);
    }

    /**
     * Adds a non-final, not initial state to the DFA instance
     *
     * @param name is the label of the state
     */
    public void addState(String name) {
        if (!Q.contains(getState(name))) {
            Q.add(new DFAState(name));
        }
    }

    /**
     * Adds a final state to the DFA
     *
     * @param name is the label of the state
     */
    public void addFinalState(String name) {
        DFAState newFinal = new DFAState(name);
        F.add(newFinal);
        if (!Q.contains(getState(name))) {
            Q.add(newFinal);
        }
    }

    // ****** HELPER METHODS ******

    /**
     * Looks for the specified DFAState in Q, the set of states
     *
     * @author JoshSchriger BrayanSilva
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
            delta.put(transition, state);   // adding transition
            sigma.add(onSymb);              // builds alphabet
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

        for (DFAState state : F) {
            if (state.getName().equals(currState.getName())) {
                return true;
            }
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

        return delta.get(from.getName() + onSymb);
    }

    /**
     * Computes a copy of this DFA
     * which language is the complement
     * of this DFA's language.
     *
     * @return a copy of this DFA
     */
    public DFA complement() {
        DFA C_DFA = new DFA();

        C_DFA.Q = this.Q;           // same states
        C_DFA.q0 = this.q0;         // same start state
        C_DFA.sigma = this.sigma;   // same Alphabet
        C_DFA.delta = this.delta;   // same transitions

        // assigns all non-final states from DFA to C_DFA
        for (DFAState s : Q) {
            if (!F.contains(s)) {
                C_DFA.F.add(s);
            }
        }
        return C_DFA;
    }

    /**
     * Uses the data of the DFA to create a formatted string of the
     * DFA's 5-tuple i.e., (Q,Σ,δ,q0,F).
     *
     * @return formatted String of the DFA's 5-tuple
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Q = { ");
        for (DFAState s : Q)
            sb.append(s.getName()).append(" ");
        sb.append("}\n");

        sb.append("Sigma = { ");
        for (char c : sigma)
            sb.append(c).append(" ");
        sb.append("}\n");

        sb.append("delta = \n\t\t\t\t");

        for (char c : sigma)
            sb.append(c).append("\t\t");
        sb.append("\n");

        for (DFAState s : Q) {
            sb.append("\t\t");
            sb.append(s.getName()).append("\t\t");
            for (char c : sigma)
                sb.append(getToState(s, c)).append("\t\t");
            sb.append("\n");
        }

        sb.append("q0 = ").append(getStartState()).append("\n");

        sb.append("F = { ");
        for (DFAState c : F)
            sb.append(c.getName()).append(" ");
        sb.append("}\n");

        return sb.toString();
    }
}