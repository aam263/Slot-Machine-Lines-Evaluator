
import java.util.*;

class payoutEvaluator{
    /**
     * Alex Molina
     * 
     * <Introduction>
     * This class takes any valid line (any line with N symbols on it),
     * and returns the payout (including lines with wilds). Using the binaryTrim
     * method, any payLine is allowed (e.g. multidirectional pays and exotic
     * payTable reel combos).
     * 
     * <Assumptions>
     * Everything in this class is only evaluated on valid lines. Be wary of
     * inaccurate floating point arithmetic in calculating alpha.
     * 
     * <Notes>
     * As of now only works for no wilds. 
     */
    
    int N;
    int M;
    int alpha;
    Map<Integer,Map<Integer,Set<Integer>>> payTable;
    Map<Integer,Set<Integer>> wildSubs;
    
    public payoutEvaluator(int N, int M, Map<Integer,Map<Integer,Set<Integer>>> payTable, Map<Integer,Set<Integer>> wildSubs){
        this.N = N;
        this.M = M;
        this.alpha = (int) Math.floor(Math.log(M)/Math.log(2))+1;
        this.payTable = payTable;
        this.wildSubs = wildSubs;
    }
    
    public boolean compareLines(Map<Integer,Integer> validLineSymbols, int payLine, Set<Integer> payLineReels){
         /**
         * <Introduction>
         * This function loops over the payLine reels and checks if the 
         * corresponding reel locations on the validLine match the payLine.
         * If at any point there is not a match, the function breaks out
         * of the loop.
         * 
         * <Purpose>
         * Tells if a validLine matches a specified payLine.
         * 
         * <Assumption>
         * Assumes that every appropriate reelNo from the payLine is contained
         * in validLineSymbols. This is always true if everything is working 
         * properly.
         * 
         * <Complexity>
         * O(N) - time
         * O(1) - memory
         */
        boolean pays = true;
        int selector = (int)Math.pow(2,this.alpha)-1; //alpha bits of 1 to extract reel info.
        for (int reelNo : payLineReels){
            int payLineSymbol = (payLine>>((reelNo-1)*this.alpha))&selector; //test
            int validLineSymbol = validLineSymbols.get(reelNo);
//            System.out.print(validLineSymbol);
//            System.out.print(" ");
//            System.out.print(payLineSymbol);
//            System.out.print(" ");
//            System.out.print(reelNo);
//            System.out.println("");
            if (this.wildSubs.containsKey(validLineSymbol)){ //cant combine these two lines because not sure if wildSubs contains key
                pays = this.wildSubs.get(validLineSymbol).contains(payLineSymbol) ? true : false;
            }
            else{
                pays = (validLineSymbol==payLineSymbol) ? true : false;
            }
            if(!pays){break;}
        }
        return pays;
    }
    
    public int returnMaxPay(int validLine, Map<Integer,Integer> validLineSymbols){
        /**
         * <Introduction>
         * This method takes a valid line and is compared to every payTable
         * combination using validLineSymbols and payLineSymbols; the maxPay 
         * is updated as you loop. Wilds work here.
         * 
         * <Assumptions>
         * Assumes that the input is a valid line.
         * 
         * <Purpose>
         * Returns the maxPay to which the line will be evaluated to.
         * 
         * <Notes>
         * Lines match only to payouts, meaning that payTable payouts with the
         * same values will be grouped together in the output. This doesn't matter 
         * if all we do is calculate RTP.
         * 
         * (Optional) try to get O(P) for time complexity
         * 
         * <Complexity>
         * O(PN) - time (P is number of pay combinations on payTable)
         * O(max(P,N)) - memory 
         */
        int maxPay = 0;      
        for (int payLine : this.payTable.keySet()){
            int payout = this.payTable.get(payLine).keySet().iterator().next(); //extracting from a single element set
            Set<Integer> payLineSymbols = this.payTable.get(payLine).get(payout); 
            if (this.compareLines(validLineSymbols,payLine,payLineSymbols) && maxPay<payout){
                maxPay = payout;
//                System.out.println(Integer.toBinaryString(validLine));
//                System.out.println(Integer.toBinaryString(payLine));
//                System.out.println(maxPay);
            }
        }
        return maxPay;
    }
}
