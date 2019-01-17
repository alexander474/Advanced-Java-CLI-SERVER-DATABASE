package no.kristiania.pgr200.cli;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InteractiveUpdate extends CommandHandler {
    public InteractiveUpdate(Scanner sc) {
        super(sc);
    }
}
