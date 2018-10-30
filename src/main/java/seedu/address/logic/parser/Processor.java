package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser class to extract keywords from user input
 * Searches the args[] string for keywords and copies them in different strings
 */
public class Processor {

    /*
    String userInput;

    public Processor(String userInput) {
        this.userInput = userInput;
   }
    */

    /**
     * Adds the string tokens in sortedArray into a single string, ignoring null entries in the array
     *
     * @param s the sorted string array to be concatenated
     * @param glue the space to be added in between each array index
     * @return a concatenated string
     */
    private String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int i = 1; i < k; i++) {
            if (s[i] != null) {
                out.append(glue).append(s[i]);
            }
        }
        return out.toString();
    }

    /**
     * Boolean function that checks if a given String is an integer
     *
     * @param s the string to be checked
     * @return true if String is integer, false if otherwise
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Sorts the tokens in args[] into the correct sequence and concatenates them into a string
     * @param userInput the line input by user
     * @return the sorted string
     * @throws ParseException
     */
    public String commandParser(String userInput) throws ParseException {
        String[] args = userInput.split(" ", 0);
        String targetIndex = null;
        String sortedInput;
        String[] sortedArray = new String[10];

        for (int i = 0; i < args.length; i++) {
            if (isInteger(args[i])) {
                targetIndex = args[i];
            }

            switch (args[i]) {

            // "COMMAND_WORD + PREFIX + DATA" argument pattern
            case AddCommand.COMMAND_WORD:
                userInput.replace(args[i], "");
                String[] prefixArguments = userInput.split("/", 0);
                int tagIndex = 5;
                sortedArray[0] = args[i];
                for (int j = 0; j < prefixArguments.length; j++) {
                    String prefix = prefixArguments[j].substring(prefixArguments[j].length()-1);
                    switch (prefix) {
                    case ("n"):
                        sortedArray[1] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("p"):
                        sortedArray[2] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("e"):
                        sortedArray[3] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("a"):
                        sortedArray[4] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("t"):
                        sortedArray[tagIndex++] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    default:
                    }
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            // "COMMAND_WORD + INDEX + PREFIX + DATA" argument pattern
            case EditCommand.COMMAND_WORD:
                userInput.replace(args[i], "");
                prefixArguments = userInput.split("/", 0);
                tagIndex = 6;
                sortedArray[0] = args[i];
                sortedArray[1] = targetIndex;
                for (int j = 0; j < prefixArguments.length; j++) {
                    String prefix = prefixArguments[j].substring(prefixArguments[j].length()-1);
                    switch (prefix) {
                    case ("n"):
                        sortedArray[2] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("p"):
                        sortedArray[3] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("e"):
                        sortedArray[4] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("a"):
                        sortedArray[5] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    case ("t"):
                        sortedArray[tagIndex++] = prefixArguments[j+1].substring(0, prefixArguments[j+1].length() - 2);
                    default:
                    }
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            //fallthrough, similar "COMMAND_WORD + INDEX" argument pattern
            case SelectCommand.COMMAND_WORD:
            case DeleteCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                sortedArray[1] = targetIndex;
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            case FindCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                int keywordIndex = 1;
                for (int j = 0; j < args.length; j++) {
                    sortedArray[keywordIndex++] = args[j];
                }
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            // fallthrough, similar "COMMAND_WORD" argument pattern
            case ClearCommand.COMMAND_WORD:
            case ListCommand.COMMAND_WORD:
            case HistoryCommand.COMMAND_WORD:
            case UndoCommand.COMMAND_WORD:
            case RedoCommand.COMMAND_WORD:
            case HelpCommand.COMMAND_WORD:
            case ExitCommand.COMMAND_WORD:
                sortedArray[0] = args[i];
                sortedInput = combine(sortedArray, " ");
                return sortedInput;

            default:
            }
        }
        throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
    }
}
