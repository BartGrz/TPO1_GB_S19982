package zad1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Futil {

public static void processDir (String dirName,String resultFileName) {

    Path outputFilePath = Paths.get(resultFileName);
    Path startingDirectory = Paths.get(dirName);

    try {
        OwnFileVisitor fileVisitor = new OwnFileVisitor(outputFilePath);
        Files.walkFileTree(startingDirectory, fileVisitor);


    } catch (Exception e) {
        System.out.println(e);
    }
}

}
