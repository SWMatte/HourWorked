package assegnazione.ore.service.impl;

import assegnazione.ore.entity.Location;
import assegnazione.ore.entity.dto.ExcelDTO;
import assegnazione.ore.entity.dto.TableDTO;
import assegnazione.ore.entity.dto.UserDTO;
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
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

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

    private void writeHeaderLine(UserDTO userDTO) {
        sheet = workbook.createSheet("Consultivo_mese_" + excelDTO.getListTableDTO().get(0).getMonth());

        // Crea la prima riga di intestazione
        Row titleRow = sheet.createRow(0);

        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeight(14);
        titleStyle.setFont(titleFont);

        createCell(titleRow, 0, "Consultivo di: " +userDTO.getName()  +" "+userDTO.getLastName(), titleStyle);
        createCell(titleRow, 1, "Mese di : "+ userDTO.getNameMonth()+" "+excelDTO.getListTableDTO().get(0).getMonth()+" / "+excelDTO.getListTableDTO().get(0).getYear(), titleStyle);

        // Aggiungi una riga vuota
        sheet.createRow(1);

        // Crea la seconda riga di intestazione
        Row headerRow = sheet.createRow(2);

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(16);
        headerStyle.setFont(headerFont);

        createCell(headerRow, 0, "Giorno:", headerStyle);
        createCell(headerRow, 1, "Numero:", headerStyle);
        createCell(headerRow, 2, "Ore:", headerStyle);
        createCell(headerRow, 3, "Luogo:", headerStyle);
        createCell(headerRow, 4, "Staordinario:", headerStyle);
        createCell(headerRow, 5, "Totale ore lavorate:", headerStyle);
        createCell(headerRow, 6, "Totale giorni lavorati:", headerStyle);
        createCell(headerRow, 7, "Totale ore nel mese:", headerStyle);
        createCell(headerRow, 8, "Totale giorni nel mese:", headerStyle);
        if (excelDTO.getGetIllnessDay() != 0) {
            createCell(headerRow, 9, "Giorni malattia:", headerStyle);
        }

        if (excelDTO.getGetTotalExtraWork() != 0) {
            createCell(headerRow, 10, "Ore di straordinario", headerStyle);
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
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 3; // Inizia a scrivere i dati dalla quarta riga

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
            createCell(row, columnCount++, tableDTO.getExtraWork(), style);
        }

        Row totalRow = sheet.createRow(rowCount);
        createCell(totalRow, 4, excelDTO.getGetTotalExtraWork(), style);
        createCell(totalRow, 5, excelDTO.getGetTotalHourWorked(), style);
        createCell(totalRow, 6, excelDTO.getGetWorkedDay(), style);
        createCell(totalRow, 7, excelDTO.getGetTotalHoursForMonth(), style);
        createCell(totalRow, 8, excelDTO.getGetTotalDayForMonth(), style);
        if (excelDTO.getGetIllnessDay() != 0) {
            createCell(totalRow, 9, excelDTO.getGetIllnessDay(), style);
        }

        if (excelDTO.getGetTotalExtraWork() != 0) {
            createCell(totalRow, 10, excelDTO.getGetTotalExtraWork(), style);
        }
    }

    public void export(HttpServletResponse response, UserDTO userDTO) throws IOException {
        writeHeaderLine(userDTO);
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public ExcelDTO getValues(String month) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        List<TableDTO> listTableDto = hourWorkedRepository.getTableValue(month);
        Double getTotalHoursForMonth = hourWorkedRepository.getTotalHoursForMonth(month);
        Double getWorkedDay = hourWorkedRepository.getWorkedDay(month);
        Integer getTotalDayForMonth = hourWorkedRepository.getTotalDayForMonth(month);
        Double getIllnessDay = hourWorkedRepository.getIllnessDay();
        Double getTotalHourWorked = hourWorkedRepository.getTotalHourWorked(month);
        Double getTotalExtraWork = hourWorkedRepository.getTotalExtraWork(month);
        return ExcelDTO.builder()
                .listTableDTO(listTableDto)
                .getIllnessDay(getIllnessDay)
                .getTotalHoursForMonth(getTotalHoursForMonth)
                .getWorkedDay(getWorkedDay)
                .getTotalDayForMonth(getTotalDayForMonth)
                .getTotalHourWorked(getTotalHourWorked)
                .getTotalExtraWork(getTotalExtraWork)
                .build();
    }

    public UserDTO getUserInfo(String month){

        String month1 = new DateFormatSymbols(Locale.ITALIAN).getMonths()[Integer.parseInt(month) - 1];

        if (month.length() == 1) {
            month = "0" + month;
        }
        UserDTO userDTO = hourWorkedRepository.getUserDTO(month);
        return UserDTO.builder()
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .nameMonth(month1)
                .build();
    }
}
