package internal.andreiva;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainBD {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("src/main/resources/bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);
        carRepo.add(new Car("Tesla","Model S", 2019));

        carRepo.update(5,new Car("Tesla","Model 3", 2014));

        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);

        System.out.println("Masinile produse intre 2005 si 2015");
        for(Car car:carRepo.findBetweenYears(2005,2015))
            System.out.println(car);

        carRepo.update(5,new Car("Tesla","Model 3", 2019));

    }
}
