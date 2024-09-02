package baada2.casino;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        // Datos de los socios
        String[][] socios = {{"TC", "RAMIREZ FLOREZ JORGE ALEXANDER",  "91535239",  "91535239",  "EN PROCESO"},
                {"MY", "SOTO PEDRAZA NESTOR ANTONIO",  "74085200",  "74085200",  "EN PROCESO"},
                {"MY", "ROMERO FERNANDEZ NELSON SEBASTIAN",  "86080272",  "86080272",  "EN PROCESO"},
                {"CT", "SANCHEZ CARDOZO EDWIN ALBERTO",  "1110460568",  "1110460568",  "EN PROCESO"},
                {"CT", "ROSERO SANCHEZ FELIPE",  "1114388233",  "1114388233",  "EN PROCESO"},
                {"CT", "PRIETO VEGA CRISTIAN CAMILO",  "1016026019",  "1016026019",  "EN PROCESO"},
                {"TE", "LOAIZA SEGURA JUAN SEBASTIAN",  "1144079148",  "1144079148",  "EN PROCESO"},
                {"ST", "BARRERO MARTINEZ JESSICA VANESSA",  "1105682776",  "1105682776",  "EN PROCESO"},
                {"ST", "RESTREPO GUERRERO STEVAN DAVID",  "1001272295",  "1001272295",  "EN PROCESO"},
                {"ST", "CHAPARRO GUILLEN NICOLAS",  "1193128052",  "1193128052",  "EN PROCESO"},
                {"ST", "PARRA ESCOBAR ESTEBAN CAMILO",  "1024600024",  "1024600024",  "EN PROCESO"},
                {"ST", "HERRERA RAMIREZ JULIAN ANDRES",  "1004767776",  "1004767776",  "EN PROCESO"},
                {"SM", "VARGAS VARGAS JAIRO NELSON",  "80130084",  "80130084",  "EN PROCESO"},
                {"SP", "RUDAS GARCIA MARDONIO DE JESUS",  "84084696",  "84084696",  "EN PROCESO"},
                {"SP", "CAMACHO VALENCIA JORGE",  "91044826",  "91044826",  "EN PROCESO"},
                {"SP", "VERA RAMIREZ WILFORD",  "93413342",  "93413342",  "EN PROCESO"},
                {"SP", "PENAGOS CAMACHO MIGUEL ANGEL",  "2950868",  "2950868",  "EN PROCESO"},
                {"SP", "CARABALI BALANTA GUSTAVO ADOLFO",  "10493773",  "10493773",  "EN PROCESO"},
                {"SP", "BEDOYA VALDERRAMA YEIMIR",  "94475833",  "94475833",  "EN PROCESO"},
                {"SP", "ROMERO MENDEZ YAIR ENRIQUE",  "9237077",  "9237077",  "EN PROCESO"},
                {"SP", "CASTRILLON PEREA JOSE ANDRES",  "1115062237",  "1115062237",  "EN PROCESO"},
                {"SV", "CADENA ROMERO JAIRO",  "79992916",  "79992916",  "EN PROCESO"},
                {"SV", "ESQUIVEL ESQUIVEL WILSON",  "83222308",  "83222308",  "EN PROCESO"},
                {"SV", "MONROY PRADA CRISTIAN SUESSI",  "79924949",  "79924949",  "EN PROCESO"},
                {"SV", "CHAVES RUBIO JUAN CARLOS",  "1032377219",  "1032377219",  "EN PROCESO"},
                {"SV", "MENESES TORRES JHON JAMES",  "4614954",  "4614954",  "EN PROCESO"},
                {"SV", "MENDEZ MENDOZA JEISSON JOSE",  "80071864",  "80071864",  "EN PROCESO"},
                {"SV", "HERNANDEZ MAUSA BAYRON",  "8058515",  "8058515",  "EN PROCESO"},
                {"SV", "PARADA IBAÑEZ JOSE ALEXANDER",  "3171937",  "3171937",  "EN PROCESO"},
                {"SV", "RAMOS AREVALO DIEGO ALEJANDRO",  "9773545",  "9773545",  "EN PROCESO"},
                {"SV", "HERNANDEZ PAYARES JUAN CARLOS",  "72334215",  "72334215",  "EN PROCESO"},
                {"SV", "MANSO ARICAPA JUAN CARLOS",  "4539087",  "4539087",  "EN PROCESO"},
                {"SV", "ORTIZ RICO ANDERSSON BEDULFO",  "1098655254",  "1098655254",  "EN PROCESO"},
                {"SV", "VARGAS DAVID OCTAVIO",  "80212357",  "80212357",  "EN PROCESO"},
                {"SV", "DIAZ CABALLERO RODOLFO ENRIQUE",  "1082867129",  "1082867129",  "EN PROCESO"},
                {"SS", "NORIEGA RODRIGUEZ VICTOR ALFONSO",  "1064706122",  "1064706122",  "EN PROCESO"},
                {"SS", "AMBUILA MOSQUERA AINER AUGUSTO",  "1062287314",  "1062287314",  "EN PROCESO"},
                {"SS", "MENDOZA RODRIGUEZ DANIEL ALEJANDRO",  "1065603506",  "1065603506",  "EN PROCESO"},
                {"SS", "OCHOA RIVERA JOHN FRANKLIN",  "81753019",  "81753019",  "EN PROCESO"},
                {"SS", "RINCON NEIRA JHON FERNANDO",  "1071629405",  "1071629405",  "EN PROCESO"},
                {"SS", "TUNAROZA PORTILLA JOSE VICENTE",  "1048821767",  "1048821767",  "EN PROCESO"},
                {"SS", "MELGAREJO RODRIGUEZ JUAN PABLO",  "1056994449",  "1056994449",  "EN PROCESO"},
                {"SS", "ARICAPA VILLADA CARLOS MARIO",  "1090333227",  "1090333227",  "EN PROCESO"},
                {"SS", "MORENO CASTAÑO FREDY ALEJANDRO",  "1058460396",  "1058460396",  "EN PROCESO"},
                {"SS", "TUMIÑA MEDINA JOSE GABRIEL",  "10308564",  "10308564",  "EN PROCESO"},
                {"SS", "PINEDA BEDOYA MARCO FERNANDO",  "1097390727",  "1097390727",  "EN PROCESO"},
                {"SS", "GARCES BARRIOS FABIO ANDRES",  "1091356000",  "1091356000",  "EN PROCESO"},
                {"SS", "SUAREZ PEREZ JHON EDUARDO",  "1024610451",  "1024610451",  "EN PROCESO"},
                {"SS", "GARCIA MOSQUERA CARLOS ANDRES",  "1078144597",  "1078144597",  "EN PROCESO"},
                {"SS", "VARGAS GARCIA CARLOS ALBERTO",  "99173002",  "99173002",  "EN PROCESO"},
                {"SS", "PEREIRA CASTAÑO ALEJANDRO",  "1076872055",  "1076872055",  "EN PROCESO"},
                {"SS", "ARIZA LEAL JHON JAIRO",  "1065531105",  "1065531105",  "EN PROCESO"},
                {"SS", "PINEDA RIVERA JOSE ALFONSO",  "1085106574",  "1085106574",  "EN PROCESO"},
                {"SS", "PUENTES GARCIA LINA MARCELA",  "1052751346",  "1052751346",  "EN PROCESO"},
                {"SS", "GARCIA ACOSTA JHON JAIRO",  "1030795518",  "1030795518",  "EN PROCESO"},
                {"SS", "MONTES SALGADO JHON JAIRO",  "1091593027",  "1091593027",  "EN PROCESO"},
                {"SS", "LOPEZ CASTAÑO CESAR ALEJANDRO",  "80093632",  "80093632",  "EN PROCESO"},
                {"SS", "HERRERA GARCIA ALEXANDER",  "1133829054",  "1133829054",  "EN PROCESO"},
                {"SS", "BARRERA TORO GABRIEL",  "81671176",  "81671176",  "EN PROCESO"},
                {"SS", "GARCIA HERRERA CARLOS ALFONSO",  "1052599448",  "1052599448",  "EN PROCESO"},
                {"SS", "MATEUS TORO JOSE ALEJANDRO",  "1071623150",  "1071623150",  "EN PROCESO"},
                {"SS", "PARRA SALGADO OSCAR ALBERTO",  "1103157965",  "1103157965",  "EN PROCESO"},
                {"SS", "GARCÍA FERNÁNDEZ LEONARDO",  "1040799902",  "1040799902",  "EN PROCESO"},
                {"SS", "RODRIGUEZ ARIAS LEONEL",  "1031642214",  "1031642214",  "EN PROCESO"},
                {"C2", "MONTOYA CASTAÑO JULIAN ESTEBAN",  "1096346442",  "1096346442",  "EN PROCESO"},
                {"C2", "SALGADO HERRERA ALEXANDER",  "83093164",  "83093164",  "EN PROCESO"},
                {"C2", "AGUDELO MELO JUAN PABLO",  "1066754643",  "1066754643",  "EN PROCESO"},
                {"C2", "SALGADO SALGADO ALEJANDRO",  "1047922360",  "1047922360",  "EN PROCESO"},
                {"C2", "GARCÍA CASTAÑO JAVIER ALBERTO",  "1054366677",  "1054366677",  "EN PROCESO"},
                {"C2", "HERRERA QUINTERO MAURICIO",  "83210524",  "83210524",  "EN PROCESO"},
                {"C2", "GARCÍA ARIAS IVÁN ANDRÉS",  "1065606432",  "1065606432",  "EN PROCESO"},
                {"C2", "GARCÍA TORO JESUS ALBERTO",  "1076211628",  "1076211628",  "EN PROCESO"},
                {"C2", "ARANGO VALENCIA JHON JAIRO",  "83742511",  "83742511",  "EN PROCESO"},
                {"C2", "SALGADO CASTAÑO RICARDO",  "80048511",  "80048511",  "EN PROCESO"},
                {"C2", "RIVERA LOPEZ JHON JAIRO",  "1044376373",  "1044376373",  "EN PROCESO"},
                {"C2", "PEREIRA CASTAÑO ALEJANDRO",  "1072044963",  "1072044963",  "EN PROCESO"},
                {"C3", "TORO CAMPOALEGRITO ROBERTO",  "1001309464",  "1001309464",  "EN PROCESO"},
                {"C3", "MOROCHO MOROCHO JHON JAIRO",  "1005084686",  "1005084686",  "EN PROCESO"},
                {"C3", "REYES GARCÍA JORGE",  "1051949122",  "1051949122",  "EN PROCESO"},
                {"C3", "LOPEZ RESTREPO HERMES",  "1088030963",  "1088030963",  "EN PROCESO"},
                {"C3", "SERRANO LOPEZ ELIAN ALEXANDER",  "1007701659",  "1007701659",  "EN PROCESO"}
                // Agrega el resto de los datos de los socios aquí
        };

        // URL de la API
        String apiUrl = "http://localhost:8080/socios";

        // Procesar cada socio
        for (String[] socio : socios) {
            enviarDatosSocio(apiUrl, socio);
        }
    }

    private static void enviarDatosSocio(String apiUrl, String[] socio) {
        HttpURLConnection conn = null;
        try {
            // Configuración de la conexión
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Crear el JSON de entrada
            String jsonInputString = crearJsonSocio(socio);

            // Enviar los datos
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Verificar la respuesta
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Socio " + socio[1] + " guardado correctamente.");
            } else {
                System.out.println("Error al guardar al socio " + socio[1] + ": " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static String crearJsonSocio(String[] socio) {
        return String.format(
                "{\"grado\": \"%s\", \"nombre\": \"%s\", \"estado\": \"%s\", \"password\": \"%s\"}",
                socio[0], socio[1], socio[4], socio[3]
        );
    }
}
