public class ReadableScramble {
    public static void main(String[] args) {
        System.out.println(scrambledString("ASCII stands for American Standard Code for Information Interchange. Computers can only understand numbers, so an ASCII code is the numerical representation of a character such as 'a' or '@' or an action of some sort."));
    }

    public static String scrambledString(String in) {
        String ret = "";
        String addNext = "";

        for (int i=0; i<in.length(); i++) {
            if (((int)in.charAt(i) >= 65 && (int)in.charAt(i) <= 90) || 
                ((int)in.charAt(i) >= 97 && (int)in.charAt(i) <= 122)) {
                    addNext = addNext + in.charAt(i);
            }
            else {
                ret = ret + scrambleSegment(addNext) + in.charAt(i);
                addNext = "";
            }
        }

        return ret + scrambleSegment(addNext);
    }

    public static String scrambleSegment(String in) {
        if (in.length() <= 1) return in;
        
        if (in.length() <= 3) in = " " + in + " ";
        String ret = "" + in.charAt(0);

        while (in.length() > 2) {
            int rand = (int)(Math.random() * Math.min(in.length()-2, 2)) + 1;

            ret = ret + in.charAt(rand);
            in = in.substring(0, rand) + in.substring(rand+1);
        }

        if (in.startsWith(" ")) return ret.substring(1);

        ret = ret + in.charAt(in.length()-1);
        return ret;
    }
}