package com.apress.prospring5.ch11.xml_scheduled_task.config;

import com.apress.prospring5.ch11.xml_scheduled_task.entities.Car;
import com.apress.prospring5.ch11.xml_scheduled_task.repos.CarRepository;
import jakarta.annotation.PostConstruct;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBInitializer {

    private static Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Autowired
    CarRepository carRepository;

    @PostConstruct
    public void initDB(){
        logger.info("========== Initialize Database ......");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

        Car car = new Car();
        car.setLicensePlate("GRAVITY-0405");
        car.setManufacturer("Ford");
        car.setManufactureDate(DateTime.parse("2006-09-12", formatter));
        carRepository.save(car);

        car = new Car();
        car.setLicensePlate("CLARITY-0432");
        car.setManufacturer("Toyota");
        car.setManufactureDate(DateTime.parse("2003-09-09", formatter));
        carRepository.save(car);

        car = new Car();
        car.setLicensePlate("ROSIE-0402");
        car.setManufacturer("Toyota");
        car.setManufactureDate(DateTime.parse("2017-04-16", formatter));
        carRepository.save(car);

        logger.info("========== End Initialization Database.");
    }

}
