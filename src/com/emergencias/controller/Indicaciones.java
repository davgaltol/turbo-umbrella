package com.emergencias.controller;

/**
 * Clase de utilidad para proporcionar indicaciones de primeros auxilios
 * basadas en el código de gravedad de una emergencia.
 */
public class Indicaciones {

    /**
     * Muestra por consola indicaciones de primeros auxilios según el código proporcionado.
     * El método es estático para poder ser llamado sin necesidad de instanciar la clase.
     *
     * @param gravedadCompleta El string de gravedad del evento (ej. "Cod01 - Persona inconsciente").
     */
    public static void darIndicaciones(String gravedadCompleta) {
        if (gravedadCompleta == null || gravedadCompleta.trim().isEmpty()) {
            System.out.println("No se ha proporcionado un código de gravedad para dar indicaciones.");
            return;
        }

        System.out.println("\n===========================================");
        System.out.println("=== INDICACIONES DE PRIMEROS AUXILIOS ===");
        System.out.println("===========================================");
        System.out.println("Mantenga la calma. La ayuda profesional ya ha sido notificada y está en camino.");

        boolean seDieronIndicaciones = false;

        if (gravedadCompleta.contains("Cod01")) { // Nivel de consciencia bajo/inestable/inconsciente
            System.out.println("\nINDICACIÓN PARA 'PERSONA INCONSCIENTE':");
            System.out.println("1. Verifique si respira. Acerque su oído a su boca y nariz para sentir la respiración.");
            System.out.println("2. Si respira, colóquela de lado (posición lateral de seguridad) para mantener la vía aérea abierta.");
            System.out.println("3. Aflójele cualquier prenda apretada (cuello, cinturón). No le dé líquidos ni comida.");
            System.out.println("4. Abríguela y quédese a su lado hasta que llegue la ayuda.");
            seDieronIndicaciones = true;
        }

        if (gravedadCompleta.contains("Cod02")) { // Fallo respiratorio/Vías obstruidas
            System.out.println("\nINDICACIÓN PARA 'FALLO RESPIRATORIO / ATRAGANTAMIENTO':");
            System.out.println("1. Anime a la persona a toser con todas sus fuerzas.");
            System.out.println("2. Si no puede toser, póngase detrás y dé 5 golpes secos en la espalda, entre los omóplatos.");
            System.out.println("3. Si sigue sin funcionar, realice la Maniobra de Heimlich (compresiones abdominales).");
            seDieronIndicaciones = true;
        }

        if (gravedadCompleta.contains("Cod03")) { // Sangrado abundante
            System.out.println("\nINDICACIÓN PARA 'HEMORRAGIA FUERTE':");
            System.out.println("1. Aplique presión directa y firme sobre la herida usando un paño limpio o gasa.");
            System.out.println("2. Si es posible, eleve la extremidad afectada por encima del nivel del corazón.");
            System.out.println("3. No retire el paño si se empapa de sangre; coloque otro encima y continúe presionando.");
            seDieronIndicaciones = true;
        }

        if (gravedadCompleta.contains("Cod04")) { // Golpe grave de alta intensidad
            System.out.println("\nINDICACIÓN PARA 'GOLPE GRAVE / POSIBLE FRACTURA O QUEMADURA':");
            System.out.println("-> Para POSIBLE FRACTURA:");
            System.out.println("   1. No mueva a la persona ni la parte del cuerpo afectada, a menos que corra peligro.");
            System.out.println("   2. Inmovilice la zona en la posición en que la encontró. No intente enderezarla.");
            System.out.println("-> Para QUEMADURA GRAVE:");
            System.out.println("   1. Enfríe la quemadura con agua corriente fría (no helada) durante 10-15 minutos.");
            System.out.println("   2. No aplique hielo, cremas o pomadas. Cubra con un paño limpio y seco.");
            seDieronIndicaciones = true;
        }

        if (!seDieronIndicaciones) {
            System.out.println("\nNo hay indicaciones específicas para la emergencia reportada.");
            System.out.println("Vigile el estado del herido y no lo deje solo hasta que llegue la ayuda.");
        }

        System.out.println("===========================================");
    }
}
