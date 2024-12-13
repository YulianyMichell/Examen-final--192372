package entities;

import java.util.ArrayList;

public class Agente extends Entidad {
    private String habilidadEspecial;
    private String misionAsignada;
    private ArrayList<Anomalia> anomaliasAsignadas; 

    public Agente(int id, String nombre, String habilidadEspecial) {
        super(id, nombre);
        this.habilidadEspecial = habilidadEspecial;
        this.anomaliasAsignadas = new ArrayList<>();
    }

    public String getHabilidadEspecial() {
        return habilidadEspecial;
    }

    public void setHabilidadEspecial(String habilidadEspecial) {
        this.habilidadEspecial = habilidadEspecial;
    }

    public String getMisionAsignada() {
        return misionAsignada;
    }

    public void asignarMision(String mision) {
        this.misionAsignada = mision;
    }

    public void asignarAnomalia(Anomalia anomalia) {
        this.anomaliasAsignadas.add(anomalia);
    }

    public void mostrarAnomaliasAsignadas() {
        if (anomaliasAsignadas.isEmpty()) {
            System.out.println("No se han asignado anomalías.");
        } else {
            System.out.println("Anomalías asignadas:");
            for (Anomalia anomalia : anomaliasAsignadas) {
                System.out.println(anomalia);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Habilidad Especial: " + habilidadEspecial +
               (misionAsignada != null ? ", Misión: " + misionAsignada : "");
    }
}



package management;

import entities.Anomalia;
import entities.Agente;
import utils.ConsoleUtils;

import java.util.ArrayList;

public class AnomaliaManager {
    private ArrayList<Anomalia> anomalias;
    private Agente agente; // Agente global

    public AnomaliaManager() {
        this.anomalias = new ArrayList<>();
        this.agente = null;
    }

    public void registrarAnomalia() {
        int id = ConsoleUtils.leerEntero("Ingrese el ID de la anomalía: ");
        ConsoleUtils.limpiarBuffer();

        String nombre = ConsoleUtils.leerTexto("Ingrese el nombre de la anomalía: ");
        int severidad = ConsoleUtils.leerEntero("Ingrese el nivel de severidad (1-10): ");
        String ubicacion = ConsoleUtils.leerTexto("Ingrese la ubicación de la anomalía: ");
       
        Anomalia nuevaAnomalia = new Anomalia(id, nombre, severidad, ubicacion);
        anomalias.add(nuevaAnomalia);
        System.out.println("Anomalía registrada con éxito.");
    }

    public void asignarAnomaliaAAgente() {
        if (anomalias.isEmpty()) {
            System.out.println("No hay anomalías registradas.");
            return;
        }
        if (agente == null) {
            System.out.println("No se ha asignado ningún agente.");
            return;
        }

        // Mostrar las anomalías disponibles
        System.out.println("Seleccione una anomalía para asignar:");
        for (int i = 0; i < anomalias.size(); i++) {
            System.out.println((i + 1) + ". " + anomalias.get(i));
        }

        int opcion = ConsoleUtils.leerEntero("Seleccione una anomalía: ");
        ConsoleUtils.limpiarBuffer();

        if (opcion < 1 || opcion > anomalias.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        Anomalia anomaliaSeleccionada = anomalias.get(opcion - 1);
        agente.asignarAnomalia(anomaliaSeleccionada);  // Asignar la anomalía al agente
        System.out.println("Anomalía asignada con éxito.");
    }

    public void mostrarAnomalias() {
        if (anomalias.isEmpty()) {
            System.out.println("No hay anomalías registradas.");
            return;
        }
        System.out.println("=== Lista de Anomalías ===");
        for (Anomalia anomalia : anomalias) {
            System.out.println(anomalia);
        }
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== Menú Anomalías ===");
            System.out.println("1. Registrar Anomalía");
            System.out.println("2. Mostrar Anomalías");
            System.out.println("3. Asignar Anomalía a Agente");
            System.out.println("4. Mostrar Anomalías Asignadas a Agente");
            System.out.println("5. Salir");
            int opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");
            ConsoleUtils.limpiarBuffer();
    
            switch (opcion) {
                case 1: registrarAnomalia(); break;
                case 2: mostrarAnomalias(); break;
                case 3: 
                    if (agente == null) {
                        agente = new Agente(1, "Agente X", "Habilidad Y");  // Crear el agente solo una vez
                    }
                    asignarAnomaliaAAgente(); break;
                case 4: 
                    if (agente != null) {
                        agente.mostrarAnomaliasAsignadas();  // Mostrar las anomalías asignadas
                    } else {
                        System.out.println("No hay agente asignado.");
                    }
                    break;
                case 5: continuar = false; break;
            }
        }
    }
}
