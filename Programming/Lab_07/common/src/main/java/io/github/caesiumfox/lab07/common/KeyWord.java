package io.github.caesiumfox.lab07.common;

import io.github.caesiumfox.lab07.common.entry.Movie;
import io.github.caesiumfox.lab07.common.entry.MpaaRating;

/**
 * Первые 8 байт любой датаграмы --
 * ID сессии, либо 0, если сессия не открыта.
 * Девятый байт любой датаграммы является
 * ключевым словом по которому определяются
 * дальнейшие действия того, кто эту
 * датаграмму принял.
 */
public enum KeyWord {
    /**
     * Пустое ключевое слово.
     * В ответ на него должно быть послано
     * сообщение с тем же ключевым словом.
     * Далее работа продолжается, как если
     * бы этих "пустых" сообщений не было.
     */
    NO_OPERATION,
    /**
     * Посылается один лишь этот байт.
     * Остальное должно игнорироваться.
     * В ответ посылается объект
     * {@link MutableDatabaseInfo},
     * перед которым идёт
     * ключевое слово (байт)
     * {@link #SOME_LEFT}.
     * Его может отправить только
     * клиент серверу.
     */
    GET_INFO,
    /**
     * Посылается серверу клиентом,
     * после этого байта должна идти строка
     * (4 байта на длнину строки, затем
     * сама строка в UTF-16).
     * Ответ представляет из себя
     * ключевое слово
     * {@link #SOME_LEFT},
     * после которого идёт один байт:
     * 0 - не зарегестрировано,
     * иное - зарегестрировано.
     */
    CHECK_PASSPORT_ID,
    /**
     * Посылается серверу клиентом,
     * после этого байта должно идти
     * число (4 байта, int).
     * Ответ представляет из себя
     * ключевое слово
     * {@link #SOME_LEFT},
     * после которого идёт один байт:
     * 0 - не зарегестрировано,
     * иное - зарегестрировано.
     */
    CHECK_ID,

    /**
     * Отправляется клиентом серверу для
     * получения всех элементов коллекции.
     * Далее происходит пересылка объектов.
     */
    GET_ALL,
    /**
     * Отпраляется клиентом серверу
     * для вставки объекта в свободный ID.
     * Поле id самого Movie игнорируется
     * сервером.
     * После ключевого слова записывается
     * объект {@link Movie.RawData}
     * через его метод.
     */
    INSERT,
    /**
     * Отпраляется клиентом серверу
     * для вставки объекта в желаемый ID.
     * Поле id самого Movie является
     * желаемым ID.
     * После ключевого слова записывается
     * объект {@link Movie.RawData}
     * через его метод.
     */
    INSERT_ID,
    /**
     * Отпраляется клиентом серверу
     * для замены объекта в указанном ID.
     * Поле id самого Movie является
     * указанным ID.
     * После ключевого слова записывается
     * объект {@link Movie.RawData}
     * через его метод.
     */
    UPDATE,
    /**
     * Отпраляется клиентом серверу
     * для удаления объекта по его ID.
     * После ключевого слова записывается
     * id (4 байта, int).
     */
    REMOVE_KEY,
    /**
     * Отпраляется клиентом серверу
     * для удаления объекта меньшего,
     * чем заданный.
     * После ключевого слова записывается
     * объект {@link Movie.RawData}
     * через его метод.
     */
    REMOVE_LOWER,
    /**
     * Отпраляется клиентом серверу
     * для удаления объектов, ID которых
     * меньше заданного.
     * После ключевого слова записывается
     * id (4 байта, int).
     */
    REMOVE_LOWER_KEY,
    /**
     * Отпраляется клиентом серверу
     * для удаления объектов, ID которых
     * больше заданного.
     * После ключевого слова записывается
     * id (4 байта, int).
     */
    REMOVE_GREATER_KEY,
    /**
     * Отпраляется клиентом серверу
     * для очистки базы данных.
     */
    CLEAR,
    /**
     * Отпраляется клиентом серверу
     * для вывода минимального объекта.
     * В ответ сервер посылает
     * {@link #SOME_LEFT}, но отвечать
     * на это сообщение не нужно, так как
     * посылается лишь один объект.
     * Если БД пуста, отправляется
     * {@link #NOTHING_LEFT}
     *
     * @see #SOME_LEFT
     */
    MIN_BY_MPAA,
    /**
     * Отправляется клиентом серверу
     * для подсчёта записей в БД,
     * у которых количество оскаров
     * большее, чем задано.
     * После ключевого слова записывается
     * указанный порог количества оскаров
     * (8 байт, long).
     * В ответ клиент получает
     * {@link #SOME_LEFT},
     * после которого идёт int
     * с результатом.
     */
    COUNT_GREATER_OSCARS,
    /**
     * Отправляется клиентом серверу
     * для отбора записей по
     * заданной возрастной категории.
     * После ключевого слова записывается
     * {@link MpaaRating}
     * через его метод ordinal().
     * Далее идёт передача записей.
     */
    FILTER_BY_MPAA,

