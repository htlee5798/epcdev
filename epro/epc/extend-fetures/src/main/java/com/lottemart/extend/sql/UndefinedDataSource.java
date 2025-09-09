package com.lottemart.extend.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 정의되지 않은 데이터 소스
 * 개발 PC용
 * @author pat
 *
 */
public class UndefinedDataSource implements DataSource, AutoCloseable {
	
	private String	dataSourceName;
	
	public UndefinedDataSource(String dataSourceName) {
		this.dataSourceName	= dataSourceName;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public Connection getConnection() throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLException("undefined datasource '" + this.dataSourceName + "'");
	}

	@Override
	public void close() throws Exception {
		// DO Notting
	}
}
