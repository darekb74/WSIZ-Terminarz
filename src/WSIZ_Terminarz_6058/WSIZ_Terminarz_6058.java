/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSIZ_Terminarz_6058;

import java.time.LocalDate;
import java.util.Locale;

/**
 *
 * @author Darek B.
 */
public class WSIZ_Terminarz_6058 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Locale locale = new Locale("pl", "PL");
        Locale.setDefault(locale);
        //Kalendarz kal = new Kalendarz();
        IO io = new IO();
        // spróbuj wgrac kalendarz
        Kalendarz kal = io.wczytajKalendarz("./dane.txt");
        LocalDate date = LocalDate.now();
        kal.ustawDate(new Data(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        kal.printMenu();
        io.zapiszKalendarz(kal, "./dane.txt");
    }
}
