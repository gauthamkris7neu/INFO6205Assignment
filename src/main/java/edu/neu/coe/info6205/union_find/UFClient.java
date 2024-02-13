package edu.neu.coe.info6205.union_find;

public class UFClient {
    public static int count(int n) {
        int connection = 0;
        UF h = new UF_HWQUPC(n);
        while(h.components() > 1) {
            int p = (int)(Math.random()*(n));
            int q = (int)(Math.random()*(n));
            if(!h.isConnected(p, q)) {
                h.union(p, q);
            }
            connection++ ;
        }

        return connection;
    }
    public static void main(String args[]) {
        int connection = 0;
        for(int i = 100; i <= 10000; i+=10) {
            connection = count(i);


            for(int j = 0; j < 50 ; j++) {
                connection  += count(i);
            }
            connection /= 50;
            System.out.println("connection of n = " + i + " is " + connection);
            connection = 0;
        }

    }
}
