import java.util.Scanner;

/**
 * .
 * 添加注释
 */
public class Expression {
    /**
     * @param args 主方法字符串数组
     */
    public static void main(final String[] args) {
        final Expression test = new Expression();
        System.out.println("请输入正确的表达式：");
        final Scanner source = new Scanner(System.in, "GBK");
        String expre = source.nextLine();
        String command;
        int f = test.expression(expre);
        while (f == 0) {

            System.out.println("请输入正确表达式！");
            expre = source.nextLine();
            f = test.expression(expre);
        }
        System.out.println("请输入命令（求值：!simplify var1=num1 ... varn=numn；"
                + "  求导： !d/d var");
        command = source.nextLine();
        while (command != null) {
            if (command.charAt(1) == Constant.SYMBOL_S0) {
                System.out.println(test.simplify(expre, command));
            } else {
                test.derivative(expre, command);
            }
            System.out.println("是否想继续对输入字符串进行操作 ？（Y or N）");
            final String str = source.nextLine();
            if (str.charAt(0) == 'Y' || str.charAt(0) == 'y') {
                System.out.println("请继续输入操作：");
                command = source.nextLine();
            } else {
                command = null;
            }

        }


    }

    /**
     * @param expre （填写参数含义）
     * @return （填写返回值）
     */
    private int expression(final String expre) {
        int flag = 1;
        for (int i = 1; i < expre.length() - 1; i++) {
            final char ch = expre.charAt(i);
            if (!Character.isDigit(ch) && !Character.isLetter(ch) && ch != '+'
                    && ch != '*') {
                flag = 0;
                break;
            }
            if (Character.isDigit(ch)) {
                final char ch1 = expre.charAt(i - 1);
                final char ch2 = expre.charAt(i + 1);
                if (!Character.isDigit(ch1) && ch1 != '+' && ch1 != '*'
                        || !Character.isDigit(ch2) && ch2 != '+'
                        && ch2 != '*') {
                    flag = 0;
                }
            } else if (Character.isLetter(ch)) {
                final char ch1 = expre.charAt(i - 1);
                final char ch2 = expre.charAt(i + 1);
                if (ch1 != '+' && ch1 != '*' || ch2 != '+' && ch2 != '*') {
                    flag = 0;
                }
            } else {
                final char ch1 = expre.charAt(i - 1);
                final char ch2 = expre.charAt(i + 1);
                if (!Character.isDigit(ch1) && !Character.isLetter(ch1)
                        || !Character.isDigit(ch2)
                        && !Character.isLetter(ch2)) {
                    flag = 0;
                }
            }
        }
        if (!Character.isDigit(expre.charAt(0))
                && !Character.isLetter(expre.charAt(0))) {
            flag = 0;
        }
        if (!Character.isDigit(expre.charAt(expre.length() - 1))
                && !Character.isLetter(expre.charAt(expre.length() - 1))) {
            flag = 0;
        }
        return flag;

    }

    /**.
     * 函数进行简化，先把表达式通过‘+’分成几部分，然后分别对每部分进行操作；
     * 又将每部分的变量和数字分开对其进行计算后再合并输出
     * @param expre (填写参数含义)
     * @param com   (填写参数含义)
     * @return (填写返回值)
     */
    private String simplify(final String expre, final String com) {
        int j,
                n,
                k;
        final StringBuffer ch = new StringBuffer();
        String newExpre = expre;
        for (int i = 0; i < com.length(); i++) {
            final StringBuffer str = new StringBuffer();
            final StringBuffer ch1 = new StringBuffer();
            if (com.charAt(i) == Constant.SYMBOL_EQ) {
                ch1.append(com.charAt(i - 1));
                while (Character.isDigit(com.charAt(i + 1))) {
                    ch.append(com.substring(i + 1, i + 2));
                    str.append(ch);
                    ch.delete(0, 1);
                    i += 1;

                    if (i == com.length() - 1) {
                        break;
                    }
                }
                newExpre = newExpre.replace(ch1, str);
            }
        }
        n = 0;
        for (int i = 0; i < newExpre.length(); i++) {
            if (newExpre.charAt(i) == Constant.SYMBOL_PLUS) {
                n = n + 1;
            }
        }
        String[] listn = new String[n + 1];
        String[] listc = new String[n + 1];
        int s = 0;
        j = 0;
        for (int i = 0; i < newExpre.length(); i++) {
            if (newExpre.charAt(i) == Constant.SYMBOL_PLUS) {
                listn[j] = newExpre.substring(s, i);
                j = j + 1;
                s = i + 1;
            }
        }
        listn[n] = newExpre.substring(s, newExpre.length());
        for (int i = 0; i <= n; i++) {
            listc[i] = "*";
        }
        int[] num = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            num[i] = 1;
        }

