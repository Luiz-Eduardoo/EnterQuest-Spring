package com.startup.enterquest.util;

public class ValidadorEntrada {

    public static boolean contemCaracteresMaliciosos(String texto) {
        return contemScript(texto);
    }

    public static boolean contemScript(String texto) {
        if (texto == null) {
            return false;
        }

        String textoMinusculo = texto.toLowerCase();

        return textoMinusculo.contains("<script") ||
                textoMinusculo.contains("</script>") ||
                textoMinusculo.contains("javascript:") ||
                textoMinusculo.contains("onerror=") ||
                textoMinusculo.contains("onload=") ||
                textoMinusculo.contains("onclick=") ||
                textoMinusculo.contains("onmouseover=") ||
                textoMinusculo.contains("<iframe") ||
                textoMinusculo.contains("</iframe>") ||
                textoMinusculo.contains("<img") ||
                textoMinusculo.contains("<svg") ||
                textoMinusculo.contains("document.cookie") ||
                textoMinusculo.contains("alert(");
    }
}