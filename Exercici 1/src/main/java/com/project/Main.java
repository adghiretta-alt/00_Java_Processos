package com.project;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        CompletableFuture<Integer> solicitud = CompletableFuture.supplyAsync(() -> {
            System.out.println("Validando los datos de la solicitud...");

            int valorInicial = 100;

            System.out.println("Solicitud validada.");
            System.out.println("Valor inicial: " + valorInicial);

            return valorInicial;
        });

        CompletableFuture<Integer> resultado = solicitud.thenApply(valor -> {
            System.out.println("Procesando los datos...");

            // Simulamos un cálculo
            int resultadoCalculado = valor * 2;

            System.out.println("Resultado calculado: " + resultadoCalculado);

            return resultadoCalculado;
        });

        CompletableFuture<Void> respuesta = resultado.thenAccept(valor -> {
            System.out.println("Enviando respuesta al usuario...");
            System.out.println("Resultado final: " + valor);
        });

        respuesta.join();

        System.out.println("Todas las operaciones han finalizado.");
    }
}
