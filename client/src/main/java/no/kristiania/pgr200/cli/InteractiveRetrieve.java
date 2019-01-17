package no.kristiania.pgr200.cli;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveRetrieve<T> extends CommandHandler {
    public InteractiveRetrieve(Scanner sc) {
        super(sc);
    }

}
