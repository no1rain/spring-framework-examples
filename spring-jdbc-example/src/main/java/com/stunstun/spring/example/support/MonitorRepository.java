
package com.stunstun.spring.example.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author stunstun(minhyuck.jung@nhnent.com)
 *
 */
@Repository
public class MonitorRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public int selectStatus() {
		return jdbcTemplate.queryForInt("SELECT 1");
	}
}