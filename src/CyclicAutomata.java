import com.sun.xml.internal.ws.client.MonitorRootClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * University of Rochester
 * Author: Shengqi Suizhu
 * Date: 2015/12/20
 * CyclicAutomata
 */
public class CyclicAutomata {



    private boolean stopped;
    public static final boolean MOORE = true;
    public static final boolean NEUMAN = !MOORE;
    //the game grid
    private int[][] map;
    private boolean[][] refresh;


    //neighbourhood range
    private int R;

    // threshold - minimal count of cells in the neighbourhood having the next color,
    // necessary for the cell to advance to the next state.
    private int T;

    //count of states in the rule (0..C-1).
    private int C;

    //neighbourhood type: true stands for extended Moore, false for extended von Neumann
    private boolean N;


    CyclicAutomata(int x, int y, int R, int T, int C, boolean N){
        this.map = new int[x][y];
        this.refresh = new boolean[x][y];
        this.R = R;
        this.T = T;
        this.C = C;
        this.N = N;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = (int)(Math.random()*C);
                refresh[i][j] = true;
            }
        }
    }

    //updates the map based on the conditions
    public void update(){
        stopped = true;
        int[][]newMap = new int[map.length][map[0].length];
        //copy of the original map
        for (int i = 0; i < map.length; i++) {
            newMap[i] = Arrays.copyOf(map[i], map[i].length);
        }

        //updating the newMap
        for(int i = 0; i< map.length; i ++){
            for (int j = 0; j < map[0].length; j++) {
                if(refresh[i][j]) {
                    int count = countValidNeighbours(i, j);
                    if (count >= this.T) {
                        refreshValidNeighbours(i,j);
                        stopped = false;
                        //gets eaten by the new value
                        newMap[i][j]++;
                        if (newMap[i][j] > C - 1) {
                            newMap[i][j] = 0;
                        }
                    }
                    else {
                        refresh[i][j] = false;
                    }
                }
            }
        }
        map = newMap;
    }

    public int countValidNeighbours(int x, int y){
        int counter = 0;

        int validNeighbourValue = map[x][y] + 1;
        if(map[x][y]==C-1) {
            validNeighbourValue = 0;
        }
        if(N==MOORE){//moore neighborhood
            for(int i = x-R; i <= x+R; i++){
                for (int j = y-R; j <= y+R; j++) {
                    //bounded by the map
                    if(!(i<0||i>=map.length||j<0||j>=map[0].length)){
                        if(map[i][j]==validNeighbourValue){
                            counter++;
                        }
                    }
                }
            }
            }
        else{//von neuman neighborhood
            for(int i = x-R; i <= x+R; i++){
                for (int j = y-R; j <= y+R; j++) {
                    //bounded by the map
                    if(!(i<0||i>=map.length||j<0||j>=map[0].length)){
                        if((Math.abs(x-i)+Math.abs(y-j))<=R){
                            if(map[i][j]==validNeighbourValue){
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    public void refreshValidNeighbours(int x, int y){
        if(N==MOORE){//moore neighborhood
            for(int i = x-R; i <= x+R; i++){
                for (int j = y-R; j <= y+R; j++) {
                    //bounded by the map
                    if(!(i<0||i>=map.length||j<0||j>=map[0].length)){
                        refresh[i][j] = true;
                    }
                }
            }
        }
        else{//von neuman neighborhood
            for(int i = x-R; i <= x+R; i++){
                for (int j = y-R; j <= y+R; j++) {
                    //bounded by the map
                    if(!(i<0||i>=map.length||j<0||j>=map[0].length)){
                        if((Math.abs(x-i)+Math.abs(y-j))<=R){
                            refresh[i][j] = true;
                        }
                    }
                }
            }
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public boolean getN() {
        return N;
    }

    public void setN(boolean n) {
        N = n;
    }

    public boolean[][] getRefresh() {
        return refresh;
    }

    public void setRefresh(boolean[][] refresh) {
        this.refresh = refresh;
    }
}
