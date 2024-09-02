package baada2.casino.utils;

public class EndPoint {
    private static String REGISTRAR_USER = "/user/registrar";

    public static String getRegistrarUser() {
        return REGISTRAR_USER;
    }

    public static void setRegistrarUser(String registrarUser) {
        REGISTRAR_USER = registrarUser;
    }
}
