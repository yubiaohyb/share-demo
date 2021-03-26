package com.yubiaohyb.sharedemo.algorithm.sort;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 3/26/21 1:19 AM
 */
@Data
public class KMPWordSearcher {
    private String target;
    private List<Integer> crosserMaxLengthList;

    public KMPWordSearcher(String target) {
        this.target = target;
        crosserMaxLengthList = new ArrayList<>(target.length() - 1);
        for (int j = 0; j < target.length(); j++) {
            String temp = target.substring(0, j+1);
            int length = temp.length();
            List<String> prefixes = new ArrayList<>(length - 1);
            List<String> suffixes = new ArrayList<>(length - 1);
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    suffixes.add(target.substring(i, length));
                }
                if (i < length - 1) {
                    prefixes.add(target.substring(0, i+1));
                }
            }
            System.out.println(prefixes);
            System.out.println(suffixes);

            HashSet<String> prefixSet = new HashSet<>(prefixes);
            HashSet<String> suffixSet = new HashSet<>(suffixes);
            HashSet<String> intersectionSet = new HashSet<>();
            intersectionSet.addAll(prefixSet);
            intersectionSet.retainAll(suffixSet);
            System.out.println(intersectionSet);

            int maxLength = 0;
            for (String intersection : intersectionSet) {
                maxLength = intersection.length() > maxLength ? intersection.length() : maxLength;
            }
            crosserMaxLengthList.add(maxLength);
            System.out.println(maxLength);
            System.out.println("//////////////////");
        }
    }

    public List<Integer> searchIndex(String original) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0, pos = -1; i <= original.length() - target.length(); ) {
            for (int j = 0; j < target.length(); j++) {
                if (original.charAt(i + j) == target.charAt(j)) {
                    pos = j;
                } else {
                    for (int k = j - 1; k > 0; k--) {
                        if (crosserMaxLengthList.get(k) > 0) {
                            j = k - crosserMaxLengthList.get(k) + 1;
                            i += crosserMaxLengthList.get(k);
                            pos = j - 1;
                            break;
                        }
                    }
                    pos = -1;
                }
            }
            if (-1 == pos) {
                i++;
            } else {
                indexes.add(i);
                i+=target.length();
                pos = -1;
            }

        }
        return indexes;
    }

    public static void main(String[] args) {
        KMPWordSearcher ababc = new KMPWordSearcher("ababc");
        System.out.println(ababc);
        System.out.println(ababc.searchIndex("ababdababcababababc"));
    }
}
