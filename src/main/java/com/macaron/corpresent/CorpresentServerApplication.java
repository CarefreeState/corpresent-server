package com.macaron.corpresent;

import com.macaron.corpresent.common.constants.DateTimeConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
//@MapperScan({"com.macaron.corpresent.domain.*.model.dao.mapper"})
public class CorpresentServerApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConstants.TIME_ZONE));
		SpringApplication.run(CorpresentServerApplication.class, args);
	}

}
