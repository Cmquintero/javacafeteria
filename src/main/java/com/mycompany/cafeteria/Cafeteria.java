package com.mycompany.cafeteria;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class Venta {
    private String item;
    private double precio;

    public Venta(String item, double precio) {
        this.item = item;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return item + "," + precio;
    }
}

public class Cafeteria {

    private static final Path filePath = Paths.get("./cuentasdiarias.txt");

    public static void main(String[] args) {
        try {
            File file = filePath.toFile();
            if (file.createNewFile()) {
                System.out.println("El archivo fue creado correctamente.");
            } else {
                System.out.println("El archivo ya existía.");
            }

            // Leer y mostrar ventas previas
            leerVentas();

            // Registrar nuevas ventas
            registrarVenta(new Venta("Café", 1.50));
            registrarVenta(new Venta("Té", 1.00));
            registrarVenta(new Venta("Sándwich", 3.00));

            // Leer y mostrar ventas nuevamente
            leerVentas();

        } catch (IOException e) {
            System.out.println("No se pudo manipular el documento: " + e.getMessage());
        }
    }

    private static void leerVentas() {
        System.out.println("Ventas registradas:");
        try (BufferedReader buffer = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error leyendo las ventas: " + e.getMessage());
        }
    }

    private static void registrarVenta(Venta venta) {
        try (BufferedWriter buffer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, Files.exists(filePath) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            buffer.write(venta.toString());
            buffer.newLine();
            System.out.println("Venta registrada: " + venta);
        } catch (IOException e) {
            System.out.println("Error registrando la venta: " + e.getMessage());
        }
    }
}
