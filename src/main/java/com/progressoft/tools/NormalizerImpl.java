package com.progressoft.tools;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class NormalizerImpl implements Normalizer {

    public static void main(String[] args) {
        NormalizerImpl dataNormalizer = new NormalizerImpl();
        if (args[3].equals("min-max"))
            dataNormalizer.minMaxScaling(Paths.get(args[0]),Paths.get(args[1]),args[2]);
        else
            dataNormalizer.zscore(Paths.get(args[0]), Paths.get(args[1]),args[2]);
    }


    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) throws NullPointerException {
        //check null values
        if (csvPath==null|| colToStandardize == null || destPath == null) {
            throw new IllegalArgumentException("source file not found");
        }

        //Get All Lines inside CSV
        List<List<String>> lines =getLinesInCsv(csvPath);

        //Check if column name is exist in the fist line(titles)
        if (!lines.get(0).contains(colToStandardize)) {
            throw new IllegalArgumentException("column " + colToStandardize + " not found");
        }

        //Get the values in the column without the name of it
        int indexOfColumn = lines.get(0).indexOf(colToStandardize);
        List<String> valuesOfColToStandardize = new ArrayList();
        valuesOfColToStandardize = lines.stream().map(l -> l.get(indexOfColumn)).collect(Collectors.toList());
        valuesOfColToStandardize.remove(0);

        //Convert the values from String to BigDecimal
        List<BigDecimal> valuesInsideCol = new ArrayList();
        for (String value : valuesOfColToStandardize) {
            valuesInsideCol.add(new BigDecimal(value, new MathContext(Integer.parseInt(value))));
        }

        //Apply scoringSummary on the values inside column
        ScoringSummary scoringSummary = new ScoringSummaryImpl(valuesInsideCol);


        //Apply Z-Score Normalization on values
        // The formula for Z-score normalization is : (value-mean)/standardDeviation
        List<BigDecimal> valuesAfterScaling = valuesInsideCol.stream().map(value -> ((value.subtract(scoringSummary.mean())).divide(scoringSummary.standardDeviation(), BigDecimal.ROUND_HALF_EVEN))).collect(Collectors.toList());

        //Add column with this form:colToStandardize_z
        String colAfterStandardize = lines.get(0).get(indexOfColumn) + "_" + "z";
        lines.get(0).add(indexOfColumn + 1, colAfterStandardize);


        //fill the lines with value after Z-Score Normalization
        for (int l = 1; l < lines.size(); l++) {
            lines.get(l).add(indexOfColumn + 1, valuesAfterScaling.get(0).toString());
            valuesAfterScaling.remove(0);
        }

        //Fill the destination file
        setLinesInCsv(destPath,lines);

        // return the scoring calculations
        return scoringSummary;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {

        //check null values
        if (csvPath==null || colToNormalize == null || destPath == null) {
            throw new IllegalArgumentException("source file not found");
        }

        //Get All Lines inside CSV
        List<List<String>> lines =getLinesInCsv(csvPath);

        //Check if column name is exist in the fist line(titles)
        if (!lines.get(0).contains(colToNormalize)) {
            throw new IllegalArgumentException("column " + colToNormalize + " not found");
        }


        //Get the values in the column without the name of it
        int indexOfColomn = lines.get(0).indexOf(colToNormalize);
        List<String> valuesOfColToStandardize = new ArrayList();
        valuesOfColToStandardize = lines.stream().map(l -> l.get(indexOfColomn)).collect(Collectors.toList());
        valuesOfColToStandardize.remove(0);


        //Convert the values from String to BigDecimal
        List<BigDecimal> valuesOfCol = new ArrayList();
        for (String value : valuesOfColToStandardize) {
            valuesOfCol.add(new BigDecimal(value, new MathContext(Integer.parseInt(value))));
        }

        //Apply scoringSummary on the values inside column
        ScoringSummary scoringSummary = new ScoringSummaryImpl(valuesOfCol);


        //Apply Min-Max Normalization Normalization on values
        // The formula for Min-Max Normalization normalization is : (value-min)/(max-min)
        List<BigDecimal> valuesAfterNormalize = valuesOfCol.stream().map(value -> ((value.subtract(scoringSummary.min())).divide((scoringSummary.max().subtract(scoringSummary.min())), BigDecimal.ROUND_HALF_EVEN))).collect(Collectors.toList());

        //Add column with this form: colToNormalize_mm
        String colAfterNormalize = lines.get(0).get(indexOfColomn) + "_" + "mm";
        lines.get(0).add(indexOfColomn + 1, colAfterNormalize);


        //fill the lines with value after Min-Max Normalization
        for (int l = 1; l < lines.size(); l++) {
            lines.get(l).add(indexOfColomn + 1, valuesAfterNormalize.get(0).toString());
            valuesAfterNormalize.remove(0);
        }

        //Fill the destination file
        setLinesInCsv(destPath,lines);

        // return the scoring calculations
        return scoringSummary;
    }


    public List<List<String>> getLinesInCsv(Path csvPath){
        String delimiter = ",";
        String line;
        List<List<String>> lines = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvPath.toFile()));
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<String>(Arrays.asList(line.split(delimiter)));
                lines.add(values);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public void setLinesInCsv(Path destPath,List<List<String>> lines){


        try {
            FileWriter file = new FileWriter(destPath.toFile());
            PrintWriter write = new PrintWriter(file);
            for (List<String> lineWrite : lines) {
                write.println(String.join(",", lineWrite));
            }
            write.close();
        } catch (IOException exe) {
            System.out.println("Cannot create file");
        }

    }
}
