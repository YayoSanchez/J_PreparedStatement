package Odonto;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Odonto {
    //creando los logs
    public static final Logger logger = Logger.getLogger(Odonto.class);
    public static final String SQL_DROP_CREATE = "DROP TABLE IF EXISTS ODONTO;"
            + "CREATE TABLE ODONTO(ID INT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, MATRICULA VARCHAR(100) NOT NULL)";
    private static final String SQL_INSERT = "INSERT INTO ODONTO VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE ODONTO SET MATRICULA = ? WHERE ID=?";

    private static final String SQL_READBD = "SELECT * FROM ODONTO";

    public static void main(String[] args) {
        Connection connection = null;
        try{
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQL_DROP_CREATE);
            logger.info("Tabla creada con exito");
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT);
            //ahora ingresamos data parametrizada
            psInsert.setInt(1,1);
            psInsert.setString(2,"Jose");
            psInsert.setString(3,"Perex");
            psInsert.setString(4,"555");

            psInsert.execute();
            logger.info("Primeros datos cargados con exito");

            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1,"888");
            psUpdate.setInt(2,1);
            psUpdate.execute();

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
    public static Connection getConnection() throws Exception{
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/TablaOdonto","admin","admin");
    }
}
