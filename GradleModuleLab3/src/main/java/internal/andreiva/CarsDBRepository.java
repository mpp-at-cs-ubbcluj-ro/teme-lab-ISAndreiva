package internal.andreiva;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN)
    {
        String sql = "select * from car_table where manufacturer=?";
        var con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, manufacturerN);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                cars.add(rsToCar(rs));
            }
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max)
    {
        String sql = "select * from car_table where year > ? and year < ?";
        var con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setInt(1, min);
            stmt.setInt(2, max);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                cars.add(rsToCar(rs));
            }
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return cars;
    }

    @Override
    public void add(Car elem) {
        String sql_check = "select * from car_table where manufacturer=? and model=? and year=?";
        var con_check = dbUtils.getConnection();
        try(PreparedStatement stmt = con_check.prepareStatement(sql_check))
        {
            stmt.setString(1, elem.getManufacturer());
            stmt.setString(2, elem.getModel());
            stmt.setInt(3, elem.getYear());
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                logger.info("Car already exists");
                System.out.println("Car already exists in the database");
                return;
            }
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }


        String sql = "insert into car_table(manufacturer, model, year) values(?, ?, ?)";
        var con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, elem.getManufacturer());
            stmt.setString(2, elem.getModel());
            stmt.setInt(3, elem.getYear());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
    }

    @Override
    public void update(Integer integer, Car elem) {
      String sql = "update car_table set manufacturer=?, model=?, year=? where id=?";
        var con = dbUtils.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, elem.getManufacturer());
            stmt.setString(2, elem.getModel());
            stmt.setInt(3, elem.getYear());
            stmt.setInt(4, integer);
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
    }

    @Override
    public Iterable<Car> findAll() {
        String sql = "select * from car_table";
        var con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                cars.add(rsToCar(rs));
            }
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        return cars;
    }

    private Car rsToCar(ResultSet rs) throws SQLException
    {
        Car car = new Car(rs.getString("manufacturer"), rs.getString("model"), rs.getInt("year"));
        car.setId(rs.getInt("id"));
        return car;
    }
}
