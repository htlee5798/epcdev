package com.lottemart.epc.edi.payment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;
import lcn.module.framework.base.AbstractDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.edi.product.model.NewProduct;



@Repository("pedmpay0030Dao")
public class PEDMPAY0030Dao extends AbstractDAO {

	private static final Logger logger = LoggerFactory.getLogger(PEDMPAY0030Dao.class);

	public List selectPayCountData(Map<String,Object> map) throws Exception{
		return (List<HashBox>)getSqlMapClientTemplate().queryForList("PEDMPAY0030.TED_PAMENT_CNT-SELECT01",map);
	}

	public Integer selectPamentStayCount(Map<String,Object> map) throws Exception{
		return (Integer) getSqlMapClientTemplate().queryForObject("PEDMPAY0030.TED_PAMENT_CNT-SELECT02", map);
	}

	public boolean sendExecuteCommand(Map<String,Object> map) {


		Connection 		  conn = null;
		PreparedStatement pstmt = null;
		ResultSet 		  rs = null;

		try
		{
			String batchName     = ConfigUtils.getString("edi.batch.name");				//배치명
			String connectionUrl = ConfigUtils.getString("edi.batch.conurl");			//jdbc:hsqldb:hsql://10.52.2.140/jobdb
			String id            = ConfigUtils.getString("edi.batch.conurl.id");		//sa
			String pass          = ConfigUtils.getString("edi.batch.conurl.pass") == null ? "":ConfigUtils.getString("edi.batch.conurl.pass");		//""

			StringBuffer query = new StringBuffer();



			query.append(" update batch_info set check_run='J', job_info='{ ");
			query.append("     FUNC= CALL; 			");
			query.append("    GRP_NM = EPC;			");
			query.append("    JOB_NM = "+batchName+";	");
			query.append("    P1 = "+map.get("payYm")+";");
			query.append("    P2 = "+map.get("splyCycle")+";");
			query.append("    P3 = "+map.get("splySeq")+";");
			query.append(" }'  ");
			query.append(" where job_type='JOB'; ");


			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection(connectionUrl, id, pass);

			this.setAutoCommit(conn, false);

			pstmt = conn.prepareStatement(query.toString());
			pstmt.executeUpdate();


			this.commit(conn);

			return true;
		}
		catch (Exception e)
		{
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage());
			}
		}
		finally
		{
			this.setAutoCommit(conn, true);

			if(conn != null) {
				try {
				conn.close();
			} catch (SQLException e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if(pstmt != null) {
				try {
				pstmt.close();
			} catch (SQLException e) {
				logger.debug("error : " + e.getMessage());
				}
			}
			if(rs != null) {
				try {
				rs.close();
				} catch (SQLException e) {
					logger.debug("error : " + e.getMessage());
				}
			}
		}

		return false;
	}



	/**
	 * Connection AutoCommit Set
	 * @param conn Connection
	 * @param autoCommit isAutoCommit
	 */
	private void setAutoCommit(Connection conn, boolean autoCommit) {
		if (conn != null) {
			try {
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				logger.debug("error : " + e.getMessage());
			}
		}
	}


	/**
	 * Connection commit
	 * @param conn Connection
	 */
	private void commit(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				logger.debug("error : " + e.getMessage());
			}
		}
	}


}
