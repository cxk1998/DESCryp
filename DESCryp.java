public class DESCryp {
    // 置换表
    private int[][] ipTable = {
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6},
            {64, 56, 48, 40, 32, 24, 16, 8},
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7} };
    // 逆置换表
    private int[][] ipTable_R = {
            {40, 8, 48, 16, 56, 24, 64, 32},
            {39, 7, 47, 15, 55, 23, 63, 31},
            {38, 6, 46, 14, 54, 22, 62, 30},
            {37, 5, 45, 13, 53, 21, 61, 29},
            {36, 4, 44, 12, 52, 20, 60, 28},
            {35, 3, 43, 11, 51, 19, 59, 27},
            {34, 2, 42, 10, 50, 18, 58, 26},
            {33, 1, 41, 9, 49, 17, 57, 25 }};
    // E扩展表
    private int[] extendTable = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17,
            18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };
    // P置换表
    private int[] pTable = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25 };
    // PC-1置换表
    private int[] pcTable_1= { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11,
            3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13,
            5, 28, 20, 12, 4 };
    // PC-2置换表
    private int[] pcTable_2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };

    private int[] loopTable = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
    //S-盒
    private int[][] SBox = {
            // S1
            { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3,
                    8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10,
                    0, 6, 13 },
            // S2
            { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11,
                    5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0,
                    5, 14, 9 },
            // S3
            { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15,
                    1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11,
                    5, 2, 12 },
            // S4
            { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14,
                    9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12,
                    7, 2, 14 },
            // S5
            { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8,
                    6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9,
                    10, 4, 5, 3 },
            // S6
            { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3,
                    8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6,
                    0, 8, 13 },
            // S7
            { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8,
                    6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14,
                    2, 3, 12 },
            // S8
            { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9,
                    2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3,
                    5, 6, 11 } };

    public static void main(String[] args) {
        String originText = "My name is Carter";
        String key = "16340014";
        System.out.println("明文为：" + originText +
                "\n密钥为：" + key);
        DESCryp des = new DESCryp();
        int l = originText.length() % 8;
        int s = originText.length() / 8;
        boolean lack = (l != 0);
        StringBuffer crypt = new StringBuffer();
        for (int i = 0; i < s; i++)
            crypt.append(des.encrypt(originText.substring(i*8,(i+1)*8), key, "encrypt", false));
        if (lack)
            crypt.append(des.encrypt(originText.substring(s*8), key, "encrypt", true));
        String cryptText = crypt.toString();
        System.out.println("加密为：" + cryptText);
        StringBuffer decrypt = new StringBuffer();
        for (int i = 0; i < s; i++)
            decrypt.append(des.encrypt(cryptText.substring(i*8,(i+1)*8), key, "decrypt", false));
        if (lack)
            decrypt.append(des.encrypt(cryptText.substring(s*8), key, "decrypt", true));
        System.out.println("解密为：" + decrypt.toString());
    }

    //转换成二进制字符串
    public StringBuffer toBinaryString(String key){
        StringBuffer keyBinary = new StringBuffer();
        for (int i = 0; i < 8; ++i) {
            StringBuffer mSubKeyTemp = new StringBuffer(Integer.toBinaryString(key.charAt(i)));
            while (mSubKeyTemp.length() < 8) {
                mSubKeyTemp.insert(0, 0);
            }
            keyBinary.append(mSubKeyTemp);
        }
        return keyBinary;
    }

    //子密钥生成
    public StringBuffer[] getSubKey(String key) {
        StringBuffer[] subKey = new StringBuffer[16];

        StringBuffer keyBinary = toBinaryString(key);

        StringBuffer substituteKey = new StringBuffer(); // 存储PC1置换后的密钥
        for (int i = 0; i < 56; ++i) {
            substituteKey.append(keyBinary.charAt(pcTable_1[i] - 1));
        }

        // 分成左右两块C0和D0
        StringBuffer C0 = new StringBuffer(); // 存储密钥左块
        StringBuffer D0 = new StringBuffer(); // 存储密钥右块
        C0.append(substituteKey.substring(0, 28));
        D0.append(substituteKey.substring(28));

        // 循环16轮生成子密钥
        for (int i = 0; i < 16; ++i) {
            // 根据LOOP_Table循环左移
            for (int j = 0; j < loopTable[i]; ++j) {
                char mTemp;
                mTemp = C0.charAt(0);
                C0.deleteCharAt(0);
                C0.append(mTemp);
                mTemp = D0.charAt(0);
                D0.deleteCharAt(0);
                D0.append(mTemp);
            }

            // 把左右两块合并
            StringBuffer C0D0 = new StringBuffer(C0.toString() + D0.toString());

            // 根据PC2压缩C0D0，得到子密钥
            StringBuffer C0D0Temp = new StringBuffer();
            for (int j = 0; j < 48; ++j) {
                C0D0Temp.append(C0D0.charAt(pcTable_2[j] - 1));
            }

            // 把子密钥存储到数组中
            subKey[i] = C0D0Temp;
        }
        return subKey;
    }

    // 加密解密
    public String encrypt(String orignText, String key, String type, boolean lack) {
        if (type.equals("encrypt") && lack) {
            int l = 8 - orignText.length();
            StringBuffer tt = new StringBuffer(orignText);
            for (int i = 0; i < l; i++)
                tt.append(String.valueOf(l));
            orignText = tt.toString();
        }
        StringBuffer ciphertext = new StringBuffer();//最终密文结果
        StringBuffer originTextBinary = toBinaryString(orignText);//二进制形式的明文
        StringBuffer substituteOrigintext = new StringBuffer();//置换后的明文
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; j++)
            substituteOrigintext.append(originTextBinary.charAt(ipTable[i][j] - 1));
        }//ip置换

        StringBuffer L = new StringBuffer(substituteOrigintext.substring(0, 32));
        StringBuffer R = new StringBuffer(substituteOrigintext.substring(32));
        //明文M分为L(前32)和R(后32)两块

        StringBuffer[] subKey = getSubKey(key);//获得子密钥

        if (type.equals("decrypt")) {
            StringBuffer[] mTemp = getSubKey(key);
            for (int i = 0; i < 16; ++i) {
                subKey[i] = mTemp[15 - i];
            }
        }//解密需要倒序使用子密钥

        // 16轮迭代T
        for (int i = 0; i < 16; ++i) {
            StringBuffer mLTemp = new StringBuffer(L);
            L.replace(0, 32, R.toString());

            // 按E位选择表扩展右边
            StringBuffer mRTemp = new StringBuffer(); // 存储扩展后的右边
            for (int j = 0; j < 48; ++j) {
                mRTemp.append(R.charAt(extendTable[j] - 1));
            }

            // 扩展后的右边和子密钥异或
            for (int j = 0; j < 48; ++j) {
                if (mRTemp.charAt(j) == subKey[i].charAt(j)) {
                    mRTemp.replace(j, j + 1, "0");
                } else {
                    mRTemp.replace(j, j + 1, "1");
                }
            }

            // 进行S盒压缩
            R.setLength(0);
            for (int j = 0; j < 8; ++j) {
                String mSNumber = mRTemp.substring(j * 6, (j + 1) * 6);
                int row = Integer.parseInt(Character.toString(mSNumber.charAt(0)) + mSNumber.charAt(5), 2);
                int column = Integer.parseInt(mSNumber.substring(1, 5), 2);
                int number = SBox[j][row * 16 + column];
                StringBuffer numberString = new StringBuffer(Integer.toBinaryString(number));
                while (numberString.length() < 4) {
                    numberString.insert(0, 0);
                }
                R.append(numberString);
            }

            // 将压缩后的R通过P_Table置换
            StringBuffer mRTemp1 = new StringBuffer(); // 存储置换后的R
            for (int j = 0; j < 32; ++j) {
                mRTemp1.append(R.charAt(pTable[j] - 1));
            }
            R.replace(0, 32, mRTemp1.toString());

            // 将置换后的R与最开始的L异或
            for (int j = 0; j < 32; ++j) {
                if (R.charAt(j) == mLTemp.charAt(j)) {
                    R.replace(j, j + 1, "0");
                } else {
                    R.replace(j, j + 1, "1");
                }
            }
        }

        // 合并迭代完的L和R
        StringBuffer LR = new StringBuffer(R.toString() + L.toString());

        //根据IPR_Table置换LR
        StringBuffer mLRTemp = new StringBuffer(); // 存储置换后的LR
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; j++)
            mLRTemp.append(LR.charAt(ipTable_R[i][j] - 1));
        }

        // 2.8 把二进制转为字符串
        for (int i = 0; i < 8; ++i) {
            String mCharTemp = mLRTemp.substring(i * 8, (i + 1) * 8);
            ciphertext.append((char) Integer.parseInt(mCharTemp, 2));
        }
        String result = ciphertext.toString();
        int x= result.charAt(result.length()-1)-'0';
        if (lack && type.equals("decrypt")) {
            result = result.substring(0,result.length()-x);
        }
        return result;
    }
}
