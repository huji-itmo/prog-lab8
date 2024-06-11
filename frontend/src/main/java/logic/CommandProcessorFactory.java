package logic;

import commands.CommandDataProcessor;
import commands.clientSideCommands.*;
import commands.clientSideCommands.commandData.ExitDatabaseCommandData;
import commands.clientSideCommands.commandData.HelpCommandData;
import commands.databaseCommands.*;
import connection.ConnectionWithServer;
import studyGroupSpecific.StudyGroupEntityBuilder;
import studyGroupSpecific.StudyGroupValidator;
import validator.Validator;

public class CommandProcessorFactory {

    public static CommandDataProcessor create(ConnectionWithServer connection) {
        CommandDataProcessor processor = new CommandDataProcessor(new StudyGroupValidator(new StudyGroupEntityBuilder()));

        processor.addCommandData(new HelpCommandData());
        processor.addCommandData(new ExitDatabaseCommandData());
        processor.addCommandData(new AddCommandData());
        processor.addCommandData(new AddIfMinData());
        processor.addCommandData(new ClearCommandData());
        processor.addCommandData(new CountLessThanFormOfEducationCommandData());
        processor.addCommandData(new InfoCommandData());
        processor.addCommandData(new PrintDescendingCommandData());
        processor.addCommandData(new RemoveByIdCommandData());
        processor.addCommandData(new RemoveGreaterCommandData());
        processor.addCommandData(new RemoveLowerCommandData());
        processor.addCommandData(new ShowCommandData());
        processor.addCommandData(new SumOfAverageMarkCommandData());
        processor.addCommandData(new UndoCommandData());
        processor.addCommandData(new UpdateByIdCommandData());
        processor.addCommandData(new GetMinStudentCountCommandData());

        processor.addClientCommand(new HelpCommandImpl(processor));
        processor.addClientCommand(new HistoryCommandImpl(processor));
        processor.addClientCommand(new ExitDatabaseCommandImpl());
        processor.addClientCommand(new UpdateIdClientSideCommandImpl(connection, processor.getValidator()));
        processor.addClientCommand(new ExecuteScriptCommand(connection,processor));
        processor.addClientCommand(new AddIfMinCommandImpl(connection,processor.getValidator()));

        return processor;
    }
}
