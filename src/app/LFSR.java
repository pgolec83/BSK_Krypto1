/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.BitSet;

public class LFSR {
    
    private BitSet seed;
    private BitSet polynomial;

    public LFSR(BitSet seed, BitSet polynomial) {
        this.seed = seed;
        this.polynomial = polynomial;
    }

    public BitSet getSeed() {
        return seed;
    }

    public void setSeed(BitSet seed) {
        this.seed = seed;
    }

    public BitSet getPolynomial() {
        return polynomial;
    }

    public void setPolynomial(BitSet polynomial) {
        this.polynomial = polynomial;
    }
    /**
     * Funkcja która na podstawie wielomiana i ziarna przechodzi 8 razy kolejne kroki algorytmu i zwraca bajt do kodowania
     * @return kolejny bajt po przejściu algorytmu
     */
    public byte getNextByte() {
        BitSet ret = new BitSet(8);
        BitSet tmp;
        for(int i=0; i<8; i++){                     //8 kroków - wielkość 1 bajtu
            tmp = (BitSet) seed.clone();            //BitSet pomocniczy kopiuje wartość ziarna
            tmp.and(polynomial);                    //tmp= logiczny AND ziarna z wielomianem w celu wyłuskania pozycji do XORowania 
            seed = shiftRight(seed);                //przesunięcie ziarna o 1 bit w prawo
            if(tmp.cardinality()%2 == 0) {          //jeżeli w tmp liczba 1 jest parzysta -> wynik XORowania jest równy 0
                seed.clear(polynomial.length()-1);  //wyzerowanie skrajnie lewego bitu
            }
            else {                                  //jeżeli w tmp liczba 1 jest nieparzysta -> wynik XORowania jest równy 1
                seed.set(polynomial.length()-1);    //ustawienie 1 na skrajnie lewym bicie
            }
            if(seed.get(0))                         //jeżeli skrajnie prawy bit jest 1 
                ret.set(i);                         //ustaw 1 na aktualnej pozycji bajtu
            else                                    //jeżeli skrajnie prawy bit jest 0
                ret.clear(i);                       //usta 0 na aktualnej pozycji bajtu
        }
        return convert(ret);                        //przekonwertuj BitSet do bajtu i zwróć 
    }

    private BitSet shiftRight(BitSet bitSet) {              //przesunięcie w prawo o 1 bit
        BitSet shifted = bitSet.get(1, bitSet.length());    //utworzenie nowego BitSetu na podstawie starego bez najmniej znaczącego bitu
        return shifted;
    }

    private byte convert(BitSet bitSet) {               //konwersja BitSetu do bajtu
        byte value = 0;                                 //bajt wypełniony samymi zerami
        for (int i = 0; i < bitSet.length(); ++i) {     //sprawdzanie wartości całego BitSetu
            value += bitSet.get(i) ? (1 << i) : 0;      //jeżeli bit jest 1 to dodaj jego wartość przez przesunięcie 1 o odpowiednią liczbę pozycji
        }
        return value;
    }
}
