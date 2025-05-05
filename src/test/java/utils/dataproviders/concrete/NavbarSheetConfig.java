package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.NavbarTestData;

public class NavbarSheetConfig implements SheetConfig<NavbarTestData> {

	private enum Columns {
		
		TEST_CASE_ID(0), MAIN_MENU(1), SUB_MENU(2), SUB_SUB_MENU(3), EXPECTED_URL(4);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "Level 0 (Main Menu)", "Level 1 (Submenu)", "Level 2 (Sub-submenu)", "Expected URL" };
	}

	@Override
	public NavbarTestData mapRow(Row row) {
		return new NavbarTestData().setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setMainMenu(getString(row, Columns.MAIN_MENU))
				.setSubMenu(getString(row, Columns.SUB_MENU))
				.setSubSubMenu(getString(row, Columns.SUB_SUB_MENU))
				.setExpectedUrl(getString(row, Columns.EXPECTED_URL));
		
	}

	private String getString(Row row, Columns column) {
		Cell cell = row.getCell(column.index);
		if (cell == null)
			return "";

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell).trim();
	}

	@Override
	public void validateRequiredFields(NavbarTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}

}