package assegnazione.ore.service;

import assegnazione.ore.entity.Location;
import assegnazione.ore.entity.dto.ExcelDTO;
import assegnazione.ore.entity.dto.TableDTO;
import assegnazione.ore.repository.HourWorkedRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {


    @Autowired
    HourWorkedRepository hourWorkedRepository;


    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private ExcelDTO excelDTO;

    public ExcelService(ExcelDTO excelDTO) {
        this.excelDTO = excelDTO;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Consultivo_mese_" + excelDTO.getListTableDTO().get(0).getMonth());

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Giorno:", style);
        createCell(row, 1, "Numero:", style);
        createCell(row, 2, "Ore: ", style);
        createCell(row, 3, "Luogo: ", style);
        createCell(row, 4, "Totale ore lavorate:", style);
        createCell(row, 5, "Totale giorni lavorati:", style);
        createCell(row, 6, "Totale ore nel mese:", style);
        createCell(row, 7, "Totale giorni nel mese:", style);
        if (excelDTO.getGetIllnessDay() != 0) {
            createCell(row, 8, "Giorni malattia:", style);
        }


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Location) {
            cell.setCellValue(Location.valueOf(value.toString()).ordinal());

    } else

    {
        cell.setCellValue((String) value);
    }
        cell.setCellStyle(style);
}

private void writeDataLines() {
    int rowCount = 1;

    CellStyle style = workbook.createCellStyle();
    XSSFFont font = workbook.createFont();
    font.setFontHeight(14);
    style.setFont(font);


    for (TableDTO tableDTO : excelDTO.getListTableDTO()) {
        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;

        createCell(row, columnCount++, tableDTO.getDay(), style);
        createCell(row, columnCount++, tableDTO.getNumber() + "/" + tableDTO.getMonth(), style);
        createCell(row, columnCount++, tableDTO.getHour(), style);
        createCell(row, columnCount++, tableDTO.getPlace().toString(), style);
    }


    Row totalRow = sheet.createRow(rowCount);
    createCell(totalRow, 4, excelDTO.getGetTotalHourWorked(), style);
    createCell(totalRow, 5, excelDTO.getGetWorkedDay(), style);
    createCell(totalRow, 6, excelDTO.getGetTotalHoursForMonth(), style);
    createCell(totalRow, 7, excelDTO.getGetTotalDayForMonth(), style);
    if (excelDTO.getGetIllnessDay() != 0) {
        createCell(totalRow, 8, excelDTO.getGetIllnessDay(), style);
    }

}

public void export(HttpServletResponse response) throws IOException {
    writeHeaderLine();
    writeDataLines();

    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    workbook.close();

    outputStream.close();

}

public ExcelDTO getValues(String month) {

    List<TableDTO> listTableDto = hourWorkedRepository.getTableValue(month);
    Double getTotalHoursForMonth = hourWorkedRepository.getTotalHoursForMonth(month);
    Double getWorkedDay = hourWorkedRepository.getWorkedDay();
    Integer getTotalDayForMonth = hourWorkedRepository.getTotalDayForMonth();
    Double getIllnessDay = hourWorkedRepository.getIllnessDay();
    Double getTotalHourWorked = hourWorkedRepository.getTotalHourWorked(month);

    return ExcelDTO.builder()
            .listTableDTO(listTableDto)
            .getIllnessDay(getIllnessDay)
            .getTotalHoursForMonth(getTotalHoursForMonth)
            .getWorkedDay(getWorkedDay)
            .getTotalDayForMonth(getTotalDayForMonth)
            .getTotalHourWorked(getTotalHourWorked)
            .build();
}
}


