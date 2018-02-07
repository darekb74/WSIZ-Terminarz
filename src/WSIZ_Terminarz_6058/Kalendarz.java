/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSIZ_Terminarz_6058;

import java.nio.CharBuffer;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Darek B.
 */
public class Kalendarz {

    protected long rok; // miały być 4 typy zmiennych - normalnie użyłbym int
    protected int miesiac;
    protected int dzien;
    protected String data;
    protected Data cDate;
    protected HashSet<Data> hsDaty = new HashSet<Data>();

    Kalendarz() {
        LocalDate date = LocalDate.now();
        rok = date.getYear();
        miesiac = date.getMonthValue();
        dzien = date.getDayOfMonth();
        data = date.toString();
        cDate = new Data(rok, miesiac, dzien);
        hsDaty.clear();
    }

    protected void ustawDate(Data dat) {
        rok = dat.getRok();
        miesiac = dat.getMiesiac();
        dzien = dat.getDzien();
        data = dat.toString(false);
        cDate = findIfPresent(dat);
        if (cDate == null) {
            cDate = dat;
        }
    }

    public void printMenu() {
        printKalendarz();
        System.out.println(
                "#################################\n"
                + "# Wybierz opcję:                #\n"
                + "#################################\n"
                + "# 1) Wybierz datę               #\n"
                + "# 2) Wyświetl wydarzenia        #\n"
                + "# 3) Dodaj wydarzenie           #\n"
                + "# 4) Usuń wydarzenie            #\n"
                + "# 5) Zakończ program            #\n"
                + "#################################\n"
        );
        int opcja = 0;
        Scanner terminalInput = new Scanner(System.in);
        try {
            opcja = terminalInput.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("BŁĄD: Wprowadzona wartość nie jest liczbą.");
            this.printMenu();
        }

        switch (opcja) {
            case 1:
                System.out.println("Wybór nowej daty:");
                Data dat = new Data();
                cDate = findIfPresent(dat);
                this.ustawDate(cDate != null ? cDate : dat);
                this.printMenu();
                break;
            case 2:
                System.out.println("Lista wydarzeń w dniu " + cDate.toString(true) + ":");
                cDate.wyswietlWydarzenia();
                this.printMenu();
                break;
            case 3:
                this.printMenuW();
                break;
            case 4:
                if (!cDate.wydarzenia.isEmpty()) {
                    System.out.println("Usuwanie wydarzeń");
                    System.out.println("Lista wydarzeń w dniu " + cDate.toString(true) + ":");
                    cDate.wyswietlWydarzenia();
                    opcja = 0;
                    terminalInput = new Scanner(System.in);
                    System.out.println("Podaj numer wydarzenia, które chcesz usunąć:");
                    try {
                        opcja = terminalInput.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("BŁĄD: Wprowadzona wartość nie jest liczbą.");
                        this.printMenu();
                    }
                    hsDaty.remove(cDate);
                    cDate.usunWydarzenie(opcja);
                    if (!cDate.wydarzenia.isEmpty()) {
                        hsDaty.add(cDate);
                    }
                    this.printMenu();
                } else {
                    System.out.println("W tym dniu nie ma wydarzeń, które możnaby usunąć.");
                    this.printMenu();
                }
                break;
            case 5:
                System.out.println("Koniec programu.");
                break;
        }

    }

    private void printMenuW() {
        System.out.println(
                "#################################\n"
                + "# Wybierz typ wydarzenia:       #\n"
                + "#################################\n"
                + "# 1) Urodziny                   #\n"
                + "# 2) Imieniny                   #\n"
                + "# 3) Spotkanie                  #\n"
                + "# 4) Opłata                     #\n"
                + "# 5) Inne wydarzenie            #\n"
                + "# 6) Wyświetl wydarzenia        #\n"
                + "# 7) Powrót                     #\n"
                + "#################################\n"
        );
        int opcja = 0;
        Scanner terminalInput = new Scanner(System.in);
        try {
            opcja = terminalInput.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("BŁĄD: Wprowadzona wartość nie jest liczbą.");
            this.printMenu();
        }

        switch (opcja) {
            case 1:
                System.out.println("Dodaj wydarzenia \"Urodziny\":");
                cDate.dodajWydarzenie(opcja);
                hsDaty.remove(cDate);
                hsDaty.add(cDate);
                this.printMenuW();
                break;
            case 2:
                System.out.println("Dodaj wydarzenia \"Imieniny\":");
                cDate.dodajWydarzenie(opcja);
                hsDaty.remove(cDate);
                hsDaty.add(cDate);
                this.printMenuW();
                break;
            case 3:
                System.out.println("Dodaj wydarzenia \"Spotkanie\":");
                cDate.dodajWydarzenie(opcja);
                hsDaty.remove(cDate);
                hsDaty.add(cDate);
                this.printMenuW();
                break;
            case 4:
                System.out.println("Dodaj wydarzenia \"Opłata\":");
                cDate.dodajWydarzenie(opcja);
                hsDaty.remove(cDate);
                hsDaty.add(cDate);
                this.printMenuW();
                break;
            case 5:
                System.out.println("Dodaj wydarzenia \"Inne\":");
                cDate.dodajWydarzenie(opcja);
                hsDaty.remove(cDate);
                hsDaty.add(cDate);
                this.printMenuW();
                break;
            case 6:
                System.out.println("Lista wydarzeń w dniu " + cDate.toString(true) + ":");
                cDate.wyswietlWydarzenia();
                this.printMenuW();
                break;
            case 7:
                this.printMenu();
                break;
            default:
                this.printMenuW();
                break;
        }
    }

