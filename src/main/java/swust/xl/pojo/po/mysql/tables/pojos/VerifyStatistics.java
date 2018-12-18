/*
 * This file is generated by jOOQ.
*/
package swust.xl.pojo.po.mysql.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VerifyStatistics implements Serializable {

    private static final long serialVersionUID = -195844144;

    private String    username;
    private Long      count;
    private Long      passedCount;
    private Long      refusedCount;
    private Long      robotCount;
    private Timestamp lastInvokeDatetime;

    public VerifyStatistics() {}

    public VerifyStatistics(VerifyStatistics value) {
        this.username = value.username;
        this.count = value.count;
        this.passedCount = value.passedCount;
        this.refusedCount = value.refusedCount;
        this.robotCount = value.robotCount;
        this.lastInvokeDatetime = value.lastInvokeDatetime;
    }

    public VerifyStatistics(
        String    username,
        Long      count,
        Long      passedCount,
        Long      refusedCount,
        Long      robotCount,
        Timestamp lastInvokeDatetime
    ) {
        this.username = username;
        this.count = count;
        this.passedCount = passedCount;
        this.refusedCount = refusedCount;
        this.robotCount = robotCount;
        this.lastInvokeDatetime = lastInvokeDatetime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCount() {
        return this.count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPassedCount() {
        return this.passedCount;
    }

    public void setPassedCount(Long passedCount) {
        this.passedCount = passedCount;
    }

    public Long getRefusedCount() {
        return this.refusedCount;
    }

    public void setRefusedCount(Long refusedCount) {
        this.refusedCount = refusedCount;
    }

    public Long getRobotCount() {
        return this.robotCount;
    }

    public void setRobotCount(Long robotCount) {
        this.robotCount = robotCount;
    }

    public Timestamp getLastInvokeDatetime() {
        return this.lastInvokeDatetime;
    }

    public void setLastInvokeDatetime(Timestamp lastInvokeDatetime) {
        this.lastInvokeDatetime = lastInvokeDatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VerifyStatistics (");

        sb.append(username);
        sb.append(", ").append(count);
        sb.append(", ").append(passedCount);
        sb.append(", ").append(refusedCount);
        sb.append(", ").append(robotCount);
        sb.append(", ").append(lastInvokeDatetime);

        sb.append(")");
        return sb.toString();
    }
}
