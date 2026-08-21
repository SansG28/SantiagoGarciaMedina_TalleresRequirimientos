import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class UserStoryValidator {

    // 1. PALABRAS VAGAS
    private static final List<String> PALABRAS_VAGAS = Arrays.asList(
            "rápido", "fácil", "amigable", "intuitivo", "flexible", "pronto", 
            "bien", "mejor", "bonito", "transparente", "adecuado", "robusto"
    );

    // 2. PALABRAS IMPOSIBLES/ABSOLUTAS 
    private static final List<String> PALABRAS_IMPOSIBLES = Arrays.asList(
            "nunca", "siempre", "100%", "cero errores", "infinito", "instantáneo", 
            "nadie", "todos", "cualquier"
    );

    // 3. PALABRAS DE VALOR 
    private static final List<String> PALABRAS_RELEVANCIA = Arrays.asList(
            "para ", "con el fin de", "y así", "lograr", "valor", "mejorar", 
            "permitir", "ahorrar", "reducir", "aumentar"
    );

    // 4. PALABRAS VERIFICABLES 
    private static final List<String> PALABRAS_VERIFICABLES = Arrays.asList(
            "cuando", "si ", "debe", "máximo", "mínimo", "menos de", "más de", 
            "al menos", "exactamente", "hasta", "límite"
    );

    public static void main(String[] args) {
        List<String> historiasDeUsuario = Arrays.asList(
                
                "REQ-042: Como administrador, quiero que las búsquedas devuelvan resultados en máximo 2 segundos para catálogos de hasta 100,000 libros, para ahorrar tiempo de gestión."
        );

        System.out.println("=== INICIANDO VALIDADOR DE HISTORIAS DE USUARIO ===\n");

        for (String hu : historiasDeUsuario) {
            evaluarRequisito(hu);
        }
    }

    public static void evaluarRequisito(String requisito) {
        String reqLower = requisito.toLowerCase();

        // Evaluaciones individuales
        boolean esEspecifico = esEspecifico(reqLower);
        boolean esMedible = esMedible(reqLower);
        boolean esAlcanzable = esAlcanzable(reqLower);
        boolean esRelevante = esRelevante(reqLower);
        boolean esIdentificable = esIdentificable(requisito); 
        boolean esVerificable = esVerificable(reqLower);

        // Calcular puntaje
        boolean[] resultados = {esEspecifico, esMedible, esAlcanzable, esRelevante, esIdentificable, esVerificable};
        int puntaje = 0;
        for (boolean res : resultados) {
            if (res) puntaje++;
        }

        // Imprimir resultados en consola 
        String iconoGeneral = (puntaje == 6) ? "✅" : "❌";
        System.out.printf("%s [%d/6] %s...\n", iconoGeneral, puntaje, requisito.substring(0, Math.min(requisito.length(), 60)));
        System.out.println("    " + (esEspecifico ? "✔" : "x") + " Específico  " + (esEspecifico ? "" : "(Contiene palabras ambiguas)"));
        System.out.println("    " + (esMedible ? "✔" : "x") + " Medible     " + (esMedible ? "" : "(Faltan números o porcentajes)"));
        System.out.println("    " + (esAlcanzable ? "✔" : "x") + " Alcanzable  " + (esAlcanzable ? "" : "(Contiene términos absolutos o imposibles)"));
        System.out.println("    " + (esRelevante ? "✔" : "x") + " Relevante   " + (esRelevante ? "" : "(Falta justificación de valor 'para...', 'con el fin de...')"));
        System.out.println("    " + (esIdentificable ? "✔" : "x") + " Identificable " + (esIdentificable ? "" : "(Falta ID único tipo REQ-001 o el rol 'Como...')"));
        System.out.println("    " + (esVerificable ? "✔" : "x") + " Verificable " + (esVerificable ? "" : "(Faltan condiciones o criterios de prueba)"));
        System.out.println("------------------------------------------------------------\n");
    }

    // 1. Específico: claro, sin ambigüedad
    private static boolean esEspecifico(String reqLower) {
        for (String palabra : PALABRAS_VAGAS) {
            if (reqLower.contains(palabra)) {
                return false; // Si tiene una palabra vaga, no es específico
            }
        }
        return true;
    }

    // 2. Medible: segundos / porcentajes 
    private static boolean esMedible(String reqLower) {
        boolean tieneNumero = Pattern.compile("\\d+").matcher(reqLower).find();
        boolean tienePorcentaje = reqLower.contains("%");
        return tieneNumero || tienePorcentaje;
    }

    // 3. Alcanzable: que pueda realizarse 
    private static boolean esAlcanzable(String reqLower) {
        for (String palabra : PALABRAS_IMPOSIBLES) {
            if (reqLower.contains(palabra)) {
                return false; 
            }
        }
        return true;
    }

    // 4. Relevante: generan valor 
    private static boolean esRelevante(String reqLower) {
        for (String palabra : PALABRAS_RELEVANCIA) {
            if (reqLower.contains(palabra)) {
                return true;
            }
        }
        return false;
    }

    // 5. Identificable: quien lo pidió y que tiene un ID único
    private static boolean esIdentificable(String requisito) {
        // Exige un ID al inicio tipo REQ-001, US-123, etc.
        boolean tieneID = Pattern.compile("^[A-Z]+-\\d{1,5}").matcher(requisito.trim()).find();
        
        // Exige que se mencione al actor 
        boolean tieneActor = requisito.toLowerCase().contains("como ") || requisito.toLowerCase().contains("actor:");
        
        // Es exigente: requiere AMBOS
        return tieneID && tieneActor;
    }

    // 6. Verificable: probar que funciona 
    private static boolean esVerificable(String reqLower) {
        for (String palabra : PALABRAS_VERIFICABLES) {
            if (reqLower.contains(palabra)) {
                return true;
            }
        }
        return false;
    }
}