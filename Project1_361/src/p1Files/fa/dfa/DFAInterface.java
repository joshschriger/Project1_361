package p1Files.fa.dfa;

import p1Files.fa.FAInterface;
import p1Files.fa.State;


public interface DFAInterface extends FAInterface {

    /**
     * Construct the textual representation of the DFA, for example
     * A simple two state DFA
     * Q = { a b }
     * Sigma = { 0 1 }
     * delta =
     * 0	1
     * a	a	b
     * b	a	b
     * q0 = a
     * F = { b }
     * <p>
     * The order of the states and the alphabet is the order
     * in which they were instantiated in the DFA.
     *
     * @return String representation of the DFA
     */
    String toString();

    /**
     * Simulates a DFA on input s to determine
     * whether the DFA accepts s.
     *
     * @param s - the input string
     * @return true if s in the language of the DFA and false otherwise
     */
    boolean accepts(String s);

    /**
     * Uses transition function delta of FA
     *
     * @param from   the source state
     * @param onSymb the label of the transition
     * @return the sink state.
     */
    State getToState(DFAState from, char onSymb);

    /**
     * Computes a copy of this DFA
     * which language is the complement
     * of this DFA's language.
     *
     * @return a copy of this DFA
     */
    DFA complement();

}
