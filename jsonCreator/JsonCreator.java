import java.io.*;
import java.util.*;

public class JsonCreator {
  public static void main(String[] args) {
    String[] departNames = getDeparts();
    String inFile = "./ProfClassReviews/";

    for (String dptNm : departNames) {
      String fn = inFile + dptNm;
      String dp = dptNm.substring(0, dptNm.length() - 4);
      createFiles(fn, dp);
    }
  }

  // inFile: input filename, dp: depart name
  // reads in the filename and create output files
  public static void createFiles(String inFile, String dp) {
    File f = new File(inFile);
    try {
      Scanner in1 = new Scanner(f);
      Scanner in2 = new Scanner(f);
    
      Set<String> profNames = getProfNames(in1);

      for (String nm : profNames) {
        outputFile(nm, dp, in2);
        in2 = new Scanner(f);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


  }

  // output one single file of one prof of one depart
  public static void outputFile(String prof, String dp, Scanner in) {
    String path = ".//outputFiles//";
    String fn = path + dp + "_" + prof.replaceAll("\\s", "") + ".js";
    File outF = new File(fn);
    
    // write
    try {
      FileWriter wr = new FileWriter(outF);
      String header = "var review = [";
      String footer = "];";

      String[] options = {
        "approvedTime",
        "student",
        "ad",
        "course",
        "review",
        "difficulty",
        "workload"
      };

      // list of info, since one prof can have multiple ratings
      List<String[]> list = new ArrayList<String[]>();
      
      boolean inFlag = false;
      String[] info = new String[options.length];
      for (int i = 0; i < info.length; i++) {
        info[i] = "";
      }

      while (in.hasNext()) {
        String ln = in.nextLine();
        if (ln.length() <= 0)
          System.out.println("depart " + dp + " prof " + prof);
        if (ln.charAt(0) == '~') 
          break;
        else if (ln.charAt(0) == '-') {
          String nm = ln.substring(1, ln.length()).trim();
          if (nm.equals(prof)) {
            // if is the prof we're writing to
            inFlag = true;
            info = new String[options.length];
          } else {
            inFlag = false;
          }
        } else if (ln.charAt(0) == '+') {
          if (inFlag) {
            String course = ln.substring(1, ln.length()).trim();
            info[3] = course;
          }
        } else if (ln.charAt(0) == '=') {
          if (inFlag) {
            // difficulty and workload
            if (ln.length() > 1)
              info[5] = "" + ln.charAt(1);
            if (ln.length() > 3)
              info[6] = "" + ln.charAt(3);
          }
        } else if (ln.charAt(0) == '_') {
          // reviews
          if (inFlag)
            info[4] = ln.substring(1, ln.length()).trim();
        } else if (ln.charAt(0) == '<') {
          if (inFlag)
            info[1] = ln.substring(1, ln.length()).trim();
        } else if (ln.charAt(0) == '!') {
          // if end of review
          if (inFlag)
            list.add(info);
        }
      }


      wr.write(header);
      for (String[] data : list) {
        wr.write("{\n");
        for (int i = 0; i < info.length; i++) {
          wr.append("  \"" +  options[i] + "\" : ");
          wr.append("\"" + data[i] + "\"");

          if (i != info.length - 1) {
            // if not the last option
            wr.write(",\n");
          } else {
            // if last option
            wr.write("\n" + "}");
          }
        }
      }
      wr.write(footer);
      wr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // get all prof names of one department(one csv file)
  public static Set<String> getProfNames(Scanner in) {
    Set<String> nm = new HashSet<String>();
    while (in.hasNext()) {
      String ln = in.nextLine();
      if (ln.length() > 0) {
        if (ln.charAt(0) == '~') // if end marker
          break;
        else if (ln.charAt(0) == '-') {
          // if prof name, add it to the set
          nm.add(ln.substring(1, ln.length()).trim());
        }
        // else just go to next line
      }
    }
    return nm;
  }

  public static String[] getDeparts() {
    String[] str = {
      "acct",
      "art",
      "buad",
      "cmsc",
      "econ",
      "engl",
      "fin",
      "fys",
      "hist",
      "jour",
      "llc",
      "ldst",
      "mgmt",
      "mkt",
      "math",
      "sci",
      /*"soc"*/
    };

    // attach .cvs
    for (int i = 0; i < str.length; i++) {
      str[i] += ".csv";
      System.out.println(str[i]);
    }

    return str;
  }

}
