package utils;

/** Prints the VaultFS startup banner. */
public class Banner {
    /** Prints the exact ASCII banner lines to the console. */
    public static void print() {
        System.out.println(Colors.c(Colors.CYAN + Colors.BOLD, " __     __         _ _   _____ ____"));
        System.out.println(Colors.c(Colors.CYAN + Colors.BOLD, " \\ \\   / /_ _ _   _| | |_|  ___/ ___|"));
        System.out.println(Colors.c(Colors.CYAN + Colors.BOLD, "  \\ \\ / / _` | | | | | __| |_  \\___ " + "\\"));
        System.out.println(Colors.c(Colors.CYAN + Colors.BOLD, "   \\ V / (_| | |_| | | |_|  _|  ___) |"));
        System.out.println(Colors.c(Colors.CYAN + Colors.BOLD, "    \\_/ \\__,_|\\__,_|_|\\__|_|   |____/"));
        System.out.println("");
        System.out.println(Colors.c(Colors.BOLD, "         VaultFS CLI  v1.0.9"));
        System.out.println(Colors.c(Colors.WHITE, "     Secure File System Simulator"));
        System.out.println(Colors.c(Colors.GRAY, "─────────────────────────────────────"));
    }
}
