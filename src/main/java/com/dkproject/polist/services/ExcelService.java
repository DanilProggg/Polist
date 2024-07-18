package com.dkproject.polist.services;


import com.dkproject.polist.entities.*;
import com.dkproject.polist.repos.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExcelService {
    private final PareRepo pareRepo;
    private final DisciplineRepo disciplineRepo;
    private final TeacherRepo teacherRepo;
    private final ClassroomRepo classroomRepo;
    private final GroupRepo groupRepo;

    public ExcelService(PareRepo pareRepo, DisciplineRepo disciplineRepo, TeacherRepo teacherRepo, ClassroomRepo classroomRepo, GroupRepo groupRepo) {
        this.pareRepo = pareRepo;
        this.disciplineRepo = disciplineRepo;
        this.teacherRepo = teacherRepo;
        this.classroomRepo = classroomRepo;
        this.groupRepo = groupRepo;
    }

    @Value("${upload.path}") String uploadPath;

    public ResponseEntity<ByteArrayResource> writeIntoExcel(java.sql.Date from_date, java.sql.Date to_date, Long group_id) throws IOException {
            Group group = groupRepo.findById(group_id).orElseThrow(() -> new RuntimeException("Группа не найдена"));
            String file = "Report.xls";
            List<Pare> pares = pareRepo.findWeekByGroupId(group_id, from_date, to_date);
            TreeMap<String, LinkedList<Pare>> paresMap = new TreeMap<>();
            pares.forEach(pare -> {
                if (paresMap.containsKey(pare.getDate().toString())) {
                    LinkedList<Pare> temp = new LinkedList<>(paresMap.get(pare.getDate().toString()));
                    temp.addLast(pare);
                    paresMap.replace(pare.getDate().toString(), temp);
                } else {
                    LinkedList<Pare> temp = new LinkedList<>();
                    temp.add(pare);
                    paresMap.put(pare.getDate().toString(), temp);
                }
            });


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(group.getName() + " " + from_date.toString() + " - " + to_date.toString());


            XSSFCellStyle listStyle = workbook.createCellStyle();
            listStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            listStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFCellStyle subNumStyle = workbook.createCellStyle();
            subNumStyle.setAlignment(CellStyle.ALIGN_CENTER);
            subNumStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            subNumStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            subNumStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            subNumStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);


            XSSFCellStyle pareNumStyle = workbook.createCellStyle();
            pareNumStyle.setAlignment(CellStyle.ALIGN_CENTER);
            pareNumStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            pareNumStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            pareNumStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            pareNumStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);


            AtomicInteger counter = new AtomicInteger();
            paresMap.forEach((key, paresArray) -> {
                Row row0 = sheet.createRow(0 + counter.get());
                Row row1 = sheet.createRow(1 + counter.get());
                Row row2 = sheet.createRow(2 + counter.get());
                Row row3 = sheet.createRow(3 + counter.get());
                Row row4 = sheet.createRow(4 + counter.get());
                Row row5 = sheet.createRow(5 + counter.get());
                Row row6 = sheet.createRow(6 + counter.get());
                Row row7 = sheet.createRow(7 + counter.get());
                Row row8 = sheet.createRow(8 + counter.get());
                Row row9 = sheet.createRow(9 + counter.get());

                Cell date = row0.createCell(0);
                DataFormat format = workbook.createDataFormat();
                XSSFCellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
                dateStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
                dateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                dateStyle.setAlignment(HorizontalAlignment.CENTER);

                date.setCellStyle(dateStyle);
                date.setCellValue(paresArray.get(0).getDate());

                //Объеденение ячеек даты
                sheet.addMergedRegion(new CellRangeAddress(counter.get(), counter.get(), 0, 5));

                //Border для даты
                CellRangeAddress area = new CellRangeAddress(row0.getRowNum(), row0.getRowNum(), 0, 5);
                setRegionBorderWithMedium(area, sheet, workbook);


                boolean isSub = false;
                for (int i = 0; i < paresArray.size(); i++) {
                    //Номер пары
                    Cell pareNum = row1.createCell(paresArray.get(i).getNumber() - 1);
                    pareNum.setCellStyle(pareNumStyle);
                    //Border для подгруппы и номера пары
                    CellRangeAddress pareBorderArea = new CellRangeAddress(row1.getRowNum(), row2.getRowNum(), paresArray.get(i).getNumber() - 1, paresArray.get(i).getNumber() - 1);
                    setRegionBorderWithMedium(pareBorderArea, sheet, workbook);

                    pareNum.setCellValue("Пара " + paresArray.get(i).getNumber());

                    if (paresArray.get(i).isSub()) {

                        isSub = true;
                        //Проверка на подгруппы
                        if (paresArray.get(i).getSubgroup() == 1) {

                            Cell subNum1 = row2.createCell(paresArray.get(i).getNumber() - 1);
                            subNum1.setCellStyle(subNumStyle);
                            subNum1.setCellValue("Подгруппа " + paresArray.get(i).getSubgroup());

                            Cell disciplineCell = row3.createCell(paresArray.get(i).getNumber() - 1);
                            Discipline discipline = disciplineRepo.findById(paresArray.get(i).getDiscipline_id()).orElseThrow(() -> new RuntimeException("Дисцпилина не найдена"));
                            disciplineCell.setCellStyle(listStyle);
                            disciplineCell.setCellValue(discipline.getName());

                            Cell teacherCell = row4.createCell(paresArray.get(i).getNumber() - 1);
                            Teacher teacher = teacherRepo.findById(paresArray.get(i).getTeacher_id()).orElseThrow(() -> new RuntimeException("Преподаватель не найден"));
                            teacherCell.setCellStyle(listStyle);
                            teacherCell.setCellValue(teacher.getName());

                            Cell classroomCell = row5.createCell(paresArray.get(i).getNumber() - 1);
                            Classroom classroom = classroomRepo.findById(paresArray.get(i).getClassroom_id()).orElseThrow(() -> new RuntimeException("Аудитория не найдена"));
                            classroomCell.setCellStyle(listStyle);
                            classroomCell.setCellValue(classroom.getNumber());

                            //Self Border для одной пары 1 подгруппы
                            CellRangeAddress self = new CellRangeAddress(row3.getRowNum(), row5.getRowNum(), paresArray.get(i).getNumber() - 1, paresArray.get(i).getNumber() - 1);
                            setRegionBorderWithMedium(self, sheet, workbook);


                        } else {

                            Cell subNum2 = row6.createCell(paresArray.get(i).getNumber() - 1);
                            subNum2.setCellStyle(subNumStyle);
                            subNum2.setCellValue("Подгруппа " + paresArray.get(i).getSubgroup());

                            Cell disciplineCell = row7.createCell(paresArray.get(i).getNumber() - 1);
                            Discipline discipline = disciplineRepo.findById(paresArray.get(i).getDiscipline_id()).orElseThrow(() -> new RuntimeException("Дисцпилина не найдена"));
                            disciplineCell.setCellStyle(listStyle);
                            disciplineCell.setCellValue(discipline.getName());

                            Cell teacherCell = row8.createCell(paresArray.get(i).getNumber() - 1);
                            Teacher teacher = teacherRepo.findById(paresArray.get(i).getTeacher_id()).orElseThrow(() -> new RuntimeException("Преподаватель не найден"));
                            teacherCell.setCellStyle(listStyle);
                            teacherCell.setCellValue(teacher.getName());

                            Cell classroomCell = row9.createCell(paresArray.get(i).getNumber() - 1);
                            Classroom classroom = classroomRepo.findById(paresArray.get(i).getClassroom_id()).orElseThrow(() -> new RuntimeException("Аудитория не найдена"));
                            classroomCell.setCellStyle(listStyle);
                            classroomCell.setCellValue(classroom.getNumber());

                            //Self Border для одной пары 2 подгруппы
                            CellRangeAddress self = new CellRangeAddress(row7.getRowNum(), row9.getRowNum(), paresArray.get(i).getNumber() - 1, paresArray.get(i).getNumber() - 1);
                            setRegionBorderWithMedium(self, sheet, workbook);

                            //Border на всю страницу при наличии 2 подгруппы
                            CellRangeAddress main = new CellRangeAddress(row1.getRowNum(), row9.getRowNum(), 0, 5);
                            setRegionBorderWithMedium(main, sheet, workbook);

                        }

                    } else {
                        //Если подгруппы отсутсвуют
                        Cell disciplineCell = row3.createCell(paresArray.get(i).getNumber() - 1);
                        Discipline discipline = disciplineRepo.findById(paresArray.get(i).getDiscipline_id()).orElseThrow(() -> new RuntimeException("Дисцпилина не найдена"));
                        disciplineCell.setCellStyle(listStyle);
                        disciplineCell.setCellValue(discipline.getName());

                        Cell teacherCell = row4.createCell(paresArray.get(i).getNumber() - 1);
                        Teacher teacher = teacherRepo.findById(paresArray.get(i).getTeacher_id()).orElseThrow(() -> new RuntimeException("Преподаватель не найден"));
                        teacherCell.setCellStyle(listStyle);
                        teacherCell.setCellValue(teacher.getName());

                        Cell classroomCell = row5.createCell(paresArray.get(i).getNumber() - 1);
                        Classroom classroom = classroomRepo.findById(paresArray.get(i).getClassroom_id()).orElseThrow(() -> new RuntimeException("Аудитория не найдена"));
                        classroomCell.setCellStyle(listStyle);
                        classroomCell.setCellValue(classroom.getNumber());

                        //Border для пар
                        CellRangeAddress main = new CellRangeAddress(row1.getRowNum(), row5.getRowNum(), 0, 5);
                        setRegionBorderWithMedium(main, sheet, workbook);

                        //Self Border для одной при отсутствии подгрупп
                        CellRangeAddress self = new CellRangeAddress(row3.getRowNum(), row5.getRowNum(), paresArray.get(i).getNumber() - 1, paresArray.get(i).getNumber() - 1);
                        setRegionBorderWithMedium(self, sheet, workbook);
                    }
                }
                if (isSub) {
                    counter.set(counter.get() + 11);
                } else {
                    counter.set(counter.get() + 7);
                }
            });

            // Меняем размер столбца
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);

            // Записываем всё в файл
            File excel = new File(uploadPath + file);

            workbook.write(new FileOutputStream(excel));
            workbook.close();

            Path path = Paths.get(excel.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" +  new String(file.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)+"\"");
//            headers.add(HttpHeaders.TRANSFER_ENCODING, "binary");
            return  ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(excel.length())

    //                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

    }
    private void setRegionBorderWithMedium(CellRangeAddress region, Sheet sheet, Workbook wb) {
        RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, region, sheet, wb);
        RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, region, sheet, wb);
        RegionUtil.setBorderRight(CellStyle.BORDER_THIN, region, sheet, wb);
        RegionUtil.setBorderTop(CellStyle.BORDER_THIN, region, sheet, wb);
    }

}
