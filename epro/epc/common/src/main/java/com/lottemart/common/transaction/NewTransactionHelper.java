package com.lottemart.common.transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.lottemart.common.exception.AlertException;

/**
 * @Class Name : 
 * @Description : 새로운 Transaction에서 실행하도록 도와주는 helper class
 * [코딩예]
 * 
   	@Resource
	private NewTransactionHelper newTransactionHelper;
	
   		newTransactionHelper.proceed(new NewTransactionJob(){
			@Override
			public Object execute() {
				return zipcodeDao.updateZipcode(vo);
			}			
		});
 *    
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class NewTransactionHelper   {

	private static Log logger = LogFactory.getLog(NewTransactionHelper.class);

	private final PlatformTransactionManager txManager ;

	public NewTransactionHelper(PlatformTransactionManager transactionManager) {
		this.txManager = transactionManager;
	}

	public <E> E proceed(final NewTransactionJob<E> job) throws Exception{
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("Requires new Transaction");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		
		TransactionStatus status = txManager.getTransaction(def);
		E result = null;
		try {
			result = job.execute();
		} catch (Throwable e) {
			logger.error(e);
			txManager.rollback(status);
			if(e instanceof Exception){
				throw (Exception)e;
			}else{
				throw new AlertException(e.getMessage());
			}
		}
		
		txManager.commit(status);
		return result;
	}



	public interface NewTransactionJob<E>{
		public E execute() throws Exception;		
	}
	
}
