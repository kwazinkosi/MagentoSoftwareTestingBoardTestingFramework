package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.SearchTestData;

public class SearchSheetConfig implements SheetConfig<SearchTestData> {

	private enum Columns {

		TEST_CASE_ID(0), SEARCH_WORD(1), EXPECTED_RESULT(2);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "SearchWord", "ExpectedResults" };
	}

	@Override
	public SearchTestData mapRow(Row row) {
		
		return new SearchTestData().setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setSearchTerm(getString(row, Columns.SEARCH_WORD))
				.setExpectedResult(getString(row, Columns.EXPECTED_RESULT));
	}

	private String getString(Row row, Columns column) {
		
		Cell cell = row.getCell(column.index);
		if (cell == null)
			return "";

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell).trim();
	}

	@Override
	public void validateRequiredFields(SearchTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}
}
