package sg.ic.umx.model;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sg.ic.umx.engine.model.ExecutionData;
import sg.ic.umx.engine.model.RuleViolation;
import sg.ic.umx.util.json.LocalDateTimeDeserializer;
import sg.ic.umx.util.json.LocalDateTimeSerializer;

public class Execution {

    private String id;
    private String name;
    private long applicationId;
    private ExecutionStatus status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime completedDt;

    private ExecutionData statistic;

    private List<RuleViolation> violationList;

    public Execution() {
        // Empty Constructor
    }

    public Execution(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDt() {
        return startDt;
    }

    public void setStartDt(LocalDateTime startDt) {
        this.startDt = startDt;
    }

    public LocalDateTime getCompletedDt() {
        return completedDt;
    }

    public void setCompletedDt(LocalDateTime completedDt) {
        this.completedDt = completedDt;
    }

    public ExecutionData getStatistic() {
        return statistic;
    }

    public void setStatistic(ExecutionData statistic) {
        this.statistic = statistic;
    }

    public List<RuleViolation> getViolationList() {
        return violationList;
    }

    public void setViolationList(List<RuleViolation> violationList) {
        this.violationList = violationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
