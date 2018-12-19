package com.synthesis.migration.smartdashboard;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.synthesis.migration.smartdashboard.dao.csrdb.CsrRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMasterRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMigrationRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsValueRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.MigrationHistoryRepository;
import com.synthesis.migration.smartdashboard.dao.omniadb.OminaRepositoryCustom;
import com.synthesis.migration.smartdashboard.dao.rbmdb.RbmRepository;
import com.synthesis.migration.smartdashboard.dto.FalloutProgressChartDto;
import com.synthesis.migration.smartdashboard.service.DashBoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartTestUsingSpring {
	
	@MockBean
	private EntityMasterRepository entityMasterRepositoryMock;

	@MockBean
	private CsrRepository csrRepositoryMock;

	@MockBean
	private RbmRepository rbmRepositoryMock;

	@MockBean
	private OminaRepositoryCustom ominaRepositoryMock;

	@MockBean
	private EnvironmentDetailsValueRepository environmentDetailsValueRepositoryMock;

	@MockBean
	private EnvironmentDetailsRepository environmentDetailsRepositoryMock;

	@MockBean
	private MigrationHistoryRepository migrationHistoryRepositoryMock;


	@MockBean
	private EntityMigrationRepository entityMigrationRepository;
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Test
	public void testFetchProgressData() {
		List<Object[]> list = new ArrayList<>();
		Object[] objs = new Object[9];
		objs[0] = 1l;
		objs[1] = "Migration description";
		objs[2] = 1l;
		objs[3] = "Accounts";
		objs[4] = 3l;
		objs[5] = "omnia";
		objs[6] = 2808l;
		objs[7] = "Telecom";
		objs[8] = 1544595947000l;
		list.add(objs);
		
		Mockito.when(environmentDetailsValueRepositoryMock.findDetailsByEntityIdCustomQuery()).thenReturn(list);
		List<FalloutProgressChartDto> dtos = dashBoardService.fetchFalloutDataFromSmart();
		System.out.println(dtos.size());
		assertTrue("True", dtos.size() == 1);
		//assertNotNull(dashBoardService.fetchFalloutDataFromSmart());
		
	}

}
