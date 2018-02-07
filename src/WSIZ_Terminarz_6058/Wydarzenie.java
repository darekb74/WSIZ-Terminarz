/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSIZ_Terminarz_6058;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Darek B.
 */
abstract class Wydarzenie {

    protected String nazwa;
    protected int typ;
    protected double kwota;

    Wydarzenie() {
        nazwa = "";
        typ = 0;
        kwota = 0;
    }

    Wydarzenie(String nazwa, int typ, double kwota) {
        this.nazwa = nazwa;
        this.typ = typ;
        this.kwota = kwota;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getTyp() {
        return typ;
    }

    public double getKwota() {
        return kwota;
    }

    public void print() {
        switch (typ) {
            case 1:
                System.out.println("Wydarzenie: Urodziny  Jubilat: " + nazwa);
                break;
            case 2:
                System.out.println("Wydarzenie: Imieniny  Solenizant: " + nazwa);
                break;
            case 3:
                System.out.println("Wydarzenie: Spotkanie  Osoba: " + nazwa);
                break;
            default:
                System.out.println("Wydarzenie: BRAK  Nazwa: " + nazwa);
                break;
        }
    }

    protected String dblLoc(double dbl) {
        return String.format(Locale.GERMAN, "%.2f", dbl);
    }
}

class Urodziny extends Wydarzenie {

    Urodziny() {
        System.out.print("Imię jubilata:");
        Scanner terminalInput = new Scanner(System.in);
        this.nazwa = terminalInput.nextLine();
        this.typ = 1;
        this.kwota = 0;
    }

    Urodziny(String nazwa, int typ, double kwota) {
        this.nazwa = nazwa;
        this.typ = typ;
        this.kwota = kwota;
    }
}

class Imieniny extends Wydarzenie {

    Imieniny() {
        System.out.print("Imię solenizanta:");
        Scanner terminalInput = new Scanner(System.in);
        this.nazwa = terminalInput.nextLine();
        this.typ = 2;
        this.kwota = 0;
    }

    Imieniny(String nazwa, int typ, double kwota) {
        super(nazwa, typ, kwota);
    }
}

class Spotkanie extends Wydarzenie {

    Spotkanie() {
        System.out.print("Imię osoby, z którą się spotykasz:");
        Scanner terminalInput = new Scanner(System.in);
        this.nazwa = terminalInput.nextLine();
        this.typ = 3;
        this.kwota = 0;
    }

    Spotkanie(String nazwa, int typ, double kwota) {
        super(nazwa, typ, kwota);
    }
}

class Oplata extends Wydarzenie {

    Oplata() {
        System.out.print("Opis opłaty:");
        Scanner terminalInput = new Scanner(System.in);
        this.nazwa = terminalInput.nextLine();
        this.typ = 4;
        try {
            System.out.print("Podaj kwotę (delimiter = \",\"):");
            this.kwota = terminalInput.nextDouble();//Double.parseDouble(terminalInput.toString());
        } catch (InputMismatchException e) {
            System.out.println("BŁĄD: Wprowadzona wartość nie jest kwotą.");
            this.kwota = 0;
        }
    }

    Oplata(String nazwa, int typ, double kwota) {
        super(nazwa, typ, kwota);
    }

    @Override
    public void print() {
        System.out.println("Wydarzenie: Opłata  Cel: " + nazwa + "  Kwota: " + dblLoc(kwota));
    }
}

class Inne extends Wydarzenie {

    Inne() {
        System.out.print("Podaj opis wydarzenia:");
        Scanner terminalInput = new Scanner(System.in);
        this.nazwa = terminalInput.nextLine();
        this.typ = 5;
        this.kwota = 0;
    }

    Inne(String nazwa, int typ, double kwota) {
        super(nazwa, typ, kwota);
    }
}

class Data {

    protected long rok;
    protected int miesiac;
    protected int dzien;
    // HashSet<Wydarzenie> wydarzenia = new HashSet<Wydarzenie>();
    List<Wydarzenie> wydarzenia = new ArrayList<Wydarzenie>();

    public void usunWydarzenie(int id) {
        wydarzenia.remove(id);
    }

    public void dodajWydarzenie(int typ) {
        switch (typ) {
            case 1:
                wydarzenia.add(new Urodziny());
                break;
            case 2:
                wydarzenia.add(new Imieniny());
                break;
            case 3:
                wydarzenia.add(new Spotkanie());
                break;
            case 4:
                wydarzenia.add(new Oplata());
                break;
            case 5:
                wydarzenia.add(new Inne());
                break;
        }
    }

    public void wyswietlWydarzenia() {
        int i = 0;
        for (Wydarzenie obj : wydarzenia) {
            switch (obj.typ) {
                case 1:
                    System.out.println("[" + i++ + "] Urodziny: " + obj.getNazwa());
                    break;
                case 2:
                    System.out.println("[" + i++ + "] Imieniny: " + obj.getNazwa());
                    break;
                case 3:
                    System.out.println("[" + i++ + "] Spotkanie: " + obj.getNazwa());
                    break;
                case 4:
                    System.out.println("[" + i++ + "] Opłata: " + obj.getNazwa() + "  Kwota: " + obj.dblLoc(obj.getKwota()));
                    break;
                case 5:
                    System.out.println("[" + i++ + "] Inne: " + obj.getNazwa());
                    break;
            }
        }
        if (i == 0) {
            System.out.println("Brak wydażeń.");
        }
    }

    @Override
    public int hashCode() {
        String h = String.format("%04d", this.rok) + String.format("%02d", this.miesiac) + String.format("%02d", this.dzien);
        int hash = Integer.parseInt(h);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (this.hashCode() == obj.hashCode()) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Data other = (Data) obj;
        if (this.rok != other.rok) {
            return false;
        }
        if (this.miesiac != other.miesiac) {
            return false;
        }
        if (this.dzien != other.dzien) {
            return false;
        }
        return true;
    }

