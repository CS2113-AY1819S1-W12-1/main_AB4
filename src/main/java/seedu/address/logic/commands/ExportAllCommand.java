package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Filetype;
import seedu.address.model.Model;

//@@author jitwei98
/**
 * Exports all persons in the address book to a csv/vcf file.
 */
public class ExportAllCommand extends Command {

    public static final String COMMAND_WORD = "exportall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports all the persons in the address book"
            + "Parameters: FILETYPE (must be either \"csv\" or \"vcf\") "
            + "Example: " + COMMAND_WORD + " csv ";

    public static final String MESSAGE_ARGUMENTS = "Filetype: %1$s";
    public static final String MESSAGE_SUCCESS = "Exported all contacts.";
    private static final String MESSAGE_FAILURE = "Export failed!";

    private Filetype filetype;

    public ExportAllCommand(Filetype filetype) {
        requireNonNull(filetype);

        this.filetype = filetype;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.exportAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // same object
        if (other == this) {
            return true;
        }

        // handles nulls
        if (!(other instanceof ExportAllCommand)) {
            return false;
        }

        // checks state
        ExportAllCommand e = (ExportAllCommand) other;
        return filetype.equals(e.filetype);
    }
}
