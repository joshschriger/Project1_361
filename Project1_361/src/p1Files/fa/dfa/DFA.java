package p1Files.fa.dfa;

import p1Files.fa.State;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;


public class DFA implements DFAInterface {

    private Set<Character> sigma;
    private Set<DFAState> Q;
    private final Set<DFAState> F;
    private DFAState q0;
    private HashMap<String, DFAState> delta;

    public DFA() {
        Q = new LinkedHashSet<>(); // states
        sigma = new LinkedHashSet<>(); // alphabet
        delta = new HashMap<>(); // transition function
        q0 = null; // start state
        F = new LinkedHashSet<>(); // final state
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
        newFinal.toggleFinal();
        F.add(newFinal);
    }


    /**
     * Takes a String a returns a DFAState from Q, the set of states
     *
     * @return DFA state that's name matches the string or null
     */
    public DFAState stringToState(String s) {
        for (DFAState state : Q) {
            if (s.equals(state.toString()))
                return state;
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
            //builds Alphabet of the language
            sigma.add(onSymb);
            DFAState value = stringToState(toState);
            delta.put(transition, value);
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
            //System.out.println(i); //debug
            //System.out.println(s.charAt(i)); //debug
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
        System.out.println(F);
        System.out.println(tempDFA.F);


        tempDFA.F.clear();
        //TODO this is adding all states rather than just the complement of the original, everything else is working
        for (DFAState s : Q) {
            if (!F.contains(s)) {
                tempDFA.F.add(s);
            }
        }

        //System.out.println(tempDFA); //debug

        return tempDFA;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Q = { ");
        for (DFAState s : Q)
            sb.append(s.toString()).append(" ");
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
            sb.append(s.toString()).append("\t\t");
            for (char c : sigma)
                sb.append(getToState(s, c)).append("\t\t");
            sb.append("\n");
        }

        sb.append("q0 = ").append(getStartState()).append("\n");

        sb.append("F = { ");
        for (DFAState c : F)
            sb.append(c.toString()).append(" ");
        sb.append("}\n");

        return sb.toString();
    }
}