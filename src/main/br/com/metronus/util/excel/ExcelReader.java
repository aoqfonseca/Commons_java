package br.com.neoris.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader {

	private POIFSFileSystem fs;

	private HSSFWorkbook wb;

	private HSSFSheet sheet;

	private HSSFCell cell;

	private HSSFRow row;

	private Context ctx;

	private Iterator rows;

	public void read(InputStream file, ExcelReaderListener listener)
			throws IOException {
		fs = new POIFSFileSystem(file);
		wb = new HSSFWorkbook(fs);
		sheet = wb.getSheetAt(0);
		ctx = new Context();
		// Iterate over each row in the sheet
		rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			checkForNewRowEvent(ctx, listener, row);
			// Iterate over each cell in the row and print out the cell's content
			for (short i = 0; i <= row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if ((cell == null)	|| (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)	|| (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR)) {
					continue;
				}
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:					
				case HSSFCell.CELL_TYPE_NUMERIC:
					// checkForNewRowEvent(ctx, listener, row);
					listener.read(row.getRowNum() + 1, cell.getCellNum() + 1,cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					// checkForNewRowEvent(ctx, listener, row);
					String value = cell.getStringCellValue().trim();
					if (value.equals("")) {
						continue;
					}
					try {
						listener.read(row.getRowNum() + 1,cell.getCellNum() + 1, new Double(value).doubleValue());
					} catch (NumberFormatException e) {
						try {
							listener.read(row.getRowNum() + 1, cell.getCellNum() + 1, new Double(value.replace(',', '.')).doubleValue());
						} catch (NumberFormatException e2) {
							listener.read(row.getRowNum() + 1, cell.getCellNum() + 1, value);
						}
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					// checkForNewRowEvent(ctx, listener, row);
					listener.read(row.getRowNum() + 1, cell.getCellNum() + 1,cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					break;
				default:
					break;
				}
			}
			if (!checkContinue(listener)) {
				break;
			}			
		}
		// chamado o metodo de finalizacao
		checkForNewRowEventFinish(ctx, listener);
	}

	public void read(InputStream file, ExcelReaderListener listener, int aba)	throws IOException {
		fs = new POIFSFileSystem(file);
		wb = new HSSFWorkbook(fs);
		sheet = wb.getSheetAt(aba);
		ctx = new Context();

		// Iterate over each row in the sheet
		rows = sheet.rowIterator();
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();			
			checkForNewRowEvent(ctx, listener, row);
			// Iterate over each cell in the row and print out the cell's content
			for (short i = 0; i <= row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				if ((cell == null)	|| (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)	|| (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR)) {
					continue;
				}
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					continue;
				case HSSFCell.CELL_TYPE_NUMERIC:
					// checkForNewRowEvent(ctx, listener, row);
					listener.read(row.getRowNum() + 1, cell.getCellNum() + 1,cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					// checkForNewRowEvent(ctx, listener, row);
					String value = cell.getStringCellValue().trim();
					if (value.equals("")) {
						continue;
					}
					try {
						listener.read(row.getRowNum() + 1,cell.getCellNum() + 1, new Double(value)
										.doubleValue());
					} catch (NumberFormatException e) {
						try {
							listener.read(row.getRowNum() + 1, cell.getCellNum() + 1, new Double(value.replace(',', '.')).doubleValue());
						} catch (NumberFormatException e2) {
							listener.read(row.getRowNum() + 1, cell.getCellNum() + 1, value);
						}
					}
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					// checkForNewRowEvent(ctx, listener, row);
					listener.read(row.getRowNum() + 1, cell.getCellNum() + 1,cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					break;
				default:
					break;
				}
			}
			if (!checkContinue(listener)) {
				break;
			}			
		}
		// chamado o metodo de finalizacao
		checkForNewRowEventFinish(ctx, listener);
	}

	private void checkForNewRowEvent(Context ctx, ExcelReaderListener listener,
			HSSFRow row) {
		if ((listener instanceof NewRowEventSupport)&& (ctx.lastRowNum != row.getRowNum())) {
			((NewRowEventSupport) listener).newRow(ctx.lastRowNum + 1, row.getRowNum() + 1);
			ctx.lastRowNum = row.getRowNum();
		}
	}

	private void checkForNewRowEventFinish(Context ctx,
			ExcelReaderListener listener) {
		if ((listener instanceof NewRowEventSupport)) {
			((NewRowEventSupport) listener).finished(ctx.lastRowNum + 1);
		}
	}

	private boolean checkContinue(ExcelReaderListener listener) {
		if ((listener instanceof NewRowEventSupport)) {
			return ((NewRowEventSupport) listener).continua();
		}
		return true;
	}

	private class Context {
		public int lastRowNum = -1;
	}
}
