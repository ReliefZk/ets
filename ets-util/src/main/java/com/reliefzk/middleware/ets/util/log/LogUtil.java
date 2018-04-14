package com.reliefzk.middleware.ets.util.log;

/**
 * 规范化日志打印工具，注意日志的级别选择：<br>
 * 
 * <p>
 *   <ol>
 *     <li>DEBUG <b>开发环境</b>应用调试，输出详细的应用状态
 *     <li>INFO <b>生产环境</b>运行状态观察，输出应用生命周期的中<b>正常重要事件</b>
 *     <li>WARN <b>生产环境</b>故障诊断，输出应用中的<b>可预期的异常事件</b>
 *     <li>ERROR <b>生产环境</b>境故障诊断，输出应用中的<b>未预期的异常事件</b>
 *   </ol>
 * </p>
 * 
 * <p>
 * @notice 
 *   <li>ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
 *   <li>日志记录支持数组参数，禁止使用“+”拼接入参，全部使用“,”由工具自行拼接
 * </p>
 * 
 */
public class LogUtil {

    /** 摘要日志的内容分隔符 */
    public static final String SEP = ",";

    /** 修饰符 */
    private static final char  RIGHT_TAG = ']';

    /** 修饰符 */
    private static final char  LEFT_TAG  = '[';

    /**
     * 打印info日志。
     * 
     * @param logger    日志对象
     * @param e         异常信息
     * @param objs      任意个要输出到日志的参数
     */
    public static void info(Logger logger, Throwable e, Object... objs) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(objs), e);
        }
    }

    /**
     * 打印指定格式的info日志。
     * 
     * @param logger
     * @param format
     * @param arguments
     */
    public static void info(Logger logger, String format, Object... arguments) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arguments);
        }
    }

    /**
     * 打印warn日志。
     * 
     * @param logger    日志对象
     * @param e         异常信息
     * @param objs      任意个要输出到日志的参数
     */
    public static void warn(Logger logger, Throwable e, Object... objs) {
        logger.warn(getLogString(objs), e);
    }

    /**
     * 打印指定格式的warn日志。
     * 
     * @param logger
     * @param format
     * @param objs
     */
    public static void warn(Logger logger, String format, Object... objs) {
        logger.warn(format, objs);
    }

    /**
     * 打印error日志。
     * 
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     * 
     * @param logger    日志对象
     * @param e         异常信息
     * @param objs      任意个要输出到日志的参数
     */
    public static void error(Logger logger, Throwable e, Object... objs) {
        logger.error(Objects.inspect(objs), e);
    }

    /**
     * 打印error日志。
     * 
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     * 
     * @param logger    日志对象
     * @param objs      任意个要输出到日志的参数
     */
    public static void error(Logger logger, Object... objs) {
        logger.error(Objects.inspect(objs));
    }

    /**
     * 打印指定格式的error日志。
     * @param logger
     * @param format
     * @param arguments
     */
    public static void error(Logger logger, String format, Object... arguments) {
        logger.error(format, arguments);
    }

    /**
     * 打印error日志。
     * 
     * ERROR日志记录尽量使用{@link ExceptionLogUtil}，避免日志的重复记录
     * 
     * @param messageName   报警日志的标题
     * @param logger        日志对象
     * @param objs          任意个要输出到日志的参数
     */
    public static void alert(String messageName, Logger logger, Object... objs) {
        logger.error(getLogString(messageName, objs));
    }

    /**
     * 打印debug日志。
     * 
     * @param logger    日志对象
     * @param e         异常信息
     * @param objs      任意个要输出到日志的参数
     */
    public static void debug(Logger logger, Throwable e, Object... objs) {
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(objs), e);
        }
    }

    /**
     * 打印指定格式的info日志。
     * 
     * @param logger
     * @param format
     * @param arguments
     */
    public static void debug(Logger logger, String format, Object... arguments) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arguments);
        }
    }

    /**
     * 生成输出到日志的字符串
     * <p>输出格式:[sofaId][messageName]objs...
     * 
     * @param messageName   报警日志的标题
     * @param objs          任意个要输出到日志的参数
     * @return              日志字符串
     */
    public static String getLogString(String messageName, Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        // 预留扩展位
        log.append(SEP).append(SEP).append(RIGHT_TAG);

        log.append(LEFT_TAG).append(messageName).append(RIGHT_TAG);

        if (objs != null) {
            for (Object o : objs) {
                log.append(o);
            }
        }

        return log.toString();
    }

    /**
     * 生成输出到日志的字符串
     * 
     * @param objs      任意个要输出到日志的参数
     * @return          日志字符串
     */
    public static String getLogString(Object... objs) {
        StringBuilder log = new StringBuilder();
        log.append(LEFT_TAG);
        // 预留扩展位
        log.append(SEP).append(SEP).append(RIGHT_TAG);

        if (objs != null) {
            for (Object o : objs) {
                log.append(o);
            }
        }
        return log.toString();
    }
}
