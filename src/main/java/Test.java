import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Test {

    public static void main(String[] args) throws Exception {


        Set<String> set = new HashSet<>();

        List<String> lines = FileUtils.readLines(new File("C:\\Users\\Quiet\\Desktop\\马克思主义基本原理.txt"));
        Iterator<String> it = lines.iterator();

        while (it.hasNext()) {
            String next = it.next();
            if (next.matches("(.+[（|(].+[）|)].+)")) {
                String s1 = next;
                while (it.hasNext()) {
                    String s2 = it.next();
                    if (s2.matches("标准.+")) {
                        set.add(s1 + s2);
                        break;
                    }
                }

            }
        }
        for (String s : set) {
            System.out.println(s);
        }

    }
}
