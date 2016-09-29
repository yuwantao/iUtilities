package com.csv;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Created by yuwt on 2016/9/29.
 */
public class CsvTest {
	private static DataSource dataSourceTest;
	static {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
			dataSource.setJdbcUrl("jdbc:mysql://192.168.10.213:3306/test?useUnicode=true&amp;characterEncoding=utf-8&useSSL=false");
			dataSource.setUser("pdc");
			dataSource.setPassword("pdc@2014");
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(2);
			dataSource.setInitialPoolSize(1);
			dataSource.setAcquireIncrement(1);
			dataSource.setMaxIdleTime(60);
			dataSource.setMaxStatements(0);
			dataSource.setIdleConnectionTestPeriod(60);
			dataSource.setAcquireRetryAttempts(30);
			dataSource.setBreakAfterAcquireFailure(false);
			dataSource.setAcquireRetryDelay(1000);
			dataSource.setTestConnectionOnCheckout(false);
		} catch (PropertyVetoException e) {
		}
		dataSourceTest = dataSource;
	}

	public static JdbcTemplate jdbcTemplateTest = new JdbcTemplate(dataSourceTest);


}
