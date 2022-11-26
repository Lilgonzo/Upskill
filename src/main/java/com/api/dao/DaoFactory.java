package com.api.dao;

import com.api.dao.concrete.JdbcProfileDao;

/**
 * Factory class
 */
public class DaoFactory {

    public enum DaoType {
        PROFILE("Profile");

        public String name;
        DaoType(String s) {
            this.name = s;
        }
    }

    public static Object getDao(DaoType type) {
        switch (type) {
            case PROFILE -> {
                return new JdbcProfileDao();
            }
        }
        return null;
    }
}
