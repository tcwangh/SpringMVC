package idv.tim.mvcprofile.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

public interface DataSourceConfig {
	
	public DataSource getDataSource() throws IllegalArgumentException, NamingException ;

}
