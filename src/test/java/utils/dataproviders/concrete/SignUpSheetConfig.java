package utils.dataproviders.concrete;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import utils.dataproviders.interfaces.SheetConfig;
import utils.dataproviders.models.SignUpTestData;

public class SignUpSheetConfig implements SheetConfig<SignUpTestData> {

	private enum Columns {

		TEST_CASE_ID(0), FIRST_NAME(1), LAST_NAME(2), EMAIL_ADDRESS(3), PASSWORD(4), PASSWORD_CONFIRM(5), EXPECTED_RESULT(6);

		final int index;

		Columns(int index) {
			this.index = index;
		}
	}

	@Override
	public String[] getExpectedHeaders() {
		return new String[] { "TestCaseID", "FirstName", "LastName", "EmailAddress", "Password", "PasswordConfirm",
				"ExpectedResult" };
	}

	@Override
	public SignUpTestData mapRow(Row row) {
		return new SignUpTestData()
				.setTestCaseId(getString(row, Columns.TEST_CASE_ID))
				.setFirstName(getString(row, Columns.FIRST_NAME))
				.setLastName(getString(row, Columns.LAST_NAME))
				.setEmail(getString(row, Columns.EMAIL_ADDRESS))
				.setPassword(getString(row, Columns.PASSWORD))
				.setPasswordConfirm(getString(row, Columns.PASSWORD_CONFIRM))
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
	public void validateRequiredFields(SignUpTestData data, int rowNumber) {
		// TODO Auto-generated method stub

	}

}
