package com.csv;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.utils.FileUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.List;

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

	public static void main(String[] args) {
		testWrite();
//		testRead();
	}

	private static void testRead() {
		String filename = "/data/library/test.csv";
		try {
			CSVReader reader = new CSVReader(new FileReader(filename), '\t', '"', 2);
			String[] next = reader.readNext();
			System.out.println(next[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testWrite() {
		for (int i=120;i<200;i++) {
			int block = 100000;
			String sql = "select isbn from bmcb order by o1 desc,chubanshijian desc limit " + block +
					" offset " + (i * block);
			List<String> isbns = jdbcTemplateTest.query(sql, (rs, rowNum) -> rs.getString("isbn"));
			String filename = "/data/library/" + i + ".csv";
			try {
				File file = FileUtils.createFile(filename);
				CSVWriter writer = new CSVWriter(new FileWriter(file), '\t');
				isbns.forEach(isbn -> writer.writeNext(new String[] {isbn}));
				writer.close();
				System.out.println("write " + i + ".csv succeeded.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
