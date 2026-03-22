package com.emergencias.controller;

public class Indicaciones {

    /**
     * Devuelve un String con las indicaciones de primeros auxilios.
     * @param gravedadCompleta El string de gravedad del evento.
     * @return Un String con las indicaciones formateadas.
     */
    public static String getIndicaciones(String gravedadCompleta) {
        if (gravedadCompleta == null || gravedadCompleta.trim().isEmpty()) {
            return "No se ha proporcionado un código de gravedad para dar indicaciones.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n===========================================\n");
        sb.append("=== INDICACIONES DE PRIMEROS AUXILIOS ===\n");
        sb.append("===========================================\n");
        sb.append("Mantenga la calma. La ayuda profesional ya ha sido notificada y está en camino.\n");

        boolean seDieronIndicaciones = false;

        if (gravedadCompleta.contains("Cod01")) {
            sb.append("\nINDICACIÓN PARA 'PERSONA INCONSCIENTE':\n");
            sb.append("1. Verifique si respira.\n");
            sb.append("2. Si respira, colóquela de lado (posición lateral de seguridad).\n");
            sb.append("3. Aflójele cualquier prenda apretada. No le dé líquidos ni comida.\n");
            seDieronIndicaciones = true;
        }
        if (gravedadCompleta.contains("Cod02")) {
            sb.append("\nINDICACIÓN PARA 'FALLO RESPIRATORIO / ATRAGANTAMIENTO':\n");
            sb.append("1. Anime a la persona a toser con fuerza.\n");
            sb.append("2. Dé 5 golpes secos en la espalda, entre los omóplatos.\n");
            sb.append("3. Si no funciona, realice la Maniobra de Heimlich.\n");
            seDieronIndicaciones = true;
        }
        if (gravedadCompleta.contains("Cod03")) {
            sb.append("\nINDICACIÓN PARA 'HEMORRAGIA FUERTE':\n");
            sb.append("1. Aplique presión directa y firme sobre la herida.\n");
            sb.append("2. Eleve la extremidad afectada por encima del nivel del corazón.\n");
            sb.append("3. No retire el paño si se empapa; coloque otro encima.\n");
            seDieronIndicaciones = true;
        }
        if (gravedadCompleta.contains("Cod04")) {
            sb.append("\nINDICACIÓN PARA 'GOLPE GRAVE / POSIBLE FRACTURA O QUEMADURA':\n");
            sb.append("-> Para POSIBLE FRACTURA: No mueva la zona afectada.\n");
            sb.append("-> Para QUEMADURA GRAVE: Enfríe con agua 10-15 min. No aplique cremas.\n");
            seDieronIndicaciones = true;
        }

        if (!seDieronIndicaciones) {
            sb.append("\nNo hay indicaciones específicas. Vigile al herido.\n");
        }
        sb.append("===========================================\n");
        
        return sb.toString();
    }
}
