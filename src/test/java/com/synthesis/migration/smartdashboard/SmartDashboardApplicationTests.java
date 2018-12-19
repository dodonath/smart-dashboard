package com.synthesis.migration.smartdashboard;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.synthesis.migration.smartdashboard.dao.csrdb.CsrRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMasterRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EntityMigrationRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.EnvironmentDetailsValueRepository;
import com.synthesis.migration.smartdashboard.dao.defaultdb.MigrationHistoryRepository;
import com.synthesis.migration.smartdashboard.dao.omniadb.OminaRepositoryCustom;
import com.synthesis.migration.smartdashboard.dao.rbmdb.RbmRepository;
import com.synthesis.migration.smartdashboard.service.impl.DashBoardServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SmartDashboardApplicationTests {
	
	@Mock
	private EntityMasterRepository entityMasterRepositoryMock;

	@Mock
	private CsrRepository csrRepositoryMock;

	@Mock
	private RbmRepository rbmRepositoryMock;

	@Mock
	private OminaRepositoryCustom ominaRepositoryMock;

	@Mock
	private EnvironmentDetailsValueRepository environmentDetailsValueRepositoryMock;

	@Mock
	private EnvironmentDetailsRepository environmentDetailsRepositoryMock;

	@Mock
	private MigrationHistoryRepository migrationHistoryRepositoryMock;


	@Mock
	private EntityMigrationRepository entityMigrationRepository;
	
	@InjectMocks
	private DashBoardServiceImpl dashBoardService;

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
		assertNotNull(dashBoardService.fetchFalloutDataFromSmart());
		
	}
		
}
