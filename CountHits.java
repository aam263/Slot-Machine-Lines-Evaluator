package AristocratProject_1;

import java.util.*;

class countHits{
    /**
     * Alex Molina
     * 
     * <Introduction>
     * This class encapsulates the ExcelRead, GenerateReelCombinations, and
     * the PayoutEvaluator class. Using these it counts the hits for each payout.
     * 
     * <Assumptions>
     * Assumes validLines and lineMultiplicities have been serialized from the
     * GenerateReelCombinations class. Also assumes a payoutEvaluator class object
     * has been instantiated and passed to the class constructor for countHits.
     * 
     * <Purpose>
     * Generate hits for each payout.
     * 
     * <Notes>
     * Currently uses payoutEvaluator.maxPayEvaluate(validLine) to find maxPay,
     * meaning that this class currently only works for the nonwilds case.
     *  
     */
    int N;
    int M;
    Map<Integer, Map<Integer,Integer>> validLines; //created by generateLines()
    Map<Integer,Integer> lineMultiplicity; //created by generateLines()
    payoutEvaluator payoutEvaluator;  //used to find payouts
    
    public countHits(int N, int M, payoutEvaluator obj){
        this.N = N;
        this.M = M;
        this.payoutEvaluator = obj;
        this.validLines = countHitsSerializer.deserializeValidLines("validLines.ser");
        this.lineMultiplicity = countHitsSerializer.deserializeLineMultiplicity("lineMultiplicities.ser");
    }
     
    public Map<Integer,Long> run(){
        /**
         * <Introduction>
         * Loops through the valid lines and classifies each payout with the number
         * of ways to achieve that payout.
         * 
         * <Assumptions>
         * As of now this only works without including wilds. Assumes validLines and
         * lineMultiplicities has already been serialized and deserialized within this
         * class. Also assumes payoutEvaluator has been instantiated and passed to the
         * constructor of this class.
         * 
         * <Purpose>
         * Calculate hits to determine RTP. Returns a map of the form {payout : hits}.
         * 
         * <Complexity>
         * O(LPN) - time (P is number of payTable combos; L is number of validLines)
         * O(max(N,P)) - memory (payoutEvaluator.maxPayEvaluate is what contributes this)
         * 
         * <Notes>
         * If we loop through only lines without wilds first, can use a hashMap 
         * approach to quickly categorize lines with wilds to find maxPay (put every 
         * nonwild line in a map with its pay as a value, and use that when subbing the
         * wilds. Be wary of inaccurate floating point arithmetic with alpha. You might
         * be able to multithread this part.
         * 
         * Some hits are too large for the integer class, so make these all longs or
         * something.
         */
        
        Map<Integer,Long> hitTable = new HashMap<>();
        for (int validLine : this.validLines.keySet()){
            int pay = this.payoutEvaluator.returnMaxPay(validLine, validLines.get(validLine));////////////////
            int lineMultiplicity = this.lineMultiplicity.get(validLine);//what if line is not present here////////////////////
//            System.out.print(validLines.get(validLine));
//            System.out.print("  ");
//            System.out.print(lineMultiplicity);
//            System.out.print("  ");
//            System.out.print(pay);
//            System.out.println("");
            if (!hitTable.containsKey(pay)){hitTable.put(pay,(long)lineMultiplicity);}
            else{hitTable.put(pay,(hitTable.get(pay)+lineMultiplicity));}
        }
        return hitTable;
    }
}