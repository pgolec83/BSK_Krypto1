/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;

public class DesEncryption {
    private DesKey desKey;
    private final int[] IP;
    private final int[] P;
    private final int[] E;

    private final int[][][] S;

    private final int[] invertedIP;

    public DesEncryption(BitSet key) {
        this.desKey = new DesKey(key);
        IP = new int[] {    58,50,42,34,26,18,10,2,
                            60,52,44,36,28,20,12,4,
                            62,54,46,38,30,22,14,6,
                            64,56,48,40,32,24,16,8,
                            57,49,41,33,25,17,9,1,
                            59,51,43,35,27,19,11,3,
                            61,53,45,37,29,21,13,5,
                            63,55,47,39,31,23,15,7};

        E = new int[] {     32,1,2,3,4,5,
                            4,5,6,7,8,9,
                            8,9,10,11,12,13,
                            12,13,14,15,16,17,
                            16,17,18,19,20,21,
                            20,21,22,23,24,25,
                            24,25,26,27,28,29,
                            28,29,30,31,32,1};

        S = new int[][][] {
                            {   {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                                {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
                                {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                                {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
                            },
                            {   {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
                                {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
                                {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
                                {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
                            },
                            {   {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
                                {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
                                {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
                                {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
                            },
                            {   {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
                                {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
                                {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
                                {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
                            },
                            {   {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
                                {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
                                {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
                                {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
                            },
                            {   {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
                                {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
                                {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
                                {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
                            },
                            {   {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
                                {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
                                {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
                                {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
                            },
                            {   {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
                                {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
                                {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
                                {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
                            }
        };

        P = new int[] {     16,7,20,21,
                            29,12,28,17,
                            1,15,23,26,
                            5,18,31,10,
                            2,8,24,14,
                            32,27,3,9,
                            19,13,30,6,
                            22,11,4,25};

        invertedIP = new int[] {    40,8,48,16,56,24,64,32,
                                    39,7,47,15,55,23,63,31,
                                    38,6,46,14,54,22,62,30,
                                    37,5,45,13,53,21,61,29,
                                    36,4,44,12,52,20,60,28,
                                    35,3,43,11,51,19,59,27,
                                    34,2,42,10,50,18,58,26,
                                    33,1,41,9,49,17,57,25};
    }

    public long encrypt(BitSet block) {
        BitSet tmp = new BitSet(64);

        FileWriter fileWriter = null;                                       //do zapisu kolejnych operacji algorytmu
        try {
            fileWriter = new FileWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fileWriter);
        desKey.logPrintNumbers(64,pw);                             //wypisanie numerow 1-64 dla poprawy widoczonsci logu
        pw.println("Początkowy blok:");
        desKey.logPrintBitSet(block,pw,64);

        for(int i=63; i>=0; i--) {                                  //premutacja IP bloku danych, zaczynamy od 63 a nie od 0 bo w dokumentacji numerowanie jest odwrotne niz w BitSet-cie
            if(block.get(64-IP[63-i]))                              //np dla i=0 IP[63]->58, 64-58->6 czyli 7 bit liczac od prawej do lewej a 58 bit liczac od lewej do prawej ktorego wartosc ustalana jest na najbardziej znaczacym bicie
                tmp.set(i);
            else
                tmp.clear(i);
        }

        pw.println("Blok po permutacji IP:");
        desKey.logPrintBitSet(tmp,pw,64);

        BitSet r = tmp.get(0,32);                                   //blok p jest blokiem mniej znaczacych bitow
        BitSet l = tmp.get(32,64);                                  //blok l jest blokiem bardziej znaczacych bitow

        pw.println("l:");                                          //wypisanie blokow c i d
        desKey.logPrintBitSet(l,pw,32);
        pw.println("r:");
        desKey.logPrintBitSet(r,pw,32);

        for(int j = 0; j<16; j++) {                                 //pętla 16 operacji z kluczem

            BitSet rTo48Bits = new BitSet(48);
            BitSet finalChanged = new BitSet();

            for (int i = 47; i >= 0; i--) {                         //permutacja E bloku Rj
                if (r.get(32 - E[47 - i]))
                    rTo48Bits.set(i);
                else
                    rTo48Bits.clear(i);
            }

            pw.println();
            desKey.logPrintNumbers(48,pw);
            pw.println("Permutacja E bloku R"+j);
            desKey.logPrintBitSet(rTo48Bits,pw,48);

            rTo48Bits.xor(desKey.getK()[j]);                        //xorowanie bloku Rj z kluczem Kj
            pw.println("Klucz K"+(j+1));
            desKey.logPrintBitSet(desKey.getK()[j],pw,48);
            pw.println("Wynik XOR");
            desKey.logPrintBitSet(rTo48Bits,pw,48);
            pw.println();
            desKey.logPrintNumbers(32,pw);
            BitSet changed = changeBitsBySTable(rTo48Bits);         //zamiana bitow wg tabel S1-S8
            pw.println("Zmiana po tabelach S:");
            desKey.logPrintBitSet(changed,pw,32);


            for(int i=31; i>=0; i--) {
                if(changed.get(32-P[31-i]))
                    finalChanged.set(i);
                else
                    finalChanged.clear(i);
            }

            pw.println("Permutacja P");
            desKey.logPrintBitSet(finalChanged,pw,32);


            pw.println();
            desKey.logPrintNumbers(32,pw);
            finalChanged.xor(l);
            l = r;
            r = finalChanged;
            pw.println("Nowe L:");
            desKey.logPrintBitSet(l,pw,32);
            pw.println("Nowe R:");
            desKey.logPrintBitSet(r,pw,32);

        }

        tmp = merge(r,l);

        desKey.logPrintNumbers(64,pw);
        pw.println("Finalne scalenie:");
        desKey.logPrintBitSet(tmp,pw,64);

        for(int i=63; i>=0; i--) {
            if(tmp.get(64-invertedIP[63-i]))
                block.set(i);
            else
                block.clear(i);
        }

        pw.println("Permutacja Inverse IP:");
        desKey.logPrintBitSet(block,pw,64);

        pw.close();
        return convert(block);
    }
    
    public long unencrypt(BitSet block) {
        BitSet tmp = new BitSet(64);

        FileWriter fileWriter = null;                                       //do zapisu kolejnych operacji algorytmu
        try {
            fileWriter = new FileWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fileWriter);
        desKey.logPrintNumbers(64,pw);                             //wypisanie numerow 1-64 dla poprawy widoczonsci logu
        pw.println("Początkowy blok:");
        desKey.logPrintBitSet(block,pw,64);

        for(int i=63; i>=0; i--) {                                  //premutacja IP bloku danych, zaczynamy od 63 a nie od 0 bo w dokumentacji numerowanie jest odwrotne niz w BitSet-cie
            if(block.get(64-IP[63-i]))                              //np dla i=0 IP[63]->58, 64-58->6 czyli 7 bit liczac od prawej do lewej a 58 bit liczac od lewej do prawej ktorego wartosc ustalana jest na najbardziej znaczacym bicie
                tmp.set(i);
            else
                tmp.clear(i);
        }

        pw.println("Blok po permutacji IP:");
        desKey.logPrintBitSet(tmp,pw,64);

        BitSet r = tmp.get(0,32);                                   //blok p jest blokiem mniej znaczacych bitow
        BitSet l = tmp.get(32,64);                                  //blok l jest blokiem bardziej znaczacych bitow

        pw.println("l:");                                          //wypisanie blokow c i d
        desKey.logPrintBitSet(l,pw,32);
        pw.println("r:");
        desKey.logPrintBitSet(r,pw,32);

        for(int j = 0; j<16; j++) {                                 //pętla 16 operacji z kluczem

            BitSet rTo48Bits = new BitSet(48);
            BitSet finalChanged = new BitSet();

            for (int i = 47; i >= 0; i--) {                         //permutacja E bloku Rj
                if (r.get(32 - E[47 - i]))
                    rTo48Bits.set(i);
                else
                    rTo48Bits.clear(i);
            }

            pw.println();
            desKey.logPrintNumbers(48,pw);
            pw.println("Permutacja E bloku R"+j);
            desKey.logPrintBitSet(rTo48Bits,pw,48);

            rTo48Bits.xor(desKey.getK()[15-j]);                        //xorowanie bloku Rj z kluczem Kj
            pw.println("Klucz K"+(j+1));
            desKey.logPrintBitSet(desKey.getK()[15-j],pw,48);
            pw.println("Wynik XOR");
            desKey.logPrintBitSet(rTo48Bits,pw,48);
            pw.println();
            desKey.logPrintNumbers(32,pw);
            BitSet changed = changeBitsBySTable(rTo48Bits);         //zamiana bitow wg tabel S1-S8
            pw.println("Zmiana po tabelach S:");
            desKey.logPrintBitSet(changed,pw,32);


            for(int i=31; i>=0; i--) {
                if(changed.get(32-P[31-i]))
                    finalChanged.set(i);
                else
                    finalChanged.clear(i);
            }

            pw.println("Permutacja P");
            desKey.logPrintBitSet(finalChanged,pw,32);


            pw.println();
            desKey.logPrintNumbers(32,pw);
            finalChanged.xor(l);
            l = r;
            r = finalChanged;
            pw.println("Nowe L:");
            desKey.logPrintBitSet(l,pw,32);
            pw.println("Nowe R:");
            desKey.logPrintBitSet(r,pw,32);

        }

        tmp = merge(r,l);

        desKey.logPrintNumbers(64,pw);
        pw.println("Finalne scalenie:");
        desKey.logPrintBitSet(tmp,pw,64);

        for(int i=63; i>=0; i--) {
            if(tmp.get(64-invertedIP[63-i]))
                block.set(i);
            else
                block.clear(i);
        }

        pw.println("Permutacja Inverse IP:");
        desKey.logPrintBitSet(block,pw,64);

        pw.close();
        return convert(block);
    }

    private BitSet changeBitsBySTable(BitSet bitSet) {
        int row, col;
        BitSet sNumber;
        BitSet block = new BitSet(32);
        for(int i=0; i<8; i++) {
            row = getRow(bitSet.get(42-(i*6),48-(i*6)));
            col = getColumn(bitSet.get(42-(i*6),48-(i*6)));
            sNumber = convert(S[i][row][col]);
            for(int j=3; j>=0; j--) {
                if(sNumber.get(j)) {
                    block.set(28-(i*4)+j);
                }
                else {
                    sNumber.clear(28-(i*4)+j);
                }
            }
        }
        return block;
    }

    private int getRow(BitSet bitSet){
        BitSet ret = new BitSet(2);
        if(bitSet.get(0)){
            ret.set(0);
        }
        else {
            ret.clear(0);
        }
        if(bitSet.get(5)){
            ret.set(1);
        }
        else {
            ret.clear(1);
        }
        return (int) convert(ret);
    }

    private int getColumn(BitSet bitSet){
        BitSet ret = bitSet.get(1,5);
        return (int) convert(ret);
    }


    public static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public static BitSet convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0) {
            if (value % 2 != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    private BitSet merge(BitSet c, BitSet d) {              //scalenie 2 BitSetow
        BitSet tmp = new BitSet(64);
        for(int i=0; i<32; i++){
            if(d.get(i))
                tmp.set(i);
            else
                tmp.clear(i);
            if(c.get(i))
                tmp.set(i+32);
            else
                tmp.clear(i+32);
        }
        return tmp;
    }

}