    Data() {
        try {
            String tStr;
            System.out.print("Podaj datę w formacie dd-mm-RRRR:");
            Scanner terminalInput = new Scanner(System.in);
            tStr = terminalInput.nextLine();
            if (tStr.length()!=10 || // regex - uwzględnia ilość dni w miesiącu oraz lata przestępne
                !tStr.matches("^^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
                ) throw new Exception();
            String str[] = tStr.split("-");
            this.dzien = Integer.parseInt(str[0]);
            this.miesiac = Integer.parseInt(str[1]);
            this.rok = Integer.parseInt(str[2]);
            this.rok = rok > 0 ? rok : 1;
            this.miesiac = miesiac > 12 ? 12 : miesiac < 1 ? 1 : miesiac;
        } catch (Exception e) {
            LocalDate date = LocalDate.now();
            rok = date.getYear();
            miesiac = date.getMonthValue();
            dzien = date.getDayOfMonth();
            System.out.println("Błąd wprowadzania daty. Ustawiam datę na " + this.toString(true) + ".");
        }
        switch (miesiac) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                this.dzien = dzien < 1 ? 1 : dzien > 31 ? 31 : dzien;
                break;
            case 2:
                this.dzien = dzien < 1 ? 1 : przestepny() ? dzien > 29 ? 29 : dzien : dzien > 28 ? 28 : dzien;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
            default:
                this.dzien = dzien < 1 ? 1 : dzien > 30 ? 30 : dzien;
                break;
        }
        System.out.println("Wprowadzona data to:" + dzien +"-"+miesiac+"-" +rok + ".");
    }

    /* Wprowadzanie odzielnych danych zastąpiłem wprowadzaniem całej daty
    Data() {
        try {
            System.out.print("Podaj rok w formacie RRRR:");
            Scanner terminalInput = new Scanner(System.in);
            rok = Integer.parseInt(terminalInput.nextLine());
            this.rok = rok > 0 ? rok : 1;
            System.out.print("Podaj miesiąc (1-12):");
            terminalInput = new Scanner(System.in);
            miesiac = Integer.parseInt(terminalInput.nextLine());
            this.miesiac = miesiac > 12 ? 12 : miesiac < 1 ? 1 : miesiac;
            System.out.print("Podaj dzień (1-31):");
            terminalInput = new Scanner(System.in);
            dzien = Integer.parseInt(terminalInput.nextLine());
        } catch (NumberFormatException e) {
            LocalDate date = LocalDate.now();
            rok = date.getYear();
            miesiac = date.getMonthValue();
            dzien = date.getDayOfMonth();
            System.out.println("Błąd wprowadzania daty. Ustawiam datę na " + this.toString(true) + ".");
        }
        switch (miesiac) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                this.dzien = dzien < 1 ? 1 : dzien > 31 ? 31 : dzien;
                break;
            case 2:
                this.dzien = dzien < 1 ? 1 : przestepny() ? dzien > 29 ? 29 : dzien : dzien > 28 ? 28 : dzien;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
            default:
                this.dzien = dzien < 1 ? 1 : dzien > 30 ? 30 : dzien;
                break;
        }
    }
*/
    Data(long rok, int miesiac, int dzien) {
        this.rok = rok > 0 ? rok : 1;
        this.miesiac = miesiac > 12 ? 12 : miesiac < 1 ? 1 : miesiac;
        switch (miesiac) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                this.dzien = dzien < 1 ? 1 : dzien > 31 ? 31 : dzien;
                break;
            case 2:
                this.dzien = dzien < 1 ? 1 : przestepny() ? dzien > 29 ? 29 : dzien : dzien > 28 ? 28 : dzien;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
            default:
                this.dzien = dzien < 1 ? 1 : dzien > 30 ? 30 : dzien;
                break;
        }

    }

    @Override
    public String toString() {
        return "" + String.format("%02d", this.dzien) + "." + String.format("%02d", this.miesiac) + "." + String.format("%04d", this.rok);
    }

    public String toString(boolean formated) {
        if (!formated) {
            return "" + String.format("%04d", this.rok) + "-" + String.format("%02d", this.miesiac) + "-" + String.format("%02d", this.dzien);
        }
        return "" + this.dzien + " " + new DateFormatSymbols().getMonths()[this.miesiac - 1] + " " + String.format("%04d", this.rok);
    }

    public String getDayOfWeek(String date, boolean formatShort) {
        Locale locale = new Locale("pl", "PL");
        LocalDate data = LocalDate.parse(date);
        DayOfWeek dow = data.getDayOfWeek();
        return dow.getDisplayName(formatShort ? TextStyle.SHORT : TextStyle.FULL, locale);
    }

    public String getNameOfMonth(String date, boolean formatShort) {
        Locale locale = new Locale("pl", "PL");
        LocalDate data = LocalDate.parse(date);
        Month nom = data.getMonth();
        return nom.getDisplayName(formatShort ? TextStyle.SHORT : TextStyle.FULL_STANDALONE, locale);
    }

    public int getDayOfWeekValue(String date) {
        LocalDate data = LocalDate.parse(date);
        DayOfWeek dow = data.getDayOfWeek();
        return dow.getValue();
    }

    public int getLengthOfMonth(String date) {
        LocalDate data = LocalDate.parse(date);
        return data.lengthOfMonth();
    }

    public long getRok() {
        return rok;
    }

    public int getMiesiac() {
        return miesiac;
    }

    public int getDzien() {
        return dzien;
    }

    private boolean przestepny() {
        return this.rok % 4 == 0;
    }

    public void Print() {
        System.out.println("Data: " + this.toString());
    }
}
