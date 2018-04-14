package com.reliefzk.middleware.ets.util.log;

public class ExceptionLogUtil {

    /** errorLogger */
    private static final Logger LOGGER = LoggerFactory.getLogger("ERROR");

    /**
     * 禁用构造函数
     */
    private ExceptionLogUtil() {
        // 禁用构造函数
    }

    /**
     * 捕捉错误日志并输出到日志文件：common-error.log
     * 
     * @param e 异常堆栈
     * @param message 错误日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Throwable e, Object... message) {
        LogUtil.error(LOGGER, e, message);
    }
    
    /**
     * 捕捉错误日志并输出到日志文件：common-error.log
     * 
     * @param message 错误日志上下文信息描述，尽量带上业务特征
     */
    public static void error(Object... message) {
        LOGGER.error(LogUtil.getLogString(message));
    }
}
