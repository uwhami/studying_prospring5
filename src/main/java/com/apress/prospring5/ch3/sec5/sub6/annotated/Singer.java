package com.apress.prospring5.ch3.sec5.sub6.annotated;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("nonSingleton")
@Scope("prototype")
public class Singer {
        private String name = "unknown";

        public Singer(@Value("John Mayer") String name){
            this.name = name;
        }
}
