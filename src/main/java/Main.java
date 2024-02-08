import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import java.util.List;

/*
В задаче вам предстоит произвести запись в файл JSON объекта, полученного из CSV файла
 */
public class Main {
    public static void main(String[] args) {
        // информация о предназначении колонок в CVS файле
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        // определите имя для считываемого CSV файла
        String fileName = "data.csv";

        //получите список сотрудников, вызвав метод parseCSV()
        List<Employee> list = parseCSV(columnMapping, fileName);

        //Полученный список преобразуйте в строчку в формате JSON
        String json = listToJson(list);

        //запишите полученный JSON в файл с помощью метода writeString()
        String fileNameJson = "data.json";
        writeString(json, fileNameJson);
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            staff = csv.parse();
            staff.forEach(System.out::println);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return staff;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);

        return json;
    }

    public static void writeString(String json, String fileNameJson) {
        //поможет FileWriter и его метод write()
        try (FileWriter file = new FileWriter(fileNameJson)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
