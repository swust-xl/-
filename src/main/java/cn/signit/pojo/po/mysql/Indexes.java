/*
 * This file is generated by jOOQ.
*/
package cn.signit.pojo.po.mysql;


import cn.signit.pojo.po.mysql.tables.UserPerson;
import cn.signit.pojo.po.mysql.tables.VerifyStatistics;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>test</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index USER_PERSON_PRIMARY = Indexes0.USER_PERSON_PRIMARY;
    public static final Index VERIFY_STATISTICS_PRIMARY = Indexes0.VERIFY_STATISTICS_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index USER_PERSON_PRIMARY = Internal.createIndex("PRIMARY", UserPerson.USER_PERSON, new OrderField[] { UserPerson.USER_PERSON.ID }, true);
        public static Index VERIFY_STATISTICS_PRIMARY = Internal.createIndex("PRIMARY", VerifyStatistics.VERIFY_STATISTICS, new OrderField[] { VerifyStatistics.VERIFY_STATISTICS.USERNAME }, true);
    }
}