    private String genDatStr(long rok, int miesiac, int dzien) {
        return String.format("%04d", rok) + "-" + String.format("%02d", miesiac) + "-" + String.format("%02d", dzien);
    }

    private void printKalendarz() {
        Data dat = cDate, tDat;
        System.out.println("Obecna data: " + dat.toString(true));
        String out[] = {"", "", "", "", "", "", "", ""};
        String output = "pon wto śro czw pią sob nie";
        String topBar = "|";
        out[0] = output + " | " + output + " | " + output + " | ";
        boolean st;
        int pi = 0, ma = 0, row = 1;
        for (int m = -1; m <= 1; m++) {
            if (m < 0) {
                if (dat.getMiesiac() == 1) {
                    pi = dat.getDayOfWeekValue(genDatStr(dat.getRok() - 1, 12, 1));
                    ma = dat.getLengthOfMonth(genDatStr(dat.getRok() - 1, 12, 1));
                    tDat = new Data(dat.getRok() - 1, 12, 1);
                } else {
                    pi = dat.getDayOfWeekValue(genDatStr(dat.getRok(), dat.getMiesiac() + m, 1));
                    ma = dat.getLengthOfMonth(genDatStr(dat.getRok(), dat.getMiesiac() + m, 1));
                    tDat = new Data(dat.getRok(), dat.getMiesiac() + m, 1);
                }
            } else if (m > 0 && dat.getMiesiac() == 12) {
                pi = dat.getDayOfWeekValue(genDatStr(dat.getRok() + 1, 1, 1));
                ma = dat.getLengthOfMonth(genDatStr(dat.getRok() + 1, 1, 1));
                tDat = new Data(dat.getRok() + 1, 1, 1);
            } else {
                pi = dat.getDayOfWeekValue(genDatStr(dat.getRok(), dat.getMiesiac() + m, 1));
                ma = dat.getLengthOfMonth(genDatStr(dat.getRok(), dat.getMiesiac() + m, 1));
                tDat = new Data(dat.getRok(), dat.getMiesiac() + m, 1);
            }
            String tmp = tDat.getNameOfMonth(tDat.toString(false), false) + " " + String.format("%04d", tDat.getRok());
            tmp = soc(' ',(29 - tmp.length()) / 2) + tmp + soc(' ',(29 - tmp.length()) / 2);
            topBar += tmp + (tmp.length() < 29 ? " |" : "|");
            row = 1;
            output = "";
            st = false;

            for (int i = 1; i < ma;) //i++)
            {
                for (int j = 1; j <= 7; j++) {
                    tDat = new Data(tDat.rok, tDat.miesiac, i);
                    if (findIfPresent(tDat) != null) {
                        tDat = findIfPresent(tDat);
                    }

                    if (!st || i > ma) // jeszce nie zczeliśmy wypełniać
                    {
                        if (j < pi || i > ma) {
                            output += "    ";
                        } else {
                            output += i < 10 ? (tDat.wydarzenia.isEmpty() ? " " : "#") + i + (dzien == i ? (m == 0 ? "<" : " ") + " " : "  ") : (tDat.wydarzenia.isEmpty() ? " " : "#") + i + (dzien == i ? (m == 0 ? "<" : " ") : " ");
                            st = true;
                            i++;
                        }
                    } else {
                        output += i < 10 ? (tDat.wydarzenia.isEmpty() ? " " : "#") + i + (dzien == i ? (m == 0 ? "<" : " ") + " " : "  ") : (tDat.wydarzenia.isEmpty() ? " " : "#") + i + (dzien == i ? (m == 0 ? "<" : " ") : " ");
                        i++;
                    }
                }

                out[row] += output + "| ";
                row++;
                output = "";
            }
            out[row] += (output.equalsIgnoreCase("") ? "                            | " : "");
        }
        System.out.println(soc('-',91));
        System.out.println(topBar);
        // System.out.println(soc('-',91));
        for (int i = 0; i <= 6; i++) {
            System.out.println("| " + out[i]);
        }
        System.out.println(soc('-',91));

    }

    private String soc(char ch, int lp) {
        return CharBuffer.allocate(lp).toString().replace('\0', ch);
    }

    private Data findIfPresent(Data source) {
        if (hsDaty.contains(source)) {
            for (Data obj : hsDaty) {
                if (obj.equals(source)) {
                    return obj;
                }
            }
        }
        return null;
    }

}
