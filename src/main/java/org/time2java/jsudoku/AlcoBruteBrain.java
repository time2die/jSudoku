package org.time2java.jsudoku;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JTextField;

/**
 * @author time2java
 * даный алгоритм посетил меня по дороге с мальчишника домой
 * dump bruttforce alhoritm for sudoky(don't do it in you home without adult people)
 */
public class AlcoBruteBrain {
    
    public static void main(String[] args) {
        AlcoBruteBrain brain = new AlcoBruteBrain(null);
        brain.work();
        brain.sayResults();
    }

    int[][] gameTable;

    private AlcoBruteBrain() {
    }

    ;
    
    AlcoBruteBrain(JTextField[][] fields) {
        gameTable = buildModel(fields);
    }

    private int[][] buildModel(JTextField[][] fields) {
        //todo parse
        int[][] result = {
            {3, 1, 6, 4, 8, 5, 2, 7, 9},
            {9, 0, 5, 1, 7, 2, 3, 4, 6},
            {7, 4, 0, 9, 6, 3, 8, 1, 5},
            {4, 9, 8, 0, 5, 6, 7, 3, 1},
            {6, 2, 7, 8, 0, 1, 9, 5, 4},
            {5, 3, 1, 7, 4, 0, 6, 8, 2},
            {1, 6, 4, 3, 2, 7, 0, 9, 8},
            {2, 7, 9, 5, 1, 8, 4, 0, 3},
            {0, 5, 3, 6, 9, 4, 1, 2, 7}};
        return result;
    }

    List<BruttedCell> initTableModel() {
        ArrayList<BruttedCell> xxx = new ArrayList<>(81);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int iter = gameTable[i][j];
                if (iter == 0) {
                    BruttedCell bIter = new BruttedCell(i, j);
                    xxx.add(bIter);
                }
            }
        }
        xxx.trimToSize();
        return xxx;
    }

    BigInteger initBIModel(List<BruttedCell> xxx) {
        StringBuilder intBuffer = new StringBuilder("");
        for (int i = 0; i < xxx.size(); i++) {
            intBuffer.append(1);
        }

        return new BigInteger(intBuffer.toString());
    }

    BigInteger getMaxValueForBrutt(List<BruttedCell> xxx) {
        StringBuilder intBuffer = new StringBuilder("");
        for (int i = 0; i < xxx.size(); i++) {
            intBuffer.append(9);
        }

        return new BigInteger(intBuffer.toString());
    }

    void printGameTable() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(gameTable[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    void work() {
        List<BruttedCell> xxx = initTableModel();

        BigInteger biModel = initBIModel(xxx);
        BigInteger iter = new BigInteger(biModel.toByteArray());
        BigInteger maxBI = getMaxValueForBrutt(xxx);

        int g = 0;
        for (; iter.compareTo(maxBI) < 0; iter = iter.add(BigInteger.ONE)) {

            String stringModel = iter.toString();
            
            if(stringModel.indexOf('0') != -1){
                continue ;
            }
            
            for (int charIter = 0; charIter < stringModel.length(); charIter++) {
                BruttedCell bcIter = xxx.get(charIter);
                gameTable[bcIter.i][bcIter.j] = Integer.valueOf(stringModel.charAt(charIter) + "");
            }
            
            if(canStope()){
                break ;
            }
            
        }
    }

    void sayResults() {
        printGameTable(); 
    }

    private boolean canStope() {
        //test cell
        for(int [] arrayIter : gameTable){
            if(lineBroken(arrayIter)){
                brokenLine++ ;
                return false ;
            }
        }
        //test row
        for(int i = 0 ; i < 9 ; i ++){
         int cellIter [] = {gameTable[i][0],
                            gameTable[i][1],
                            gameTable[i][2],
                            gameTable[i][3],
                            gameTable[i][4],
                            gameTable[i][5],
                            gameTable[i][6],
                            gameTable[i][7],
                            gameTable[i][8]};
             
         if(lineBroken(cellIter)){
             return false ;
         }
        }
        
        return true ;   
    }
    
    private int brokenLine = 0 ;
    private boolean lineBroken(int [] line){
        //sum test line
        int sum = 0 ;
        for(int lineIter : line){
            sum += lineIter ;
        }
        
        if(sum > 81){
            return true ;
        }
        
        //
        HashSet<Integer> set = new HashSet<Integer>() ;
        for(int lineIter : line){
            if(set.contains(lineIter)){
                return true ;
            }
            set.add(lineIter) ;
        }
        
        return false; 
    }
}