        for (int i = 0; i <= n; i++) {
            for (j = 0; j < listn[i].length(); j++) {
                if (Character.isDigit(listn[i].charAt(j))) {
                    for (k = j; k < listn[i].length(); k++) {
                        if (listn[i].charAt(k) == Constant.SYMBOL_MUL) {
                            break;
                        }

                    }

                    final String digit = listn[i].substring(j, k);
                    num[i] = num[i] * (Integer.parseInt(digit));
                    j = k - 1;
                } else if (Character.isLetter(listn[i].charAt(j))) {
                    final StringBuffer tmp = new StringBuffer(listc[i]);
                    tmp.append(listn[i].substring(j, j + 1)).append(
                            Constant.SYMBOL_MUL);
                    listc[i] = String.valueOf(tmp);
                }

            }
        }
        for (int i = 0; i < n; i++) {
            final String str3 = listc[i];
            for (j = i + 1; j <= n; j++) {
                if (listc[j] == null || listc[j].equals(str3)) {
                    num[i] = num[i] + num[j];
                    num[j] = 0;
                    listc[j] = null;
                }
            }
        }
        String expre1 = "";
        for (int i = 0; i <= n; i++) {
            if (num[i] != 0) {
                final StringBuffer tmp = new StringBuffer(expre1);
                tmp.append(num[i]).append(listc[i]).append(
                        Constant.SYMBOL_PLUS);
                expre1 = String.valueOf(tmp);
            }
        }
        expre1 = expre1.replace("*+", "+");
        if (expre1.charAt(expre1.length() - 1) == Constant.SYMBOL_PLUS) {
            expre1 = expre1.substring(0, expre1.length() - 1);
        }
        return expre1;
    }

    /**.
     * 只是对以上函数进行的一点改变，先把表达式进行简化，
     * 然后再把其通过相同的方法分成几部分分别操作，然后进行降幂求导
     *
     * @param expre (填写参数含义)
     * @param com   (填写参数含义)
     */
    private void derivative(final String expre, final String com) {
        final Expression test = new Expression();
        final String newExpre = test.simplify(expre, com);
        int i,
                j,
                k,
                n = 0,
                flag = 0;
        char ch = '*';
        for (i = 0; i < com.length() - 1; i++) {
            if (com.charAt(i) == Constant.SYMBOL_SLASH) {
                ch = com.charAt(i + 2);
                flag = 1;
                for (j = 0; j <= newExpre.length(); j++) {
                    if (newExpre.charAt(j) == ch) {
                        flag = 1;
                        break;
                    }
                }
            }
        }
        if (flag == 0) {
            System.out.println("请重新输入！");
        } else {
            for (i = 0; i < newExpre.length(); i++) {
                if (newExpre.charAt(i) == Constant.SYMBOL_PLUS) {
                    n = n + 1;
                }
            }
            String[] listn = new String[n + 1];
            String[] listc = new String[n + 1];
            for (i = 0; i <= n; i++) {
                listc[i] = "";
            }
            int s = 0;
            j = 0;
            for (i = 0; i < newExpre.length(); i++) {
                if (newExpre.charAt(i) == Constant.SYMBOL_PLUS) {
                    listn[j] = newExpre.substring(s, i);
                    j = j + 1;
                    s = i + 1;
                }
            }
            listn[n] = newExpre.substring(s, newExpre.length());
            int[] num = new int[n + 1];

            for (i = 0; i <= n; i++) {
                num[i] = 1;
            }
            int[] sum = new int[n + 1];
            for (i = 0; i <= n; i++) {
                sum[i] = 0;
                for (j = 0; j < listn[i].length(); j++) {
                    if (listn[i].charAt(j) == ch) {
                        sum[i] = sum[i] + 1;           //计算每组变量的次幂
                    }
                }
                for (j = 0; j < listn[i].length(); j++) {
                    if (Character.isDigit(listn[i].charAt(j))) {
                        for (k = j; k < listn[i].length(); k++) {
                            if (listn[i].charAt(k) == Constant.SYMBOL_MUL) {
                                break;
                            }

                        }

                        final String digit = listn[i].substring(j, k);
                        num[i] = num[i] * (Integer.parseInt(digit));
                        j = k - 1;
                    } else if (Character.isLetter(listn[i].charAt(j))
                            && listn[i].charAt(j) != ch) {
                        final StringBuffer tmp = new StringBuffer(listc[i]);
                        tmp.append(listn[i].substring(j, j + 1)).append(
                                Constant.SYMBOL_MUL);
                        listc[i] = String.valueOf(tmp);
                    }

                }
                if (sum[i] == 0) {
                    listc[i] = null;
                    num[i] = 0;
                } else if (sum[i] != 0 && sum[i] != 1) {
                    for (j = 1; j < sum[i]; j++) {
                        final StringBuffer tmp = new StringBuffer(listc[i]);
                        tmp.append(Constant.SYMBOL_MUL).append(ch).append(
                                Constant.SYMBOL_MUL);
                        listc[i] = String.valueOf(tmp);
                    }
                    num[i] = num[i] * sum[i];
                }
            }
            String expre1 = "";
            for (i = 0; i <= n; i++) {
                if (num[i] != 0) {
                    final StringBuffer tmp = new StringBuffer(expre1);
                    tmp.append(num[i]).append(listc[i]).append(
                            Constant.SYMBOL_PLUS);
                    expre1 = String.valueOf(tmp);
                }
            }
            expre1 = expre1.replace("*+", "+");
            if (expre1.charAt(expre1.length() - 1) == Constant.SYMBOL_PLUS) {
                expre1 = expre1.substring(0, expre1.length() - 1);
            }
            System.out.println(expre1);

        }


    }

}

/**.
 * 常量
 */
final class Constant {

    /**
     * .
     * 字符's'
     */
    public static final char SYMBOL_S0 = 's';

    /**
     * .
     * 字符'='
     */
    public static final char SYMBOL_EQ = '=';

    /**
     * .
     * 字符'+'
     */
    public static final char SYMBOL_PLUS = '+';

    /**
     * .
     * 字符'*'
     */
    public static final char SYMBOL_MUL = '*';

    /**
     * .
     * 字符'/'
     */
    public static final char SYMBOL_SLASH = '/';

    /**
     * .
     * 隐藏工具类的构造函数
     */
    private Constant() {
    }
}
