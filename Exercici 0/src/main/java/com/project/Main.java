package com.project;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {

        ConcurrentHashMap<String, Double> datos = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable recibirOperacion = () -> {
            datos.put("saldo", 1000.0);
            datos.put("importe", 500.0);

            System.out.println("Operación bancaria recibida.");
            System.out.println("Saldo inicial: " + datos.get("saldo") + " €");
            System.out.println("Importe de la operación: " + datos.get("importe") + " €");
        };

        Runnable calcularComision = () -> {
            double saldo = datos.get("saldo");
            double importe = datos.get("importe");

            double comision = importe * 0.02;
            double saldoActualizado = saldo + importe - comision;

            datos.put("comision", comision);
            datos.put("saldo", saldoActualizado);

            System.out.println("Comisión calculada: " + comision + " €");
            System.out.println("Saldo actualizado: " + saldoActualizado + " €");
        };

        Callable<Double> consultarSaldo = () -> {
            double saldoFinal = datos.get("saldo");

            System.out.println("Consultando saldo final...");

            return saldoFinal;
        };

        try {
            executor.submit(recibirOperacion);

            Thread.sleep(100);

            executor.submit(calcularComision);

            Thread.sleep(100);

            Future<Double> resultado = executor.submit(consultarSaldo);

            double saldoFinal = resultado.get();

            System.out.println();
            System.out.println("===== RESULTADO FINAL =====");
            System.out.println("Saldo final de la operación: "
                    + saldoFinal + " €");
            System.out.println("===========================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}