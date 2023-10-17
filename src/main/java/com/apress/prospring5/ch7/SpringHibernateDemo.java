package com.apress.prospring5.ch7;

import com.apress.prospring5.ch7.config.AppConfig;
import com.apress.prospring5.ch7.dao.SingerDao;
import com.apress.prospring5.ch7.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class SpringHibernateDemo {

    private static Logger logger = LoggerFactory.getLogger(SpringHibernateDemo.class);

    private static void listSingersWithAlbum(List<Singer> singers){
        logger.info("==========List of Singers (Including instruments to handle)");

        singers.forEach(s -> {
            if(s.getAlbums() != null){
                s.getAlbums().forEach(a -> logger.info("==========\t" + a.toString()));
            }
            if(s.getInstruments() != null){
                s.getInstruments().forEach(a -> logger.info("==========\t" + a.toString()));
            }
        });

    }

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        listSingersWithAlbum(singerDao.findAll());

        ctx.close();
    }

}
