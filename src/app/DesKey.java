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

public class DesKey {
    private BitSet  key;                //klucz poczatkowy
    private BitSet[] k;                 //tablica kluczy K1-K16
    private int[] pc1;                  //permutacja PC1
    private int[] pc2;                  //permutacja PC2
    private int[] shifts;               //tablica przesuniec blokow Cn i Dn

    public DesKey(BitSet key) {
        this.key = key;
        this.k = new BitSet[16];
        pc1 = new int[] {   57,49,41,33,25,17,9,
                            1,58,50,42,34,26,18,
                            10,2,59,51,43,35,27,
                            19,11,3,60,52,44,36,
                            63,55,47,39,31,23,15,
                            7,62,54,46,38,30,22,
                            14,6,61,53,45,37,29,
                            21,13,5,28,20,12,4 };
        pc2 = new int[] {   14,17,11,24,1,5,
                            3,28,15,6,21,10,
                            23,19,12,4,26,8,
                            16,7,27,20,13,2,
                            41,52,31,37,47,55,
                            30,40,51,45,33,48,
                            44,49,39,56,34,53,
                            46,42,50,36,29,32 };
        shifts = new int[] { 1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
        initialization();
    }

    private void initialization() {
        BitSet tmp = new BitSet(56);                            //BitSet na ktorym beda wykonywane operacja

        FileWriter fileWriter = null;                                 //do zapisu kolejnych operacji algorytmu
        try {
            fileWriter = new FileWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fileWriter);
        logPrintNumbers(64,pw);                             //wypisanie numerow 1-64 dla poprawy widoczonsci logu
        pw.println("Początkowy klucz:");
        logPrintBitSet(key,pw,64);                              //wypisanie poczatkowego klucza

        for(int i=55; i>=0; i--) {                                  //premutacja pc1 klucza poczatkowego, zaczynamy od 55 a nie od 0 bo w dokumentacji numerowanie jest odwrotne niz w BitSet-cie
            if(key.get(64-pc1[55-i]))                               //np dla i=0 pc1[55]->4, 64-4->60 czyli 61 bit liczac od lewej do prawej a 4 bit liczac od lewej do prawej ktorego wartosc ustalana jest na najmniej znaczacym bicie
                tmp.set(i);
            else
                tmp.clear(i);
        }

        pw.println("Klucz po permutacji pc1:");
        logPrintBitSet(tmp,pw,56);                             //zapis klucza po permutacji

        BitSet d = tmp.get(0,27);                                   //blok d jest blokiem mniej znaczacych bitow
        BitSet c = tmp.get(28,55);                                  //blok c jest blokiem bardziej znaczacych bitow

        pw.println("c0:");                                          //wypisanie blokow c i d
        logPrintBitSet(c,pw,28);
        pw.println("d0");
        logPrintBitSet(d,pw,28);

        for(int i=0; i<16; i++){                                    //tworzenie kluczy K1-K16
            c = shiftLeft(c,shifts[i]);                             //przesuniecie blokow c i d o odpowiednia liczbe bitow
            d = shiftLeft(d,shifts[i]);

            pw.println("c"+(i+1)+":");                              //wypisanie przesunietych blokow c i d
            logPrintBitSet(c,pw,28);
            pw.println("d"+(i+1)+":");
            logPrintBitSet(d,pw,28);

            tmp = merge(c,d);                                       //scalenie blokow c i d

            pw.println();
            logPrintNumbers(56,pw);                         //wypisanie numerow 1-56 do logu dla poprawy widocznosci
            pw.println("Złączenie c i d:");                         //wypisanie scalonych blokow c i d
            logPrintBitSet(tmp,pw,56);

            k[i] = new BitSet(48);
            for(int j=47; j>=0; j--) {                              //permutacja pc2 po ktorej powstaja 48 bitowe klucze K1-K16
                if(tmp.get(56-pc2[47-j]))
                    k[i].set(j);
                else
                    k[i].clear(j);
            }

            pw.println("klucz k"+(i+1)+" po permutacji pc2");       //wypisanie klucza Kn
            logPrintBitSet(k[i],pw,48);
        }

        pw.close();

    }

    private BitSet shiftLeft(BitSet bitSet, int bits) {              //przesuniecie BitSetu o zadana liczbe bitow
        BitSet tmp = new BitSet(28);
        for(int i=0; i<28-bits; i++) {
            if(bitSet.get(i))
                tmp.set(i+bits);
            else
                tmp.clear(i+bits);
        }
        for(int i=0; i<bits; i++){
            if(bitSet.get(27-i))
                tmp.set(bits-i-1);
            else
                tmp.clear(bits-i-1);
        }
        return tmp;
    }

    private BitSet merge(BitSet c, BitSet d) {              //scalenie 2 BitSetow
        BitSet tmp = new BitSet(56);
        for(int i=0; i<28; i++){
            if(d.get(i))
                tmp.set(i);
            else
                tmp.clear(i);
            if(c.get(i))
                tmp.set(i+28);
            else
                tmp.clear(i+28);
        }
        return tmp;
    }

    private void logPrintNumbers(int numbers, PrintWriter pw) {         //wypisywanie numerow od 1 do numbers do logu
        for(int i=1; i<=numbers; i++) {
            if(i<10) {
                pw.print("| " + i);
            }
            else {
                pw.print("|" + i);
            }
        }
        pw.println();
    }

    private void logPrintBitSet(BitSet bitSet, PrintWriter pw, int bits) {  //wypisywanie ciagow bitow do logu o zadanej wielkosci
        for(int i=bits-1; i>=0; i--) {
            if(bitSet.get(i)) {
                pw.print("| 1");
            }
            else {
                pw.print("| 0");
            }
        }
        pw.println();
    }
}