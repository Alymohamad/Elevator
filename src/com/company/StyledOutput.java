package com.company;

/**
 * Used for "goodlooking" Console output
 * Displays Text in form of boxes by using Header, Text, Delimiter and Footer elements
 * Should replace all System.out.println() calls
 * 1 Tab = 3 Whitespaces
 *
 * Example Usage: (with WIDTH=20)
 *      StyledOutput.printHeading("Header")
 *      StyledOutput.printText("Text")
 *      StyledOutput.printFooter()
 *
 * Example Output:
 *      ╔══────────────────────»
 *      ║ Header
 *      ╠══────────────────────»
 *      ║	Text
 *      ╚══────────────────────»
 *
 * @version 1.0
 * @author  ITTB20VZ_03
 */
public class StyledOutput {

    private static final int WIDTH = 95;

    /**
     * Prints ASCII based banner
     *
     * @since     1.0
     */
    public static void printBanner() {
        StyledOutput.clearScreen();
        System.out.println("\n\n\n" +
                "██████   ██████       ████████  ██████   ██        ██ ███████ ██████  \n" +
                "██    ██ ██              ██    ██    ██ ██        ██ ██      ██   ██ \n" +
                "██    ██ ██                   ██     ██       ██ ██   █   ██ █████     ██████  \n" +
                "██   ██ ██                   ██      ██     ██  ██ ███ ██ ██       ██   ██ \n" +
                "██████   ██████          ██       ██████   ███  ███  ███████ ██     ██ \n" +
                "                                                                \n" +
                "                                                                " +
                "                                                                ");

    }

    /**
     * Prints horizonal line
     * Used by all methods printing horizontal box elements to assure same width
     * Uses Static WIDTH attribute -> {@link this.WIDTH}
     *
     * @since 1.0
     */
    private static String printLine(){
        return new String(new char[WIDTH]).replace("\0", "─");
    }

    /**
     * Print the header line of a box with only one
     * Used for (Error) messages
     * Prepends new line
     *
     * @since 1.0
     */
    public static void printHead() {
        System.out.println();
        System.out.println("╔══" + printLine() + "»");
    }

    /**
     * Print a single box with only one line of text
     * Used for (Error) messages
     * Prepends new line
     *
     * @param text Text to print
     * @since 1.0
     */
    public static void printBlock(String text) {
        printHead();
        printText(text);
        printFoot();
    }

    /**
     * Print Seperator to use inside a box
     *
     * @since 1.0
     */
    public static void printSeparator() {
        System.out.println("╠══"+printLine()+"»");
    }

    /**
     * Open Box and print header followed by a sepearator
     * Expects additional text to be added below
     * Prepends new line
     *
     * @param text Text to print as header of the box
     * @since 1.0
     */
    public static void printHeading(String text) {
        printHead();
        printText(text);
        printSeparator();
    }

    /**
     * Print text with leading ║
     *
     * @param text Text to print
     * @since 1.0
     */
    public static void printText(String text) {
        System.out.println("║   "+ text.replace("\n", "\n║   "));
    }

    /**
     * Print text without new line to use with scan
     *
     * @param text Text to print
     * @since 1.0
     */
    public static void printPrompt(String text) {
        System.out.print("║   " + text);
    }

    /**
     * Print footer to close a box
     *
     * @since 1.0
     */
    public static void printFoot() {
        System.out.println("╚══"+printLine()+"»");
    }

    /**
     * Clears the terminal
     *
     * @since 1.0
     */
    public static void clearScreen(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}
