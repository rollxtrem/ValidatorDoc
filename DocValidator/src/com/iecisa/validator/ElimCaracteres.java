package com.iecisa.validator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElimCaracteres {

    public static ArrayList<Patrones> listaCaracteresInvalidos = new ArrayList<Patrones>();

    
    public class Patrones {

        private String patron;
        private String replace;

        public void setPatron(String patron) {
            this.patron = patron;
        }

        public String getPatron() {
            return patron;
        }

        public void setRemplace(String replace) {
            this.replace = replace;
        }

        public String getRemplace() {
            return replace;
        }

        public Patrones(String patron, String replace) {
            this.patron = patron;
            this.replace = replace;
        }
    }

    private ElimCaracteres() {

        listaCaracteresInvalidos.add(new Patrones("A", "À"));
        listaCaracteresInvalidos.add(new Patrones("A", "Á"));
        listaCaracteresInvalidos.add(new Patrones("A", "Â"));
        listaCaracteresInvalidos.add(new Patrones("A", "Ã"));
        listaCaracteresInvalidos.add(new Patrones("A", "Ä"));
        listaCaracteresInvalidos.add(new Patrones("A", "Å"));
        listaCaracteresInvalidos.add(new Patrones("E", "È"));
        listaCaracteresInvalidos.add(new Patrones("E", "É"));
        listaCaracteresInvalidos.add(new Patrones("E", "Ê"));
        listaCaracteresInvalidos.add(new Patrones("E", "Ë"));
        listaCaracteresInvalidos.add(new Patrones("I", "Ì"));
        listaCaracteresInvalidos.add(new Patrones("I", "Í"));
        listaCaracteresInvalidos.add(new Patrones("I", "Î"));
        listaCaracteresInvalidos.add(new Patrones("I", "Ï"));
        listaCaracteresInvalidos.add(new Patrones("O", "Ò"));
        listaCaracteresInvalidos.add(new Patrones("O", "Ó"));
        listaCaracteresInvalidos.add(new Patrones("O", "Ô"));
        listaCaracteresInvalidos.add(new Patrones("O", "Õ"));
        listaCaracteresInvalidos.add(new Patrones("O", "Ö"));
        listaCaracteresInvalidos.add(new Patrones("U", "Ù"));
        listaCaracteresInvalidos.add(new Patrones("U", "Ú"));
        listaCaracteresInvalidos.add(new Patrones("U", "Û"));
        listaCaracteresInvalidos.add(new Patrones("U", "Ü"));
        listaCaracteresInvalidos.add(new Patrones("N", "Ñ"));
        listaCaracteresInvalidos.add(new Patrones("a", "à"));
        listaCaracteresInvalidos.add(new Patrones("a", "á"));
        listaCaracteresInvalidos.add(new Patrones("a", "â"));
        listaCaracteresInvalidos.add(new Patrones("a", "ã"));
        listaCaracteresInvalidos.add(new Patrones("a", "ä"));
        listaCaracteresInvalidos.add(new Patrones("a", "å"));
        listaCaracteresInvalidos.add(new Patrones("e", "è"));
        listaCaracteresInvalidos.add(new Patrones("e", "é"));
        listaCaracteresInvalidos.add(new Patrones("e", "ê"));
        listaCaracteresInvalidos.add(new Patrones("e", "ë"));
        listaCaracteresInvalidos.add(new Patrones("i", "ì"));
        listaCaracteresInvalidos.add(new Patrones("i", "í"));
        listaCaracteresInvalidos.add(new Patrones("i", "î"));
        listaCaracteresInvalidos.add(new Patrones("i", "ï"));
        listaCaracteresInvalidos.add(new Patrones("o", "ò"));
        listaCaracteresInvalidos.add(new Patrones("o", "ó"));
        listaCaracteresInvalidos.add(new Patrones("o", "ô"));
        listaCaracteresInvalidos.add(new Patrones("o", "õ"));
        listaCaracteresInvalidos.add(new Patrones("o", "ö"));
        listaCaracteresInvalidos.add(new Patrones("u", "ù"));
        listaCaracteresInvalidos.add(new Patrones("u", "ú"));
        listaCaracteresInvalidos.add(new Patrones("u", "û"));
        listaCaracteresInvalidos.add(new Patrones("u", "ü"));
        listaCaracteresInvalidos.add(new Patrones("n", "ñ"));
        listaCaracteresInvalidos.add(new Patrones("N", "Ñ"));
        listaCaracteresInvalidos.add(new Patrones("-", " "));
        listaCaracteresInvalidos.add(new Patrones("-", "_"));



    }

    public ArrayList<Patrones> getListaCaracteresInvalidos() {
        return listaCaracteresInvalidos;
    }

    public void setListaCaracteresInvalidos(
            ArrayList<Patrones> listaCaracteresInvalidos) {
        this.listaCaracteresInvalidos = listaCaracteresInvalidos;
    }

    public static String CHANGE_CARACTERES_INVALIDOS(String variable) {
        //ElimCaracteres clss = new ElimCaracteres();
        String resultado = "";
        /*for (Patrones o : ElimCaracteres.listaCaracteresInvalidos) {
            Pattern p = Pattern.compile(o.replace, Pattern.CASE_INSENSITIVE);
            Matcher matcher = p.matcher(variable);
            // Sustituimos todas las ocurrencias
            variable = matcher.replaceAll(o.patron);
        }*/
        for (int i = 0; i < variable.length(); i++) {
            char data = variable.charAt(i);
            if ((data >= 48 && data <= 57) || (data >= 65 && data <= 90) || (data >= 97 && data <= 122) || (data == 45)) {
                if (resultado.length() < 1 && data == 45) {
                } else {
                    resultado = resultado + data;
                }
            }
            else if(data=='á')
            {
                 resultado = resultado + "a";
            }
            else if(data=='é')
            {
                 resultado = resultado + "e";
            }
            else if(data=='í')
            {
                 resultado = resultado + "i";
            }
            else if(data=='ó')
            {
                 resultado = resultado + "o";
            }
            else if(data=='ñ')
            {
                 resultado = resultado + "n";
            } /*else if(data==','||data==';'||data=='.'||data==':')
            {
                 resultado = resultado + "-";
            }*/
        }
        return resultado;
    }
    
     public static void main(String[] args) {
         
         System.out.print(ElimCaracteres.CHANGE_CARACTERES_INVALIDOS("á,é,í,ó,ñ"));
     }
}
