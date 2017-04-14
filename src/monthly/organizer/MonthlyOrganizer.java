/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.StageStyle;
import static monthly.organizer.Client.behaviors;


public class MonthlyOrganizer extends Application {
	static File selectedFolder;
        private Client client;
        int clientNum = -1;
	Workbook wb;
	Workbook wbo;
	File outputFile;
	int fileNum = 0;
	FileOutputStream outputStream;
	@Override
	public void start(Stage primaryStage) {
		wbo = new XSSFWorkbook();
                try {
                        Button merge = new Button("merge");
                        Button folder = new Button("Choose Folder");
			Button file = new Button("Choose Save File");
                        Label fileLoc = new Label("Not Selected");
                        Label folderLoc = new Label("Not Selected");
                        folder.setOnAction(new EventHandler<ActionEvent>(){
				
				@Override
				public void handle(ActionEvent arg0) {
                                    DirectoryChooser folderChooser = new DirectoryChooser();
				    folderChooser.setTitle("Choose Folder Containing Files");
                                    selectedFolder = folderChooser.showDialog(primaryStage);
                                    //folderLoc.setText(selectedFolder.getName());
                                    if(null == selectedFolder)
                                                folderLoc.setText("No Folder Selected");
                                            else
                                                folderLoc.setText(outputFile.getName());
                                }
                                
                        });
                        
			file.setOnAction(new EventHandler<ActionEvent>(){
				
				@SuppressWarnings("deprecation")
				@Override
				public void handle(ActionEvent arg0) {
					
                                            FileChooser fileSaver = new FileChooser();
                                            fileSaver.setTitle("Create Save File");
                                            fileSaver.getExtensionFilters().addAll(
                                                    new ExtensionFilter("ExceBeaconController bc = new BeaconController();ll Files", "*.xlsx"));
                                            
                                            outputFile = fileSaver.showSaveDialog(primaryStage);
                                            //outputFile = new File("/home/camen/Desktop" + "/Final_Books" + LocalDate.now() + ".xlsx");
                                            if(null == outputFile)
                                                fileLoc.setText("No File Selected");
                                            else
                                                fileLoc.setText(outputFile.getName());
                                }
                        });
                        
                        merge.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent arg0){ 
                                        BeaconController bc = new BeaconController();
                                            try {
                                                outputStream = new FileOutputStream(outputFile);
                                            } catch (FileNotFoundException e2) {
                                                // TODO Auto-generated catch block
                                                e2.printStackTrace();
                                            }
                                            
                                            try(
                                                    
                                                    Stream<Path> paths = Files.walk(Paths.get(selectedFolder.getPath()))) {
                                                paths.forEach(filePath -> {
                                                    if (Files.isRegularFile(filePath) && !filePath.toString().equals(selectedFolder.toPath().toString())) {
                                                        System.out.println(filePath);
                                                        fileNum++;
                                                        FileInputStream inputStream;
                                                        
                                                        try {
                                                            inputStream = new FileInputStream(filePath.toString());
                                                            
                                                            wb = new XSSFWorkbook(inputStream);
                                                            
                                                            CreationHelper createHelper = wbo.getCreationHelper();
                                                            Sheet sheetOut = wbo.createSheet(filePath.getFileName().toString());
                                                            //add client name to beacon controller
                                                            client = (new Client(filePath.getFileName().toString()));
                                                            bc.addClient(client);
                                                            clientNum ++;
                                                            Row rowOut = sheetOut.createRow(0);
                                                            Cell cellOut;
                                                            cellOut = rowOut.createCell(0);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Week"));
                                                            cellOut = rowOut.createCell(1);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Behavior/Decel"));
                                                            cellOut = rowOut.createCell(2);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Data Input Total"));
                                                            cellOut = rowOut.createCell(3);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Measurment Type"));
                                                            cellOut = rowOut.createCell(4);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Measurment Unit"));
                                                            cellOut = rowOut.createCell(5);
                                                            cellOut.setCellValue(createHelper.createRichTextString("Total Time for Week (Mins)"));
                                                            int rowCount = 1;
                                                            for (int k = 0; k < wb.getNumberOfSheets(); k++) {
                                                                Sheet sheet = wb.getSheetAt(k);
                                                                double weekTotal = 0;
                                                                double timeTotal = 0;
                                                                boolean replace = false;
                                                                String decelName = null;
                                                                String replaceName = null;
                                                                String measType = null;
                                                                String measUnit = null;
                                                                String replacedName = null;
                                                                String replacedNameOld = null;
                                                                LocalDateTime date = null;
                                                                int weekNum = -1;
                                                                int lastWeek = -1;
                                                                int year = 0;
                                                                int rows = sheet.getPhysicalNumberOfRows();
                                                                //System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows
                                                                //+ " row(s).");
                                                                for (int r = 0; r < rows; r++) {
                                                                    
                                                                    Row row = sheet.getRow(r);
                                                                    if (row == null) {
                                                                        continue;
                                                                    }
                                                                    
                                                                    int cells = row.getPhysicalNumberOfCells();
                                                                    //System.out.println("\nROW " + row.getRowNum() + " has " + cells
                                                                    //+ " cell(s).");
                                                                    for (int c = 0; c < cells; c++) {
                                                                        Cell cell = row.getCell(c);
                                                                        if(cell == null)
                                                                        {
                                                                            //c++;
                                                                            cells++;
                                                                        }
                                                                        else{
                                                                            switch (cell.getCellTypeEnum()) {
                                                                                case STRING:
                                                                                    if(r == 0 && cell.getRichStringCellValue().getString().contains("Replacement"))
                                                                                        replace = true;
                                                                                    if(r == 0 && replace == true && c == 1)
                                                                                        replaceName = cell.getRichStringCellValue().getString();
                                                                                    else if(r == 0 && replace == false && c == 1)
                                                                                        decelName = cell.getRichStringCellValue().getString();
                                                                                    else if(r == 0 && replace == true && c == 2)
                                                                                        replacedName = cell.getRichStringCellValue().getString();
                                                                                    if(r == 1 && c == 0)
                                                                                        measType = cell.getRichStringCellValue().getString();
                                                                                    if(r == 1 && c == 1)
                                                                                        measUnit = cell.getRichStringCellValue().getString();
                                                                                    //System.out.println(cell.getRichStringCellValue().getString());
                                                                                    break;
                                                                                case NUMERIC:
                                                                                    if (DateUtil.isCellDateFormatted(cell)) {
                                                                                        date = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(LocalTime.NOON);
                                                                                        year = date.getYear();
                                                                                        weekNum = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                                                                                        if(lastWeek == -1)
                                                                                            lastWeek = weekNum;
                                                                                        c++;
                                                                                        cell = row.getCell(c);
                                                                                        weekTotal += cell.getNumericCellValue();
                                                                                        c++;
                                                                                        cell = row.getCell(c);
                                                                                        timeTotal += cell.getNumericCellValue();
                                                                                    } else {
                                                                                        System.out.println(cell.getNumericCellValue());
                                                                                        
                                                                                    }
                                                                                    break;
                                                                                case BOOLEAN:
                                                                                    //System.out.println(cell.getBooleanCellValue());
                                                                                    break;
                                                                                case FORMULA:
                                                                                    //System.out.println(cell.getCellFormula());
                                                                                    break;
                                                                                case BLANK:
                                                                                    //System.out.println();
                                                                                    break;
                                                                                default:
                                                                                    //System.out.println();
                                                                            }
                                                                            
                                                                            //System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE="
                                                                            //+ value);
                                                                        }
                                                                    }//System.out.println(lastWeek + " " + weekNum + " " + weekTotal);
                                                                    if(lastWeek != weekNum && lastWeek != -1)
                                                                    {
                                                                        
                                                                        //weekCount ++;
                                                                        rowOut	= sheetOut.createRow(rowCount);
                                                                        Cell cell = rowOut.createCell(0);
                                                                        cell.setCellValue(createHelper.createRichTextString(date.getMonth() + ", Week " + weekNum + " of Year: " + date.getYear()));

                                                                        cell = rowOut.createCell(1);
                                                                        if(replace == true){
                                                                             cell.setCellValue(createHelper.createRichTextString(replaceName));
                                                                             client.addValue(weekNum, weekTotal, Arrays.binarySearch(Client.behaviors, replaceName));
                                                                             
                                                                        }
                                                                           
                                                                        else
                                                                            cell.setCellValue(createHelper.createRichTextString(decelName));
                                                                        cell = rowOut.createCell(2);
                                                                        cell.setCellValue(weekTotal);
                                                                        cell = rowOut.createCell(3);
                                                                        cell.setCellValue(createHelper.createRichTextString(measType));
                                                                        cell = rowOut.createCell(4);
                                                                        cell.setCellValue(createHelper.createRichTextString(measUnit));
                                                                        //System.out.println(months[wR[weekNum].month-1] + " week " + (wR[weekNum].monthWeek) + ", " + (year+1900) + ": " + weekTotal + " Measurment Type: " + measType + " Measument Unit: " + measUnit);
                                                                        weekTotal = 0;
                                                                        lastWeek = weekNum;
                                                                        //newWeek = false;
                                                                        rowCount++;
                                                                    }
                                                                    
                                                                }
                                                                //if(replace == true)
                                                                //System.out.println(replaceName + " for " + replacedName);
                                                                //else
                                                                //System.out.println(decelName);
                                                            }
                                                            
                                                            
                                                            wb.close();
                                                            
                                                            inputStream.close();
                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                                wbo.write(outputStream);
                                                wbo.close();
                                                outputStream.close();
                                                final FXMLLoader loader = new FXMLLoader(getClass().getResource("beacon.fxml"));
                                                final Stage stage = new Stage(StageStyle.DECORATED);
                                                stage.setScene(new Scene((Pane) loader.load()));
                                                stage.showAndWait();
                                                //getHostServices().showDocument(outputFile.toURI().toURL().toExternalForm());
                                            } catch (IOException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                            }
                                    }
				
			});
			
			 
			VBox root = new VBox();
                        root.setId("pane");
                        folder.setId("button");
                        file.setId("button");
                        merge.setId("button");
                        root.getStylesheets().addAll(this.getClass().getResource("buttonStyle.css").toExternalForm());
                        root.setPadding(new Insets(180,200,30,200));
                        root.setAlignment(Pos.BASELINE_CENTER);
			root.getChildren().addAll(folder, folderLoc, file, fileLoc, merge);
                        root.setSpacing(10);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

                    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
		
    }
	
}