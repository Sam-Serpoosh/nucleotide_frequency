import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NucleotideOccurances {
  private static final String ADENINE  = "A";
  private static final String THYMINE  = "T";
  private static final String CYTOSINE = "C";
  private static final String GUANINE  = "G";

  private String _directory;
  private Map<String, Integer> _nucleotidesCount;

  public NucleotideOccurances(String directory) {
    _directory = directory;
    _nucleotidesCount = new HashMap<String, Integer>();
    _nucleotidesCount.put(ADENINE, 0);
    _nucleotidesCount.put(THYMINE, 0);
    _nucleotidesCount.put(CYTOSINE, 0);
    _nucleotidesCount.put(GUANINE, 0);
  }

  public Map<String, Integer> countOccurancesOfNucleotides() throws IOException {
    List<String> fileNames = listAllFilesInDirectory();
    for (String file : fileNames)
      addNumberOfEachNucleotidesToItsTotal(file);

    return _nucleotidesCount;
  }

  private List<String> listAllFilesInDirectory() { 
    File dir = new File(_directory);
    File[] allFiles = dir.listFiles();
    List<String> fileNames = new ArrayList<String>();
    for (File f : allFiles)
      fileNames.add(f.getName());

    return fileNames;
  }

  private void addNumberOfEachNucleotidesToItsTotal(String file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(_directory + "/" + file));
    String line;
    while((line = reader.readLine()) != null)
      addNumberOfEachNucleotideInLineToItsTotal(line);
    reader.close();
  }

  private void addNumberOfEachNucleotideInLineToItsTotal(String line) {
    for (int i = 0; i < line.length(); i++) {
      String currentChar = String.valueOf(line.charAt(i));
      switch(currentChar) {
        case ADENINE:
          _nucleotidesCount.put(ADENINE, _nucleotidesCount.get(ADENINE) + 1);
          break;
        case THYMINE:
          _nucleotidesCount.put(THYMINE, _nucleotidesCount.get(THYMINE) + 1);
          break;
        case CYTOSINE:
          _nucleotidesCount.put(CYTOSINE, _nucleotidesCount.get(CYTOSINE) + 1);
          break;
        case GUANINE:
          _nucleotidesCount.put(GUANINE, _nucleotidesCount.get(GUANINE) + 1);
          break;
      }
    }
  }

}
