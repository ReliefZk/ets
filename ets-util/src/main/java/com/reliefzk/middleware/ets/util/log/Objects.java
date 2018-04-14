package com.reliefzk.middleware.ets.util.log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Objects {

    private static final Logger LOGGER = LoggerFactory.getLogger(Objects.class);

    public static String asString(Object obj) {
        return asString(obj, null);
    }

    public static String asString(Object obj, String defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : obj.toString();
    }

    public static Boolean asBoolean(Object obj) {
        return asBoolean(obj, null);
    }

    public static Boolean asBoolean(Object obj, Boolean defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : Boolean.valueOf(obj.toString());
    }

    public static Integer asInteger(Object obj) {
        return asInteger(obj, null);
    }

    public static Integer asInteger(Object obj, Integer defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : Integer.valueOf(obj.toString());
    }

    public static Long asLong(Object obj) {
        return asLong(obj, null);
    }

    public static Long asLong(Object obj, Long defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : Long.valueOf(obj.toString());
    }

    public static Float asFloat(Object obj) {
        return asFloat(obj, null);
    }

    public static Float asFloat(Object obj, Float defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : Float.valueOf(obj.toString());
    }

    public static Double asDouble(Object obj) {
        return asDouble(obj, null);
    }

    public static Double asDouble(Object obj, Double defaultValue) {
        return null == obj ? (null == defaultValue ? null : defaultValue) : Double.valueOf(obj.toString());
    }

    /**
     * @since 1.1.6
     */
    public static byte[] serialize(Object obj) {
        byte[] result = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            result = bo.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Failed to serialize object", e);
        } finally {
            try {
                bo.close();
                oo.close();
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
        return result;
    }

    /**
     * @since 1.1.6
     */
    public static Object deserialize(byte[] bytes) {
        Object result = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            result = oi.readObject();
        } catch (Exception e) {
            LOGGER.error("Failed to deserialize object", e);
        } finally {
            try {
                bi.close();
                oi.close();
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
        return result;
    }

    public static String inspect(Object obj) {
        return Inspector.inspect(obj);
    }
}