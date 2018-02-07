/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSIZ_Terminarz_6058;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Darek B.
 */
public class IO {

    protected File file;
    //public String filename;

    IO() {
    }

    protected void fOpen(String filename) {
        if (!filename.equals("")) {
            this.file = new File(filename);
        }
    }

    protected String readLine(Scanner odczyt) throws FileNotFoundException {
        String tmp = "";
        tmp = odczyt.nextLine();
        return tmp;
    }

    protected void writeLine(String s, boolean append) throws IOException {
        //PrintWriter zapis = new PrintWriter(file);
        PrintWriter zapis = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        zapis.println(s);
        zapis.close();
    }

    protected int readInt(Scanner odczyt) throws FileNotFoundException {
        int tmp = 0;
        tmp = Integer.parseInt(odczyt.nextLine());
        return tmp;
    }

    protected void writeInt(int i, boolean append) throws IOException {
        //PrintWriter zapis = new PrintWriter(file);
        PrintWriter zapis = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        zapis.println(i);
        zapis.close();
    }

    protected double readDouble(Scanner odczyt) throws FileNotFoundException {
        double tmp = 0;
        tmp = odczyt.nextDouble();
        return tmp;
    }

    protected void writeDouble(double d, boolean append) throws IOException {
        PrintWriter zapis = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        zapis.println(d);
        zapis.close();
    }

    public Kalendarz wczytajKalendarz(String filename) {
        Kalendarz tKal = new Kalendarz();

        try {
            int i = 0, j = 0;
            int r = 1900, m = 1, d = 1;
            fOpen(filename);
            Scanner o = new Scanner(file);
            o.useLocale(Locale.US);
            // pobierz ilość dat
            try {
                i = readInt(o);
            } catch (FileNotFoundException e) {
                System.out.println("Błąd: Nieprawidłowe dane.");
            }
            for (; i > 0; i--) {
                // pobierz datę
                try {
                    r = readInt(o); // rok
                    m = readInt(o); // miesiac
                    d = readInt(o); // dzien
                    j = readInt(o); // ilosc wydarzen
                } catch (FileNotFoundException e) {
                    System.out.println("Błąd: Nieprawidłowe dane.");
                }
                Data tDat = new Data(r, m, d);
                for (; j > 0; j--) { // wczytywanie wydarzen
                    try {
                        Wydarzenie tW;
                        int t = readInt(o); // typ
                        String n = readLine(o);
                        double db = Double.parseDouble(readLine(o));
                        switch (t) {
                            case 1: // urodziny
                                tW = new Urodziny(n, t, db);
                                tDat.wydarzenia.add(tW);
                                break;
                            case 2: // imieniny
                                tW = new Imieniny(n, t, db);
                                tDat.wydarzenia.add(tW);
                                break;
                            case 3: // Spotkanie
                                tW = new Spotkanie(n, t, db);
                                tDat.wydarzenia.add(tW);
                                break;
                            case 4: // Oplata
                                tW = new Oplata(n, t, db);
                                tDat.wydarzenia.add(tW);
                                break;
                            case 5: // inne
                                tW = new Inne(n, t, db);
                                tDat.wydarzenia.add(tW);
                                break;
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Błąd: Nieprawidłowe dane.");
                    }
                }
                tKal.hsDaty.add(tDat);
            }

        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Błąd: Nie znaleziono pliku.");
        }
        return tKal;

    }

    public void zapiszKalendarz(Kalendarz tKal, String filename) {
        fOpen(filename);
        // Zapisz ilość dat
        try {
            writeInt(tKal.hsDaty.size(), false);
        } catch (IOException e) {
            System.out.println("Błąd zapisu kalendarza.");
        }
        for (Data obj : tKal.hsDaty) {
            // zapisz datę
            try {
                writeInt((int)obj.rok, true); // rok
                writeInt(obj.miesiac, true); // miesiac
                writeInt(obj.dzien, true); // dzien
                writeInt(obj.wydarzenia.size(), true); // ilosc wydarzen
            } catch (IOException e) {
                System.out.println("Błąd zapisu kalendarza.");
            }
            for (Wydarzenie wObj : obj.wydarzenia) { // wczytywanie wydarzen
                try {
                    writeInt(wObj.typ, true); // typ
                    writeLine(wObj.nazwa, true);
                    writeDouble(wObj.kwota, true);
                } catch (IOException e) {
                    System.out.println("Błąd zapisu kalendarza.");
                }
            }
        }
    }

}
