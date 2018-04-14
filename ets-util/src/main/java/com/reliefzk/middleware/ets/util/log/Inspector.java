package com.reliefzk.middleware.ets.util.log;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class Inspector {

    private static final String S_INDENT    = "  ";
    private static final String S_NULL      = "<null>";
    private static final String S_EMPTY     = "<empty>";
    private static final String S_EXCEPTION = "<exception>";
    private static final String S_IGNORE    = "<...>";

    public static int MAX_LAYERS = 10;

    /**
     * Inspects object
     * @param obj
     * @return string
     */
    public static String inspect(Object obj) {
        return toString(obj);
    }

    private static String toString(Object obj) {
        return toString(obj, 0);
    }

    private static String toString(Object obj, int indent) {

        StringBuilder builder = new StringBuilder();
        if (null == obj) {
            builder.append(S_NULL);
        } else {
            if (isSimpleType(obj)) {
                builder.append(toSimpleString(obj, indent));
            } else if (isArrayType(obj)) {
                if (obj instanceof byte[]) {
                    builder.append(Arrays.toString((byte[]) obj));
                } else {
                    builder.append(toArrayString((Object[]) obj, indent));
                }
            } else if (isCollectionType(obj)) {
                builder.append(toIterableString((Iterable<?>) obj, indent));
            } else if (isMapType(obj)) {
                builder.append(toMapString((Map<?, ?>) obj, indent));
            } else {
                builder.append(toObjectString(obj, indent));
            }
        }
        return builder.toString();

    }

    private static String toSimpleString(Object obj, int indent) {
        if (indent > MAX_LAYERS) {
            return S_IGNORE;
        }

        StringBuilder builder = new StringBuilder();
        if (null == obj) {
            builder.append(S_NULL);
        } else {
            builder.append(obj.toString());
        }
        return builder.toString();
    }

    private static String toObjectString(Object obj, int indent) {
        if (indent > MAX_LAYERS) {
            return S_IGNORE;
        }

        StringBuilder builder = new StringBuilder();
        if (null == obj) {
            builder.append(S_NULL);
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
                if (properties != null) {
                    builder.append("{");
                    builder.append(" #" + obj.getClass().getSimpleName() + "#");
                    builder.append("\n");
                    indent++;
                    for (int i = 0; i < properties.length; i++) {
                        if (!"class".equals(properties[i].getName())) {
                            Method readMethod = properties[i].getReadMethod();
                            if (null != readMethod) {
                                builder.append(getIndent(indent));
                                builder.append(properties[i].getName());
                                builder.append(" = ");
                                Object value = null;
                                try {
                                    value = readMethod.invoke(obj, new Object[0]);
                                } catch (Exception e) {
                                    value = S_EXCEPTION;
                                }
                                if (null == value) {
                                    builder.append(S_NULL);
                                } else {
                                    builder.append(toString(value, indent));
                                }
                                builder.append("\n");
                            }
                        }
                    }
                    indent--;
                    builder.append(getIndent(indent));
                    builder.append("}");
                }
            } catch (Exception e) {
            }
        }
        return builder.toString();
    }

    private static String toArrayString(Object[] array, int indent) {

        if (indent > MAX_LAYERS) {
            return S_IGNORE;
        }

        StringBuilder builder = new StringBuilder();
        if (null == array) {
            builder.append(S_NULL);
        } else {
            builder.append("[ #Array#");
            indent++;
            if (0 == array.length) {
                builder.append("\n");
                builder.append(getIndent(indent));
                builder.append(S_EMPTY);
            } else {
                for (int i = 0; i < array.length; i++) {
                    builder.append("\n");
                    builder.append(getIndent(indent));
                    builder.append("[" + i + "]");
                    builder.append(" = ");
                    builder.append(toString(array[i], indent));
                }
            }
            indent--;
            builder.append("\n");
            builder.append(getIndent(indent));
            builder.append("]");
        }
        return builder.toString();

    }

    private static String toIterableString(Iterable<?> iterable, int indent) {

        if (indent > MAX_LAYERS) {
            return S_IGNORE;
        }

        StringBuilder builder = new StringBuilder();
        if (null == iterable) {
            builder.append(S_NULL);
        } else {
            builder.append("[ #List#");
            indent++;
            Iterator<?> it = iterable.iterator();
            if (!it.hasNext()) {
                builder.append("\n");
                builder.append(getIndent(indent));
                builder.append(S_EMPTY);
            } else {
                while (it.hasNext()) {
                    builder.append("\n");
                    Object next = it.next();
                    // if (isSimpleType(next)) {
                    // builder.append(getIndent(indent));
                    // builder.append(toString(next, indent));
                    // } else {
                    builder.append(getIndent(indent));
                    builder.append(toString(next, indent));
                    // }
                }
            }
            indent--;
            builder.append("\n");
            builder.append(getIndent(indent));
            builder.append("]");
        }
        return builder.toString();

    }

    private static String toMapString(Map<?, ?> map, int indent) {

        if (indent > MAX_LAYERS) {
            return S_IGNORE;
        }

        StringBuilder builder = new StringBuilder();
        if (null == map) {
            builder.append(S_NULL);
        } else {
            builder.append("{ #Map#");
            indent++;
            if (map.isEmpty()) {
                builder.append("\n");
                builder.append(getIndent(indent));
                builder.append(S_EMPTY);
            } else {
                for (Object key : map.keySet()) {
                    builder.append("\n");
                    builder.append(getIndent(indent));
                    builder.append(null == key ? null : key.toString());
                    builder.append(" => ");
                    builder.append(toString(map.get(key), indent));
                }
            }
            indent--;
            builder.append("\n");
            builder.append(getIndent(indent));
            builder.append("}");
        }
        return builder.toString();

    }

    private static Boolean isSimpleType(Object value) {
        if (null == value) {
            throw new NullPointerException();
        }
        Boolean result = false;
        if (isPrimitive(value)) {
            result = true;
        } else {
            if (String.class.equals(value.getClass())) {
                result = true;
            } else if (BigDecimal.class.equals(value.getClass())) {
                result = true;
            } else if (value instanceof Date) {
                result = true;
            } else if (value.getClass().isEnum()) {
                result = true;
            } else {
                // do nothing here.
            }
        }
        return result;
    }

    private static Boolean isArrayType(Object value) {
        if (null == value) {
            throw new NullPointerException();
        }
        Boolean result = false;
        if (value.getClass().isArray()) {
            result = true;
        } else {
            // do nothing here.
        }
        return result;
    }

    private static Boolean isCollectionType(Object value) {
        if (null == value) {
            throw new NullPointerException();
        }
        Boolean result = false;
        if (value instanceof Iterable) {
            result = true;
        } else {
            // do nothing here.
        }
        return result;
    }

    private static Boolean isMapType(Object value) {
        if (null == value) {
            throw new NullPointerException();
        }
        Boolean result = false;
        if (value instanceof Map) {
            result = true;
        } else {
            // do nothing here.
        }
        return result;
    }

    private static String getIndent(int indent) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(S_INDENT);
        }
        return buffer.toString();

    }

    private static Boolean isPrimitive(Object value) {

        return (Character.class.equals(value.getClass()) || Byte.class.equals(value.getClass())
                || Short.class.equals(value.getClass()) || Integer.class.equals(value.getClass())
                || Long.class.equals(value.getClass()) || Float.class.equals(value.getClass())
                || Double.class.equals(value.getClass()) || Boolean.class.equals(value.getClass()));

    }

}