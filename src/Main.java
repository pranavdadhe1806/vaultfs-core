import java.util.Scanner;

/** Entry point for the File System Simulator CLI. */
public class Main {
    /** Starts the command loop and routes input to FileSystem operations. */
    public static void main(String[] args) {
        filesystem.FileSystem fs = new filesystem.FileSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to File System Simulator. Type 'help' to view commands.");

        while (true) {
            System.out.print(fs.currentDirectory.absolutePath + "> ");
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            String[] tokens = line.trim().split("\\s+");
            String command = tokens[0];
            try {
                if ("pwd".equals(command)) {
                    fs.pwd();
                } else if ("cd".equals(command)) {
                    fs.cd(tokens[1]);
                } else if ("mkdir".equals(command)) {
                    fs.mkdir(tokens[1]);
                } else if ("rmdir".equals(command) && "-f".equals(tokens[1])) {
                    fs.rmdir(tokens[2], true);
                } else if ("rmdir".equals(command)) {
                    fs.rmdir(tokens[1], false);
                } else if ("rename".equals(command)) {
                    if (fs.currentDirectory.fileIndex.contains(tokens[1])) {
                        fs.renameFile(tokens[1], tokens[2]);
                    } else {
                        fs.renameDirectory(tokens[1], tokens[2]);
                    }
                } else if ("create".equals(command)) {
                    fs.createFile(tokens[1], Long.parseLong(tokens[2]));
                } else if ("delete".equals(command)) {
                    fs.deleteFile(tokens[1]);
                } else if ("info".equals(command)) {
                    fs.info(tokens[1]);
                } else if ("find".equals(command)) {
                    fs.find(tokens[1]);
                } else if ("search".equals(command) && "-t".equals(tokens[1])) {
                    fs.searchByType(tokens[2]);
                } else if ("search".equals(command)) {
                    fs.find(tokens[1]);
                } else if ("ls".equals(command) && "-l".equals(tokens[1])) {
                    fs.ls(true);
                } else if ("ls".equals(command)) {
                    fs.ls(false);
                } else if ("tree".equals(command) && tokens.length > 1) {
                    fs.tree(tokens[1]);
                } else if ("tree".equals(command)) {
                    fs.tree(null);
                } else if ("topk".equals(command) && tokens.length > 2) {
                    fs.topK(Integer.parseInt(tokens[1]), tokens[2]);
                } else if ("topk".equals(command)) {
                    fs.topK(Integer.parseInt(tokens[1]), null);
                } else if ("help".equals(command)) {
                    printHelp();
                } else if ("clear".equals(command)) {
                    clearScreen();
                } else if ("exit".equals(command)) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("[command not found] type 'help' to see all commands");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Missing argument for '" + command + "'. Type 'help' for usage.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Usage: create <name> <bytes> or topk <k>");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /** Prints the complete command help table. */
    private static void printHelp() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║           File System Simulator — Commands           ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║ pwd                      Print current path          ║");
        System.out.println("║ cd <name>                Navigate into directory     ║");
        System.out.println("║ cd ..                    Go one level up             ║");
        System.out.println("║ cd /                     Go to root                  ║");
        System.out.println("║ mkdir <name>             Create directory            ║");
        System.out.println("║ rmdir <name>             Delete empty directory      ║");
        System.out.println("║ rmdir -f <name>          Force delete directory      ║");
        System.out.println("║ rename <old> <new>       Rename file or directory    ║");
        System.out.println("║ create <name> <bytes>    Create file                 ║");
        System.out.println("║ delete <name>            Delete file                 ║");
        System.out.println("║ info <name>              Show file metadata          ║");
        System.out.println("║ find <name>              Find file in tree           ║");
        System.out.println("║ search -t <type>         Search files by type        ║");
        System.out.println("║ ls                       List current directory      ║");
        System.out.println("║ ls -l                    Detailed listing            ║");
        System.out.println("║ tree                     Print full directory tree   ║");
        System.out.println("║ tree <path>              Print subtree              ║");
        System.out.println("║ topk <k>                 Top k largest files         ║");
        System.out.println("║ topk <k> <path>          Top k in specific path      ║");
        System.out.println("║ clear                    Clear terminal              ║");
        System.out.println("║ exit                     Exit program                ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    /** Prints 50 newlines to clear the terminal view. */
    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
