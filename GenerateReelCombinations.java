package AristocratProject_1;

import java.util.*;

class generateReelCombinations{
    /**
     * Alex Molina
     * 
     *<Introduction>
     * This class generates all possible combinations of reel outputs,
     * along with the multiplicity of each possible reel combination.
     * This takes into account all possible ways a single combo can be
     * achieved given different amount of symbol counts on each reel.
     * 
     * The methodology is representing each line as a binary representation
     * depending on the symbols that appear on each reel.
     * 
     * <Limitations of Class>
     * This class is solely used to generate the possible reel lines for N reels
     * and M symbols, with different symbol counts on each reel. Everything from
     * this class will be passed into (insert payout class name here). Be wary of
     * inaccurate floating point arithmetic in calculating alpha.
     */
    
    int N; //number of reels
    int M; // number of symbols
    int alpha; //number of bits needed to represent a symbol
    Map<String,Integer> symbolConversions; //<integer,integer> - containsSymbol would be better, would change generate
    Map<Integer,Map<Integer,Integer>> symbolCounts; //<integer,integer> - no change to return Mult,
    
    public generateReelCombinations(int N, int M, Map<String,Integer> symbolConversions, Map<Integer,Map<Integer,Integer>> symbolCounts){
        this.N = N;
        this.M = M;
        this.alpha = (int) Math.floor(Math.log(M)/Math.log(2))+1; 
        this.symbolConversions = symbolConversions;
        this.symbolCounts = symbolCounts;
    }
    
    public Map<Integer,Integer> containsSymbols(int line){
        /**
         * <Introduction>
         * This method checks any line representation and, by checking
         * the different bits representing the different reels, we see
         * what reel symbols it contains and which reel that symbol belongs to.
         * The Binary reps are read from right to left, starting with the rightmost 
         * alpha bits.
         * 
         * <Purpose>
         * Returns a map containing the string symbols and their reel locations
         * in the form {reelNo : Symbol} for an individual line.
         * 
         * <Complexity>
         * O(NM) - time
         * O(N) - memory
         */
        Map<Integer,Integer> symbolLocs = new HashMap<>(); 
        int reelNo = 1; 
        while(reelNo<=this.N){
            int shift = (reelNo-1)*this.alpha; 
            int reelRep = (line>>shift)-((line>>(shift+this.alpha))<<this.alpha); 
            for (int symbolRep : this.symbolCounts.keySet()){ //symbolCounts contains all symbols
                if (reelRep==symbolRep){
                    symbolLocs.put(reelNo, symbolRep);
//                    System.out.print(symbol+" ");
//                    System.out.print(reelNo);
//                    System.out.println("");
                }
            }
            reelNo+=1;
        }
        return symbolLocs;
    }
    
    public int returnMult(Map<Integer,Integer> validLineSymbols){
        /**
         * <Introduction>
         * Each reel combo is going to be associated with various symbols on each 
         * reel that have various amounts of way of obtaining that symbol (stored
         * in symbolCounts). THis function basically takes an assumed valid line
         * (a valid line contains N symbol reps) and returns the number of ways
         * the same line can come up in the reel setup.
         * 
         * <Assumptions>
         * Assumes that the input is a line which has N symbols and is thus a 
         * valid outcome for the reel setup. Assumes that validLineSymbols is
         * the outcome of the "containsSymbols" method used on a line.
         * 
         * <Purpose>
         * To get the multiplicity of a single reel outcome.
         * 
         * <Complexity>
         * O(N) - time 
         * O(1) - memory
         * 
         * <Notes>
         * If multiplicities get too large might have to use longs.
         */
        int mult = 1; 
        for (int reel : validLineSymbols.keySet()){
            mult = mult*this.symbolCounts.get(validLineSymbols.get(reel)).get(reel);
        }
        return mult;       
    }
    
    public void generate(){
        /**
         * <Introduction>
         * Loops from 1 to the largest binary number that can be made using N
         * reels and alpha bits per reel to represent the symbols. For each number
         * it sees if this is a possible outcome, and if it is valid, finds the
         * multiplicity of the valid line on the reel setup. 
         * 
         * <Purpose>
         * Generate all possible valid lines and their associated multiplicity. The
         * results are stored in two distinct maps: validLines & lineMultiplicity.
         * 
         * <Notes>
         * Two maps are used here instead of one because we want to have the line, 
         * its associated reel symbols, and its multiplicity. Thus we create two maps
         * of the forms {line : {reelNo : symbol}} & {line : multiplicity}. 
         * 
         * <Complexity>
         * O((2^(N*alpha))*NM) - time
         * O(LN) - memory (L is number of valid lines)
         */
        int line=1;
        Map<Integer, Map<Integer,Integer>> validLines = new HashMap<>();
        Map<Integer,Integer> lineMultiplicities = new HashMap<>();
        while (line <= Math.pow(2, this.N*this.alpha)-1){
            Map<Integer,Integer> validLineSymbols = this.containsSymbols(line);
            if (validLineSymbols.size()== this.N){
//                System.out.print(validLineSymbols);
//                System.out.print(" ");
//                System.out.print(this.returnMult(validLineSymbols));
//                System.out.println("");
                lineMultiplicities.put(line, this.returnMult(validLineSymbols));//O(N)
                validLines.put(line, validLineSymbols);
            }
           line+=1;
        }
        countHitsSerializer.serialize(validLines,"validLines.ser");
        countHitsSerializer.serialize(lineMultiplicities,"lineMultiplicities.ser");
    }    
}