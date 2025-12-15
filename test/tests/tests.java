package tests;

import com.emergencias.controller.RetrieveData;
import com.emergencias.controller.ValidaEntrada;
import com.emergencias.model.UserData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para las utilidades de validación.
 *
 * NOTA IMPORTANTE SOBRE TESTING:
 * Para poder probar otras clases como EmergencyDetector o AlertSender de forma efectiva,
 * es necesario refactorizar (rediseñar) el código para que sea más "testeable".
 *
 * 1. Clases que usan `Scanner(System.in)`: Deberían recibir el Scanner o el InputStream
 *    en su constructor. Esto permite que en los tests se les pase un flujo de datos
 *    preparado en lugar de depender de la entrada del usuario en tiempo real.
 *
 * 2. Constructores con lógica: El constructor de `EmergencyEvent` contiene casi toda
 *    la lógica de la aplicación. Un constructor solo debería asignar valores a los campos
 *    de la clase. Toda esa lógica debería moverse a otra clase (como `EmergencyDetector`)
 *    y al final, crear un `new EmergencyEvent(...)` pasándole los datos ya recopilados.
 *
 * Los tests a continuación se centran en la clase `ValidaEntrada`, que es un ejemplo
 * perfecto de código testeable porque sus métodos son estáticos y no dependen de
 * entradas/salidas externas.
 */
public class tests {

    // --- Tests para ValidaEntrada ---

    @Test
    void testValidaEntSN_ConLetraS() {
        assertTrue(ValidaEntrada.validaEntSN("S"), "Debería devolver true para 'S' mayúscula");
        assertTrue(ValidaEntrada.validaEntSN("s"), "Debería devolver true para 's' minúscula");
    }

    @Test
    void testValidaEntSN_ConLetraN() {
        // La función validaEntSN devuelve true si es 'S' o 's', y false para cualquier otra cosa.
        assertFalse(ValidaEntrada.validaEntSN("N"), "Debería devolver false para 'N'");
        assertFalse(ValidaEntrada.validaEntSN("n"), "Debería devolver false para 'n'");
    }

    @Test
    void testValidaEntSN_ConEntradaInvalida() {
        assertFalse(ValidaEntrada.validaEntSN("Si"), "Debería devolver false para 'Si'");
        assertFalse(ValidaEntrada.validaEntSN("No"), "Debería devolver false para 'No'");
        assertFalse(ValidaEntrada.validaEntSN("CualquierCosa"), "Debería devolver false para una cadena larga");
        assertFalse(ValidaEntrada.validaEntSN(""), "Debería devolver false para una cadena vacía");
        assertFalse(ValidaEntrada.validaEntSN(null), "Debería devolver false para un valor nulo");
    }

    @Test
    void testValidaEntDNI_ConFormatoCorrecto() {
        assertTrue(ValidaEntrada.validaEntDNI("12345678A"), "Un DNI válido debería devolver true");
        assertTrue(ValidaEntrada.validaEntDNI("98765432Z"), "Otro DNI válido debería devolver true");
    }

    @Test
    void testValidaEntDNI_ConFormatoIncorrecto() {
        assertFalse(ValidaEntrada.validaEntDNI("1234567A"), "Inválido: menos de 8 números");
        assertFalse(ValidaEntrada.validaEntDNI("123456789A"), "Inválido: más de 8 números");
        assertFalse(ValidaEntrada.validaEntDNI("12345678"), "Inválido: sin letra");
        assertFalse(ValidaEntrada.validaEntDNI("ABCDEFGHA"), "Inválido: letras en lugar de números");
        assertFalse(ValidaEntrada.validaEntDNI("12345678a"), "Inválido: letra minúscula (según el regex actual)");
        assertFalse(ValidaEntrada.validaEntDNI(""), "Inválido: cadena vacía");
        assertFalse(ValidaEntrada.validaEntDNI(null), "Inválido: nulo");
    }
    // En Pruebas.java


// ... (dentro de la clase Pruebas)

    @Test
    void testRetrieveData_CuandoDniExiste() {
        // GIVEN: Un DNI que sabemos que existe en el fichero pacientes.json
        String dniExistente = "11111111A";

        // WHEN: Llamamos al método estático para recuperar los datos
        UserData paciente = RetrieveData.retrieveInjuredData(dniExistente);

        // THEN: Verificamos que se ha devuelto un objeto y que sus datos son correctos
        assertNotNull(paciente, "El paciente no debería ser nulo para un DNI que existe.");
        assertEquals("Juan", paciente.getNombre(), "El nombre del paciente no coincide.");
        assertEquals("Pérez Gómez", paciente.getApellidos(), "Los apellidos del paciente no coinciden.");
        assertEquals(dniExistente, paciente.getDni(), "El DNI del paciente no coincide.");
    }

    @Test
    void testRetrieveData_CuandoDniNoExiste() {
        // GIVEN: Un DNI que sabemos que NO existe en el fichero pacientes.json
        String dniInexistente = "00000000Z";

        // WHEN: Llamamos al método estático
        UserData paciente = RetrieveData.retrieveInjuredData(dniInexistente);

        // THEN: Verificamos que el resultado es nulo
        assertNull(paciente, "El resultado debería ser nulo para un DNI que no existe.");
    }

    @Test
    void testRetrieveData_CuandoDniEsInvalido() {
        // GIVEN: Diferentes tipos de DNI inválidos
        String dniNulo = null;
        String dniVacio = "";
        String dniConEspacios = "   ";

        // WHEN & THEN: Verificamos que para todas las entradas inválidas, el resultado es nulo
        assertNull(RetrieveData.retrieveInjuredData(dniNulo), "El resultado debería ser nulo para un DNI nulo.");
        assertNull(RetrieveData.retrieveInjuredData(dniVacio), "El resultado debería ser nulo para un DNI vacío.");
        assertNull(RetrieveData.retrieveInjuredData(dniConEspacios), "El resultado debería ser nulo para un DNI con solo espacios.");
    }
}
