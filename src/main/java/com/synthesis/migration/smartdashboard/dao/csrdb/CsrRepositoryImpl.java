package com.synthesis.migration.smartdashboard.dao.csrdb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(transactionManager="csrSqlTransactionManager")
public class CsrRepositoryImpl implements CsrRepository{

	@PersistenceContext(unitName="csr")
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