    /**
     * Отправляется клиентом серверу для
     * продолжения пересылки объектов по
     * одному из некоторого массива.
     * Отправляется только если предыдущее
     * сообщение от сервера начиналось со
     * слова
     * {@link #SOME_LEFT}
     */
    CONTINUE,
    /**
     * Отправляется сервером при пересылке массива данных.
     * Может быть ответом на запрос отправки данных с
     * сервера. Отправляется только если остались
     * ещё элементы в коллекции.
     * После идёт очередной элемент коллекции.
     * Передача элементов заканчивается сообщением,
     * начинающегося с {@link #NOTHING_LEFT}.
     *
     * Также используется для передачи одиночных объектов.
     * В этом случае {@link #NOTHING_LEFT} не посылается.
     */
    SOME_LEFT,
    /**
     * Отправляется сервером при пересылке массива данных.
     * Может быть ответом на запрос отправки данных с
     * сервера. Отправляется, если не осталось больше
     * элементов в коллекции. На этом сообщение заканчивается.
     */
    NOTHING_LEFT,

    /**
     * Отправляется клиентом серверу
     * для проверки входа.
     * Отправляется только при входе
     * в учётную запись на стороне клиента
     * вместо NO_OPERATION.
     */
    LOGIN_CHECK,
    /**
     * Отправляется сервером клиенту
     * в случае неправильного входа.
     */
    LOGIN_INCORRECT,
    /**
     * Отправляется сервером клиенту
     * в случае перегруженности сервера.
     */
    SERVER_OVERLOADED,
    /**
     * Отправляется сервером клиенту
     * в случае приёма неправильного
     * номера сессии.
     */
    INVALID_SESSION,

    /**
     * Если команда от клиента серверу
     * не требует особого ответа, то
     * это сообщение посылается в ответ
     * клиенту, если всё прошло успешно.
     */
    OK,
    /**
     * Если команда от клиента серверу
     * не требует особого ответа, то
     * это сообщение посылается в ответ
     * клиенту, если при выполнении
     * команды произошли ошибки.
     * После этого ключевого слова
     * идёт строка с сообщением о неполадке
     * (4 байта на длину, затем
     * сама строва в формате UTF-16).
     */
    ERROR;

    public static byte getCode(KeyWord keyWord) {
        switch (keyWord) {
            default:
            case NO_OPERATION:         return (byte)0x00;
            case GET_INFO:             return (byte)0x10;
            case CHECK_PASSPORT_ID:    return (byte)0x11;
            case CHECK_ID:             return (byte)0x12;
            case GET_ALL:              return (byte)0x21;
            case INSERT:               return (byte)0x22;
            case INSERT_ID:            return (byte)0x23;
            case UPDATE:               return (byte)0x24;
            case REMOVE_KEY:           return (byte)0x25;
            case REMOVE_LOWER:         return (byte)0x26;
            case REMOVE_LOWER_KEY:     return (byte)0x27;
            case REMOVE_GREATER_KEY:   return (byte)0x28;
            case CLEAR:                return (byte)0x29;
            case MIN_BY_MPAA:          return (byte)0x2A;
            case COUNT_GREATER_OSCARS: return (byte)0x2B;
            case FILTER_BY_MPAA:       return (byte)0x2C;
            case CONTINUE:             return (byte)0x30;
            case SOME_LEFT:            return (byte)0x31;
            case NOTHING_LEFT:         return (byte)0x32;
            case LOGIN_CHECK:          return (byte)0x40;
            case LOGIN_INCORRECT:      return (byte)0x41;
            case SERVER_OVERLOADED:    return (byte)0x42;
            case INVALID_SESSION:      return (byte)0x43;
            case OK:                   return (byte)0xFE;
            case ERROR:                return (byte)0xFF;
        }
    }

    public static KeyWord getKeyWord(byte code) {
        switch (code) {
            default:
            case (byte)0x00: return NO_OPERATION;
            case (byte)0x10: return GET_INFO;
            case (byte)0x11: return CHECK_PASSPORT_ID;
            case (byte)0x12: return CHECK_ID;
            case (byte)0x21: return GET_ALL;
            case (byte)0x22: return INSERT;
            case (byte)0x23: return INSERT_ID;
            case (byte)0x24: return UPDATE;
            case (byte)0x25: return REMOVE_KEY;
            case (byte)0x26: return REMOVE_LOWER;
            case (byte)0x27: return REMOVE_LOWER_KEY;
            case (byte)0x28: return REMOVE_GREATER_KEY;
            case (byte)0x29: return CLEAR;
            case (byte)0x2A: return MIN_BY_MPAA;
            case (byte)0x2B: return COUNT_GREATER_OSCARS;
            case (byte)0x2C: return FILTER_BY_MPAA;
            case (byte)0x30: return CONTINUE;
            case (byte)0x31: return SOME_LEFT;
            case (byte)0x32: return NOTHING_LEFT;
            case (byte)0x40: return LOGIN_CHECK;
            case (byte)0x41: return LOGIN_INCORRECT;
            case (byte)0x42: return SERVER_OVERLOADED;
            case (byte)0x43: return INVALID_SESSION;
            case (byte)0xFE: return OK;
            case (byte)0xFF: return ERROR;
        }
    }
}
