package com.synthesis.migration.smartdashboard.dao.rbmdb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager="rbmSqlTransactionManager")
public class RbmRepositoryImpl implements RbmRepository{

	@PersistenceContext(unitName="rbm")
    private EntityManager em;

	@Override
	@Transactional(readOnly=true)
	public Long getCount(String sqlQuery) {
		Object countObject = em.createNativeQuery(sqlQuery).getSingleResult();
		if(countObject!=null)
		{
			return Long.valueOf(countObject.toString());
		}
		return 0l;
	}

}
