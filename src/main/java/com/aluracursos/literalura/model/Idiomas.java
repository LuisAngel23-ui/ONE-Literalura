package com.aluracursos.literalura.model;

public enum Idiomas {

    INGLES("en", "Ingles"),
    ESPANOL("es", "Español"),


    AFRIKAANS("af", "Afrikáans"),
    ALEMAN("de", "Alemán"),
    ARABE("ar", "Árabe"),
    BRETON("br", "Bretón"),
    BULGARO("bg", "Búlgaro"),
    CATALAN("ca", "Catalán"),
    CHECO("cs", "Checo"),
    CHINO("zh", "Chino"),
    COREANO("ko", "Coreano"),
    DANES("da", "Danés"),
    ESLOVENO("sl", "Esloveno"),
    ESPERANTO("eo", "Esperanto"),
    ESTONIO("et", "Estonio"),
    FINES("fi", "Finés"),
    FRANCES("fr", "Francés"),
    FRISON("fy", "Frisón"),
    GAELICO("gd", "Gaélico"),
    GALES("cy", "Galés"),
    GALLEGO("gl", "Gallego"),
    GRIEGO("el", "Griego"),
    HEBREO("he", "Hebreo"),
    HUNGARO("hu", "Húngaro"),
    INTERLINGUA("ia", "Interlingua"),
    INUKTITUT("iu", "Inuktitut"),
    IRLANDES("ga", "Irlandés"),
    ISLANDES("is", "Islandés"),
    ITALIANO("it", "Italiano"),
    JAPONES("ja", "Japonés"),
    LATIN("la", "Latín"),
    LITUANO("lt", "Lituano"),
    MAORI("mi", "Maorí"),
    NAVAJO("nv", "Navajo"),
    NEERLANDES("nl", "Neerlandés"),
    NORUEGO("no", "Noruego"),
    OCCITANO("oc", "Occitano"),
    OJIBWA("oj", "Ojibwa"),
    PERSA("fa", "Persa"),
    POLACO("pl", "Polaco"),
    PORTUGUES("pt", "Portugués"),
    RUMANO("ro", "Rumano"),
    RUSO("ru", "Ruso"),
    SANSCRITO("sa", "Sánscrito"),
    SERBIO("sr", "Serbio"),
    SUECO("sv", "Sueco"),
    TAGALO("tl", "Tagalo"),
    TELUGU("te", "Telugu"),
    TIBETANO("bo", "Tibetano"),
    YIDDISH("yi", "Yiddish"),

    ALEUTIANO("ale", "Aleutiano"),
    ARAPAHO("arp", "Arapaho"),
    BODO("brx", "Bodo"),
    CALO("rmq", "Caló"),
    CASUBIO("csb", "Casubio"),
    CEBUANO("ceb", "Cebuano"),
    ESCOCES("sco", "Escocés"),
    FRIULANO("fur", "Friulano"),
    GAMILARAAY("kld", "Gamilaraay"),
    GRIEGO_ANTIGUO("grc", "Griego Antiguo"),
    HAIDA("hai", "Haida"),
    ILOCANO("ilo", "Ilocano"),
    INGLES_ANTIGUO("ang", "Inglés Antiguo"),
    INGLES_MEDIO("enm", "Inglés Medio"),
    KHASI("kha", "Khasi"),
    LENGUAS_MAYAS("myn", "Lenguas Mayas"),
    NAHUATL("nah", "Náhuatl"),
    NAPOLITANO("nap", "Napolitano"),
    NORTH_AMERICAN_INDIAN("nai", "North American Indian"),
    TAGABAWA("bgs", "Tagabawa");

    String simboloAPI; // Para la interfaz grafica.
    String idiomaUI;

    Idiomas(String simboloAPI,String idiomaUI){
        this.simboloAPI = simboloAPI;
        this.idiomaUI = idiomaUI;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.simboloAPI.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    @Override
    public String toString() {
        return this.idiomaUI;
    }
}
