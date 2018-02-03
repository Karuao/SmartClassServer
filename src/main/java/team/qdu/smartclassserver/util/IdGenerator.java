package team.qdu.smartclassserver.util;

import java.math.BigInteger;
import java.util.Random;

public class IdGenerator {
    /**
     * 生成UUID
     * 这里的generateGUID()来产生随机的32位16进置值
     * @return UUID
     */
    public static String generateGUID() {
        return new BigInteger(165, new Random()).toString(36).toUpperCase();
    }
}
