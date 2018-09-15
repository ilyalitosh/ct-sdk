package com.litosh.ilya.ct_sdk.models;

/**
 * Like
 * Класс с константами для лайков
 *
 * @author Ilya Litosh
 */
public final class Like {

    /**
     * Константы с типами лайков
     *
     */
    public class Type {

        /**
         * Тип лайка поста
         *
         */
        public static final String POST_LIKE = "1";

        /**
         * Тип лайка комментария к посту
         *
         */
        public static final String COMMENT_LIKE = "2";

    }

    /**
     * Константы с флагами требуемости лайка
     *
     */
    public class Act {

        /**
         * Требуется лайк
         *
         */
        public static final String PUT_LIKE = "1";

        /**
         * Лайк уже стоит, требуется убрать
         *
         */
        public static final String REMOVE_LIKE = "2";

    }

}
